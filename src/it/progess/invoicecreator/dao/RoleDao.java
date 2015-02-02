package it.progess.invoicecreator.dao;

import it.progess.invoicecreator.hibernate.HibernateUtils;
import it.progess.invoicecreator.pojo.TblRole;
import it.progess.invoicecreator.pojo.TblUser;
import it.progess.invoicecreator.vo.Role;
import it.progess.invoicecreator.vo.User;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;

public class RoleDao {
	/***
	 * Get the list of role
	 * @return
	 */
	public ArrayList<Role> getRoleList(){
		Session session = HibernateUtils.getSessionFactory().openSession();
		ArrayList<Role> list = new ArrayList<Role>();
		try{
			Criteria cr = session.createCriteria(TblRole.class);
			List roles = cr.list();
			if (roles.size() > 0){
				for (Iterator iterator = roles.iterator(); iterator.hasNext();){
					TblRole tblrole = (TblRole)iterator.next();
					Role role = new Role();
					role.convertFromTable(tblrole);
					list.add(role);
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
	public ArrayList<Role> saveUpdatesRoles(Role[] roles){
		Session session = HibernateUtils.getSessionFactory().openSession();
		ArrayList<Role> list = new ArrayList<Role>();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			for(int i =0; i< roles.length;i++){
				Role role = roles[i];
				if (role.getName() != "" && role.getName() != null){
					TblRole tblrole = new TblRole();
					tblrole.convertToTable(role);
					session.saveOrUpdate(tblrole);
				}
			}
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
		return this.getRoleList();
	}
	/***
	 * DELETE A SINGLE ROLE
	 * **/
	public ArrayList<Role> deleteRole(Role role){
		TblRole tblrole = new TblRole();
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = null;
		try{
			tblrole.convertToTable(role);
			tx = session.beginTransaction();
			session.delete(tblrole);
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
		return this.getRoleList();
		
	}
	/****
	 *  Create default role
	 */
	public TblRole createAdminRole(){
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = null;
		TblRole role = new TblRole();
		role.setAdmin(true);
		role.setCreate(true);
		role.setDelete(true);
		role.setName("Admin");
		role.setRead(true);
		role.setUpdate(true);
		try{
			tx = session.beginTransaction();
			
		    session.save(role);
			
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
		return getRoleAdmin();
	}
	public TblRole getRoleAdmin(){
		Session session = HibernateUtils.getSessionFactory().openSession();
		TblRole role = new TblRole();
		try{
			Criteria cr = session.createCriteria(TblRole.class,"role");
			cr.add(Restrictions.eq("role.admin", true));
			cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List roles = cr.list();
			role = (TblRole)roles.get(0);
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}finally{
			session.close();
		}
		return role;
	}
	 
}
