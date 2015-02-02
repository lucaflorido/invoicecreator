package it.progess.invoicecreator.dao;

import it.progess.invoicecreator.hibernate.HibernateUtils;
import it.progess.invoicecreator.pojo.TblRole;
import it.progess.invoicecreator.pojo.TblUser;
import it.progess.invoicecreator.vo.User;
import it.progess.transport.config.ProgessParameters;
import it.progess.transport.vo.ProgessError;
import it.progess.transport.vo.ProgessObject;
import it.progess.transport.vo.ProgessSuccess;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.SystemException;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;

import com.google.gson.Gson;

public class UserDao {
	
	
	public UserDao(){
		
	}
	/***
	 * Check if exists the admin user,if it doesn't exist it will create it
	 * @return
	 */
	public Boolean checkAdmin(){
		Session session = HibernateUtils.getSessionFactory().openSession();
		Boolean result = false;
		try{
			Criteria cr = session.createCriteria(TblUser.class,"user");
			cr.createAlias("user.role", "role");
			cr.add(Restrictions.eq("role.admin", true));
			cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List users = cr.list();
			if (users.size() > 0){
				result =  true;
			}
				
			
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			session.close();
			throw new ExceptionInInitializerError(e);
		}finally{
			session.close();
		}
		if (result == false){
			
			createAdminFun();
		    return true;
		}
		return result;
	}
	public Boolean createAdminFun(){
		TblRole role = new RoleDao().createAdminRole();
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = null;
		Boolean result = false;
		try{
			tx = session.beginTransaction();
			TblUser admin = this.createAdmin();
			admin.setRole(role);
			session.save(admin);
		    tx.commit();
				
			
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			if (tx!= null) tx.rollback();
			e.printStackTrace();
			session.close();
			throw new ExceptionInInitializerError(e);
		}finally{
			session.close();
		}
		
		return result;
	}
	/***
	 * Check if exists the credentials are correct
	 * @return
	 */
	public ProgessObject checkCredentials(String username,String password,HttpSession session){
		ProgessObject obj = checkCredentials(username,password);
		if (obj.type.equals(ProgessParameters.PROGESS_SUCCESS)){
			session.setAttribute("user",new Gson().toJson(((ProgessSuccess)obj).getSuccess()));
		}
		return obj;
	}
	public ProgessObject checkCredentials(String username,String password){
		
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		try{
			
			Criteria cr = session.createCriteria(TblUser.class,"user");
			cr.add(Restrictions.eq("username", username));
			password = HibernateUtils.md5Java(password);
		    cr.add(Restrictions.eq("password", password));
			cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List users = cr.list();
			t.commit();
			if (users.size() > 0){
				TblUser tu = (TblUser)users.get(0);
				User u = new User();
				u.convertFromTable(tu);
				new DocumentDao().checkExpiredDoxuments(u);
				return new ProgessSuccess(u);
			}else{
			    return new ProgessError("CRWR", "Username o Password errati");
			}
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			t.rollback();
			throw new ExceptionInInitializerError(e);
			
		}finally{
			session.close();
		}
		/**/
	}
	/***
	 * Get the list of users
	 * @return
	 */
	public ArrayList<User> getListUser(){
		Session session = HibernateUtils.getSessionFactory().openSession();
		ArrayList<User> list = new ArrayList<User>();
		try{
			Criteria cr = session.createCriteria(TblUser.class);
			cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List users = cr.list();
			if (users.size() > 0){
				for (Iterator iterator = users.iterator(); iterator.hasNext();){
					TblUser tbluser = (TblUser)iterator.next();
					User user = new User();
					user.convertFromTable(tbluser);
					list.add(user);
				}
			}
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}finally{
			session.close();
		}
		return list;
	}
	private TblUser createAdmin(){
		TblUser admin = new TblUser();
	    TblRole role = new TblRole();
	    admin.setRole(role);
	    admin.setActive(true);
	    admin.setUsername("admin");
	    String password ="admin";
	    password = HibernateUtils.md5Java(password);
	    admin.setPassword(password);
	    role.setName("Administrator");
	    role.setCreate(true);
	    role.setAdmin(true);
	    role.setRead(true);
	    role.setUpdate(true);
	    role.setDelete(true);
	    return admin;
	}
	/**
	 * SAVE THE USER
	 * **/
	public int saveUpdate(User user){
		TblUser tbluser = new TblUser();
		int iduser=0;
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = null;
		try{
			if (user.getIduser() <= 0 && user.getPassword() == null ){
				user.setPassword(HibernateUtils.md5Java(user.getUsername()));  
			}
			tbluser.convertToTableSave(user);
			tx = session.beginTransaction();
			session.saveOrUpdate(tbluser);
			iduser = tbluser.getIduser();
			tx.commit();
			
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			if (tx!= null) tx.rollback();
			e.printStackTrace();
			session.close();
			throw new ExceptionInInitializerError(e);
		}finally{
			session.close();
		}
		
		return iduser;
	}
	public int saveUpdate(User user,HttpSession sessionhttp,HttpServletRequest request){
		TblUser tbluser = new TblUser();
		int iduser=0;
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = null;
		try{
			if (user.getIduser() <= 0 && user.getPassword() == null ){
				user.setPassword(HibernateUtils.md5Java(user.getUsername()));  
			}
			tbluser.convertToTableSave(user);
			tx = session.beginTransaction();
			session.saveOrUpdate(tbluser);
			iduser = tbluser.getIduser();
			tx.commit();
			
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			if (tx!= null) tx.rollback();
			e.printStackTrace();
			session.close();
			throw new ExceptionInInitializerError(e);
		}finally{
			session.close();
		}
		if (HibernateUtils.getUserFromSession(request).getIduser() == iduser){
			sessionhttp.setAttribute("user",new Gson().toJson(getSingleUserVO(iduser)));
		}
		return iduser;
	}
	/**
	 * GET A SINGLE USER
	 * **/
	public TblUser getSingleUser(int iduser){
		Session session = HibernateUtils.getSessionFactory().openSession();
		try{
			Criteria cr = session.createCriteria(TblUser.class,"user");
			cr.add(Restrictions.eq("iduser", iduser));
			cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List users = cr.list();
			if (users.size() > 0){
				return (TblUser)users.get(0);
			}else{
			    return new TblUser();
			}
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
			
		}finally{
			session.close();
		}
	}
	public User getSingleUserVO(int iduser){
		TblUser tbluser = getSingleUser(iduser);
		User user = new User();
		user.convertFromTable(tbluser);
		return user;
	}
	/***
	 * DELETE A SINGLE USER
	 * **/
	public void deleteUser(User user){
		TblUser tbluser = new TblUser();
		int iduser=0;
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = null;
		try{
			tbluser.convertToTable(user);
			tx = session.beginTransaction();
			session.delete(tbluser);
			iduser = tbluser.getIduser();
			tx.commit();
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			if (tx!= null) tx.rollback();
			e.printStackTrace();
			session.close();
			
			throw new ExceptionInInitializerError(e);
		}finally{
			session.close();
		}
		
	}
	/**
	 * Change the user password
	 * **/
	public int changePassword(User user){
		TblUser tbluser = new TblUser();
		int iduser=0;
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = null;
		try{
			ProgessObject obj = this.checkCredentials(user.getUsername(), user.getPassword());
				if (obj.type.equals(ProgessParameters.PROGESS_SUCCESS)){
					ProgessSuccess ps = (ProgessSuccess)obj;
					User checkeduser = (User)ps.getSuccess();	
					if (checkeduser.getIduser() == user.getIduser() && user.getNewpassword() != "" && user.getNewpassword() != null ){
					user.setPassword(HibernateUtils.md5Java(user.getNewpassword()));  
				    tbluser.convertToTable(user);
				    tx = session.beginTransaction();
				    session.saveOrUpdate(tbluser);
				    iduser = tbluser.getIduser();
				    tx.commit();
				}
			}
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			if (tx!= null) tx.rollback();
			e.printStackTrace();
			session.close();
			throw new ExceptionInInitializerError(e);
		}finally{
			session.close();
		}
		return iduser;
	}
}
