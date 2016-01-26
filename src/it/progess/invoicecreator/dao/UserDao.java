package it.progess.invoicecreator.dao;

import it.progess.invoicecreator.hibernate.HibernateUtils;
import it.progess.invoicecreator.pojo.TblCustomer;
import it.progess.invoicecreator.pojo.TblRole;
import it.progess.invoicecreator.pojo.TblUser;
import it.progess.invoicecreator.vo.Contact;
import it.progess.invoicecreator.vo.Customer;
import it.progess.invoicecreator.vo.GECOError;
import it.progess.invoicecreator.vo.GECOObject;
import it.progess.invoicecreator.vo.GECOSuccess;
import it.progess.invoicecreator.vo.Role;
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
import java.util.UUID;

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
	public ProgessObject checkCredentials(String username,String password,HttpSession session,boolean ecommerce){
		ProgessObject obj = checkCredentials(username,password);
		if (obj.type.equals(ProgessParameters.PROGESS_SUCCESS)){
			ProgessSuccess ps = (ProgessSuccess)obj;
			User u =(User)ps.getSuccess();
			if (ecommerce == false){
				if (u.getEntity() instanceof Customer){
					Customer c = (Customer)u.getEntity();
					if (c.isIsprivate() == true){
						return new ProgessError("err","Utente non autorizzato");
					}else{
						session.setAttribute("user",new Gson().toJson(((ProgessSuccess)obj).getSuccess()));
					}
				}else{
					session.setAttribute("user",new Gson().toJson(((ProgessSuccess)obj).getSuccess()));
				}
			}else{
				if (u.getEntity() instanceof Customer){
					Customer c = (Customer)u.getEntity();
					if (c.isIsprivate() == true){
						session.setAttribute("user",new Gson().toJson(((ProgessSuccess)obj).getSuccess()));
					}else{
						return new ProgessError("err","Utente non autorizzato");
					}
				}else{
					return new ProgessError("err","Utente non autorizzato");
				}
			}
			
		}
		return obj;
	}
	public ProgessObject checkCredentials(String username,String password,HttpSession session,boolean ecommerce,String uid){
		ProgessObject obj = checkCredentials(username,password,session,ecommerce);
		if (obj.type.equals(ProgessParameters.PROGESS_SUCCESS)){
			ProgessSuccess ps = (ProgessSuccess)obj;
			User u =(User)ps.getSuccess();
			if (u.getCompany().getCode().equals(uid) == false){
				session.setAttribute("user",null);
				obj = new ProgessError("err","Utente non autorizzato");
			}
			
		}
		return obj;
	}
	public ProgessObject checkCredentials(String username,String password){
		
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction t = session.beginTransaction();
		ProgessObject obj = null;
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
				if (u.getActive() == true){
					obj =  new ProgessSuccess(u);
				}else{
					obj = new ProgessError("CRWR", "Utente non attivo.Controlla la tua mail e clicca il link per confermare il tuo account");
				}
				
			}else{
				obj = new  ProgessError("CRWR", "Username o Password errati");
			}
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			t.rollback();
			obj = new ProgessError("CRWR", "Errore di sistema");
			
		}finally{
			session.close();
		}
		return obj;
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
		if (user.getCode() == null || user.getCode() == ""){
			UUID ui = UUID.randomUUID();
			user.setCode(ui.toString());
		}
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
			session.flush();
			session.refresh(tbluser);
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
	public TblUser getSingleUser(String code){
		Session session = HibernateUtils.getSessionFactory().openSession();
		try{
			Criteria cr = session.createCriteria(TblUser.class,"user");
			cr.add(Restrictions.eq("code", code));
			cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List users = cr.list();
			if (users.size() > 0){
				return (TblUser)users.get(0);
			}else{
			    return new TblUser();
			}
		}catch(Exception e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			return null;
			
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
	public User getSingleUserVO(String code){
		TblUser tbluser = getSingleUser(code);
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
	public GECOObject changePassword(User user){
		TblUser tbluser = new TblUser();
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = null;
		if (user.getNewpassword().equals(user.getConfirmpassword()) == false){
			return new GECOError("PWD", "La nuova Password è differente dalla conferma");
		}
		if (user.getNewpassword().equals("") == true || user.getNewpassword().equals(null) == true){
			return new GECOError("PWD", "La nuova Password non puo' essere vuota");
		}
		try{
			ProgessObject obj = this.checkCredentials(user.getUsername(), user.getOldpassword());
			if (obj.type.equals(ProgessParameters.PROGESS_SUCCESS) == true){
					ProgessSuccess ps = (ProgessSuccess)obj;
					User checkeduser = (User)ps.getSuccess();	
					if (checkeduser.getIduser() == user.getIduser()  ){
					user.setPassword(HibernateUtils.md5Java(user.getNewpassword()));  
				    tbluser.convertToTable(user);
				    tx = session.beginTransaction();
				    session.saveOrUpdate(tbluser);
				    
				    tx.commit();
				}else{
					return new GECOError("PWD", "Utente non autorizzato");
				}
			}else{
				return new GECOError("PWD", "La vecchia password non è corretta");
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
		return new GECOSuccess();
	}
	public GECOObject setUserActive(String code){
		User u;
		try{
			u = getSingleUserVO(code);
			u.setActive(true);
			saveUpdate(u);
		}catch(Exception e){
			return new GECOError("error",e.getMessage());
		}
	    return new GECOSuccess(u);
	}
	public int createEcUser(User user, String key){
		user.setActive(false);
		user.setPassword(HibernateUtils.md5Java(user.getPassword()));
		user.setUsername(user.getEmail());
		user.setCompany(new RegistryDao().getCompany(key));
		Role role = new RoleDao().getRoleListEc().get(0);
	    user.setRole(role);
		return saveUpdate(user);
	}
	public GECOObject createEcommerceUser(User user, String key,HttpServletRequest request){
		return new DraftDao().createEcUserForm(user, key,request);
	}
	public GECOObject usernameECForgotten(String tc,String key){
		Session session = HibernateUtils.getSessionFactory().openSession();
		Contact c = new Contact();
		try{
			Criteria cr = session.createCriteria(TblCustomer.class,"customer");
			cr.add(Restrictions.eq("customer.taxcode", tc));
			cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List<TblCustomer> customers = cr.list();
			if (customers.size() > 0){
				for (Iterator<TblCustomer> itc = customers.iterator();itc.hasNext();){
					TblCustomer ct = itc.next();
					if (ct.isIsprivate() == true){
						c.convertFromTable(customers.get(0).getContact());
					}
				}
				if (c.getIdcontact() == 0){
					return new GECOError("UT","Utente non valido");
				}
			}else{
			    return new GECOError("CF","Codice fiscale non presente in anagrafica");
			}
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
			
		}finally{
			session.close();
		}
		TblUser u = getUserFromContact(c);
		if (u.getIduser() == 0){
			return new GECOError("UT","Utente non valido");
		}
		if (u.getCompany().getCode().equals(key) == false){
			return new GECOError("UT","Utente non valido");
		}
		return new MailDao().sendEcRecoverUsername(u);
	}
	public GECOObject passwordECForgotten(String tc,String key,HttpServletRequest request){
		Session session = HibernateUtils.getSessionFactory().openSession();
		Contact c = new Contact();
		try{
			Criteria cr = session.createCriteria(TblCustomer.class,"customer");
			cr.add(Restrictions.eq("customer.taxcode", tc));
			cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List<TblCustomer> customers = cr.list();
			if (customers.size() > 0){
				for (Iterator<TblCustomer> itc = customers.iterator();itc.hasNext();){
					TblCustomer ct = itc.next();
					if (ct.isIsprivate() == true){
						c.convertFromTable(customers.get(0).getContact());
					}
				}
				if (c.getIdcontact() == 0){
					return new GECOError("UT","Utente non valido");
				}
			}else{
			    return new GECOError("CF","Codice fiscale non presente in anagrafica");
			}
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
			
		}finally{
			session.close();
		}
		TblUser u = getUserFromContact(c);
		if (u.getIduser() == 0){
			return new GECOError("UT","Utente non valido");
		}
		if (u.getCompany().getCode().equals(key) == false){
			return new GECOError("UT","Utente non valido");
		}
		return new MailDao().sendEcRecoverPassword(u,request);
	}
	public GECOObject resetPassword(User u){
		if (u.getPassword().equals(u.getConfirmpassword()) == false){
			return new GECOError("PWD","La password inserita è diversa da quella di conferma");
		}
		User user = getSingleUserVO(u.getCode());
		user.setPassword(HibernateUtils.md5Java(u.getPassword()));
		if (saveUpdate(user) <=0){
			return new GECOError("PWD","Cambio password non riuscito");
		}
		
		return new GECOSuccess();
	}
	public TblUser getUserFromContact(Contact c){
		Session session = HibernateUtils.getSessionFactory().openSession();
		try{
			Criteria cr = session.createCriteria(TblUser.class,"user");
			cr.createAlias("user.contact", "contact");
			cr.add(Restrictions.eq("contact.idcontact", c.getIdcontact()));
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
}
