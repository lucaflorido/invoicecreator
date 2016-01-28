package it.progess.invoicecreator.dao;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import it.progess.invoicecreator.hibernate.DataUtilConverter;
import it.progess.invoicecreator.hibernate.HibernateUtils;
import it.progess.invoicecreator.pojo.TblPaid;
import it.progess.invoicecreator.pojo.TblStorage;
import it.progess.invoicecreator.pojo.TblSuspended;
import it.progess.invoicecreator.properties.GECOParameter;
import it.progess.invoicecreator.util.accounting.AccountingHelper;
import it.progess.invoicecreator.vo.AccountingElement;
import it.progess.invoicecreator.vo.Customer;
import it.progess.invoicecreator.vo.GECOError;
import it.progess.invoicecreator.vo.GECOObject;
import it.progess.invoicecreator.vo.GECOSuccess;
import it.progess.invoicecreator.vo.Head;
import it.progess.invoicecreator.vo.Paid;
import it.progess.invoicecreator.vo.Storage;
import it.progess.invoicecreator.vo.Suspended;
import it.progess.invoicecreator.vo.filter.AccountingFilter;
import it.progess.invoicecreator.vo.filter.AccountingMovementFilter;

import org.codehaus.jackson.map.ser.std.StdContainerSerializers.IteratorSerializer;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class AccountingDao {
	public void saveSuspended(Suspended susp,Session session){
		try{
			TblSuspended st = new TblSuspended();
			st.convertToTable(susp);
			session.saveOrUpdate(st);
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}
	} 
	public GECOObject savePaid(Paid sm){
		
		if (sm.control() == null){
			Session session = HibernateUtils.getSessionFactory().openSession();
			Transaction tx = null;
			try{
				tx = session.beginTransaction();
				GECOObject obj = savePaid(sm, session);
				TblPaid paidTbl = null;
				if (obj.type.equals(GECOParameter.SUCCESS_TYPE)){
					paidTbl = (TblPaid)((GECOSuccess)obj).success;
				}else{
					return obj;
				}
				sm.setIdPaid(paidTbl.getIdPaid());
				AccountingHelper.payGeneralSuspended(sm, session);
				savePaid(sm,paidTbl, session);
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
		}else{
			return sm.control();
		}
		return new GECOSuccess(true);
	}
	public GECOObject savePaid(Paid sm,Set<Suspended> susp){
		if (sm.control() == null){
			Session session = HibernateUtils.getSessionFactory().openSession();
			Transaction tx = null;
			try{
				tx = session.beginTransaction();
				GECOObject obj = savePaid(sm, session);
				TblPaid paidTbl = null;
				if (obj.type.equals(GECOParameter.SUCCESS_TYPE)){
					paidTbl = (TblPaid)((GECOSuccess)obj).success;
				}else{
					return obj;
				}
				sm.setIdPaid(paidTbl.getIdPaid());
				AccountingHelper.paySelectedSuspended(sm, susp, session);
				savePaid(sm,paidTbl, session);
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
		}else{
			return sm.control();
		}
		return new GECOSuccess(true);
	}
	private GECOObject savePaid(Paid sm,Session session){
		TblPaid tblsm = new TblPaid();
		if (sm.control() == null){
		int id = 0;
		
		try{
			
			tblsm.convertToTable(sm);
			session.saveOrUpdate(tblsm);
			id = tblsm.getIdPaid();
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			
			throw new ExceptionInInitializerError(e);
		}
		}else{
			return sm.control();
		}
		return new GECOSuccess(tblsm);
		
	}
	private GECOObject savePaid(Paid sm,TblPaid tblsm ,Session session){
		if (sm.control() == null){
		try{
			
			tblsm.convertToTable(sm);
			session.saveOrUpdate(tblsm);
			
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			
			throw new ExceptionInInitializerError(e);
		}
		}else{
			return sm.control();
		}
		return new GECOSuccess(tblsm);
		
	}
	public GECOObject getList(AccountingFilter filter){
		Session session = HibernateUtils.getSessionFactory().openSession();
		TreeSet<Suspended> list = new TreeSet<Suspended>();
		
		try{
			Criteria cr = session.createCriteria(TblSuspended.class,"suspended");
			cr.add(Restrictions.eq("suspended.customer",filter.isCustomer));
			cr.add(Restrictions.eq("suspended.supplier",filter.isSupplier));
			cr.createAlias("suspended.head", "head");
			
			cr.createAlias("suspended.deadlines", "deadlines");//expiredDate
			cr.add(Restrictions.eq("suspended.paid", filter.paid));
			if (filter.customer != null){
				cr.createAlias("head.customer", "customer");
				cr.add(Restrictions.eq("customer.idCustomer", filter.customer.getIdCustomer()));
			}else if (filter.supplier != null){
				cr.createAlias("head.supplier", "supplier");
				cr.add(Restrictions.eq("supplier.idSupplier", filter.supplier.getIdSupplier()));
			}
			if (filter.dateFrom != null && filter.dateFrom.equals("") != true && filter.dateTo != null &&   filter.dateTo.equals("") != true  ){
				cr.add(Restrictions.disjunction().add(Restrictions.between("head.date", DataUtilConverter.convertDateFromString(filter.dateFrom), DataUtilConverter.convertDateFromString(filter.dateTo)) ).add(Restrictions.between("deadlines.expiredDate", DataUtilConverter.convertDateFromString(filter.dateFrom), DataUtilConverter.convertDateFromString(filter.dateTo))));
			}else{
				if (filter.dateFrom != null && filter.dateFrom.equals("") != true){
					cr.add(Restrictions.disjunction().add(Restrictions.ge("head.date", DataUtilConverter.convertDateFromString(filter.dateFrom)) ).add(Restrictions.ge("deadlines.expiredDate", DataUtilConverter.convertDateFromString(filter.dateFrom))));
				}else if (filter.dateTo != null && filter.dateTo.equals("") != true){
					cr.add(Restrictions.disjunction().add(Restrictions.le("head.date", DataUtilConverter.convertDateFromString(filter.dateTo)) ).add(Restrictions.le("deadlines.expiredDate", DataUtilConverter.convertDateFromString(filter.dateTo))));
				}
			}
			cr.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			List<TblSuspended> suspendeds = cr.list();
			if (suspendeds.size() > 0){
				for (Iterator<TblSuspended> iterator = suspendeds.iterator(); iterator.hasNext();){
					TblSuspended s = iterator.next();
					Suspended susp = new Suspended();
					susp.convertFromTable(s);
					list.add(susp);
				}
			}
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			//throw new ExceptionInInitializerError(e);
			return new GECOError(GECOParameter.ERROR_HIBERNATE,"Errore nella ricerca");
			
			
		}finally{
			session.close();
		}
		return new GECOSuccess(list);
	}
	public TreeSet<Suspended> getListFromCustomer(Customer c){
		Session session = HibernateUtils.getSessionFactory().openSession();
		TreeSet<Suspended> list = new TreeSet<Suspended>();
		try{
			Criteria cr = session.createCriteria(TblSuspended.class,"suspended");
			cr.createAlias("suspended.head", "head");
			cr.createAlias("head.customer", "customer");
			cr.add(Restrictions.eq("customer.idCustomer", c.getIdCustomer()));
			cr.add(Restrictions.eq("suspended.paid", false));
			cr.addOrder(Order.desc("head.date"));
			cr.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			List<TblSuspended> suspendeds = cr.list();
			if (suspendeds.size() > 0){
				for (Iterator<TblSuspended> iterator = suspendeds.iterator(); iterator.hasNext();){
					TblSuspended s = iterator.next();
					Suspended susp = new Suspended();
					susp.convertFromTable(s);
					list.add(susp);
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
	@SuppressWarnings("unchecked")
	public GECOObject getAccountigList(AccountingMovementFilter filter){
		TreeSet<AccountingElement> list = new TreeSet<AccountingElement>();
		TreeSet<Suspended> listsusp = new TreeSet<Suspended>();
		TreeSet<Paid> listpaid = new TreeSet<Paid>();
		try{
			GECOObject suspobj = getListSuspended(filter);
			if (suspobj.type == GECOParameter.SUCCESS_TYPE){
				listsusp = (TreeSet<Suspended>)((GECOSuccess)suspobj).success;
				for (Iterator<Suspended> its = listsusp.iterator();its.hasNext();){
					Suspended s = its.next();
					AccountingElement e = new AccountingElement(s);
					list.add(e);
				}
			}else{
				return suspobj;
			}
			GECOObject paidobj = getListPaid(filter);
			if (paidobj.type == GECOParameter.SUCCESS_TYPE){
				listpaid = (TreeSet<Paid>)((GECOSuccess)paidobj).success;
				for (Iterator<Paid> itp = listpaid.iterator();itp.hasNext();){
					Paid p = itp.next();
					AccountingElement e = new AccountingElement(p);
					list.add(e);
				}
			}else{
				return paidobj;
			}
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			return new GECOError(GECOParameter.ERROR_GENERIC, "Errore nel caricamento dei dati");
		}
		return new GECOSuccess(list);
	}
	private GECOObject getListSuspended(AccountingMovementFilter filter){
		Session session = HibernateUtils.getSessionFactory().openSession();
		TreeSet<Suspended> list = new TreeSet<Suspended>();
		
		try{
			Criteria cr = session.createCriteria(TblSuspended.class,"suspended");
			
			cr.createAlias("suspended.head", "head");
			
			cr.createAlias("suspended.deadlines", "deadlines");//expiredDate
			if (filter.getSinglecustomer() != null){
				cr.createAlias("head.customer", "customer");
				cr.add(Restrictions.eq("customer.idCustomer", filter.getSinglecustomer().getIdCustomer()));
			}else if (filter.getSinglesupplier() != null){
				cr.createAlias("head.supplier", "supplier");
				cr.add(Restrictions.eq("supplier.idSupplier", filter.getSinglesupplier().getIdSupplier()));
			}
			if (filter.getFromDate() != null && filter.getFromDate().equals("") != true && filter.getToDate() != null &&   filter.getToDate().equals("") != true  ){
				cr.add(Restrictions.disjunction().add(Restrictions.between("head.date", DataUtilConverter.convertDateFromString(filter.getFromDate()), DataUtilConverter.convertDateFromString(filter.getToDate())) ).add(Restrictions.between("deadlines.expiredDate", DataUtilConverter.convertDateFromString(filter.getFromDate()), DataUtilConverter.convertDateFromString(filter.getToDate()))));
			}else{
				if (filter.getFromDate() != null && filter.getFromDate().equals("") != true){
					cr.add(Restrictions.disjunction().add(Restrictions.ge("head.date", DataUtilConverter.convertDateFromString(filter.getFromDate())) ).add(Restrictions.ge("deadlines.expiredDate", DataUtilConverter.convertDateFromString(filter.getFromDate()))));
				}else if (filter.getToDate() != null && filter.getToDate().equals("") != true){
					cr.add(Restrictions.disjunction().add(Restrictions.le("head.date", DataUtilConverter.convertDateFromString(filter.getToDate())) ).add(Restrictions.le("deadlines.expiredDate", DataUtilConverter.convertDateFromString(filter.getToDate()))));
				}
			}
			cr.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			List<TblSuspended> suspendeds = cr.list();
			if (suspendeds.size() > 0){
				for (Iterator<TblSuspended> iterator = suspendeds.iterator(); iterator.hasNext();){
					TblSuspended s = iterator.next();
					Suspended susp = new Suspended();
					susp.convertFromTable(s);
					list.add(susp);
				}
			}
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			//throw new ExceptionInInitializerError(e);
			return new GECOError(GECOParameter.ERROR_HIBERNATE,"Errore nella ricerca");
			
			
		}finally{
			session.close();
		}
		return new GECOSuccess(list);
	}
	private GECOObject getListPaid(AccountingMovementFilter filter){
		Session session = HibernateUtils.getSessionFactory().openSession();
		TreeSet<Paid> list = new TreeSet<Paid>();
		
		try{
			Criteria cr = session.createCriteria(TblPaid.class,"paid");
			
			
			
			if (filter.getSinglecustomer() != null){
				cr.createAlias("paid.customer_paid", "customer");
				cr.add(Restrictions.eq("customer.idCustomer", filter.getSinglecustomer().getIdCustomer()));
			}else if (filter.getSinglesupplier() != null){
				//cr.createAlias("paid.supplier_paid", "supplier");
				cr.add(Restrictions.eq("paid.supplier_paid.idSupplier", filter.getSinglesupplier().getIdSupplier()));
			}
			if (filter.getFromDate() != null && filter.getFromDate().equals("") != true && filter.getToDate() != null &&   filter.getToDate().equals("") != true  ){
				cr.add(Restrictions.between("paid.date", DataUtilConverter.convertDateFromString(filter.getFromDate()), DataUtilConverter.convertDateFromString(filter.getToDate())));
			}else{
				if (filter.getFromDate() != null && filter.getFromDate().equals("") != true){
					cr.add(Restrictions.ge("paid.date", DataUtilConverter.convertDateFromString(filter.getFromDate())) );
				}else if (filter.getToDate() != null && filter.getToDate().equals("") != true){
					cr.add(Restrictions.le("paid.date", DataUtilConverter.convertDateFromString(filter.getToDate())));
				}
			}
			cr.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			List<TblPaid> suspendeds = cr.list();
			if (suspendeds.size() > 0){
				for (Iterator<TblPaid> iterator = suspendeds.iterator(); iterator.hasNext();){
					TblPaid s = iterator.next();
					Paid susp = new Paid();
					susp.convertFromTableForList(s);
					list.add(susp);
				}
			}
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			//throw new ExceptionInInitializerError(e);
			return new GECOError(GECOParameter.ERROR_HIBERNATE,"Errore nella ricerca");
			
			
		}finally{
			session.close();
		}
		return new GECOSuccess(list);
	}
	public Suspended getSuspendedHead(Head head){
		Session session = HibernateUtils.getSessionFactory().openSession();
		try{
			Criteria cr = session.createCriteria(TblSuspended.class,"suspended");
			cr.createAlias("suspended.head", "head");
			cr.add(Restrictions.eq("head.idHead",head.getIdHead()));
			
			cr.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			List<TblSuspended> suspendeds = cr.list();
			if (suspendeds.size() > 0){
				TblSuspended s = suspendeds.get(0);
					Suspended susp = new Suspended();
					susp.convertFromTable(s);
					return susp;
			}else{
				return null;
			}
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			//throw new ExceptionInInitializerError(e);
			return null;
			
			
		}finally{
			session.close();
		}
		
	}
	
}
