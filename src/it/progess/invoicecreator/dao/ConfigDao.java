package it.progess.invoicecreator.dao;

import it.progess.invoicecreator.hibernate.HibernateUtils;
import it.progess.invoicecreator.pojo.TblCustomer;
import it.progess.invoicecreator.pojo.TblDocumentFlow;
import it.progess.invoicecreator.properties.GECOParameter;
import it.progess.invoicecreator.vo.Customer;
import it.progess.invoicecreator.vo.DocumentFlow;
import it.progess.invoicecreator.vo.GECOError;
import it.progess.invoicecreator.vo.GECOObject;
import it.progess.invoicecreator.vo.GECOSuccess;
import it.progess.invoicecreator.vo.User;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

public class ConfigDao {
	/****
	 * 
	 * Document flow list
	 * @param user
	 * @return
	 */
	public ArrayList<DocumentFlow> getDocumentFlowList(User user){
		Session session = HibernateUtils.getSessionFactory().openSession();
		ArrayList<DocumentFlow> list = new ArrayList<DocumentFlow>();
		try{
			Criteria cr = session.createCriteria(TblDocumentFlow.class,"flow");
			cr.add(Restrictions.eqOrIsNull("flow.company.idCompany", user.getCompany().getIdCompany()));
			List<TblDocumentFlow> flows = cr.list();
			if (flows.size() > 0){
				for (Iterator<TblDocumentFlow> iterator = flows.iterator(); iterator.hasNext();){
					TblDocumentFlow tdf = iterator.next();
					DocumentFlow df = new DocumentFlow();
					df.convertFromTable(tdf);
					list.add(df);
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
	/***
	 * Delete document flow
	 * @param sm
	 * @return
	 */
	public Boolean deleteDocumentFlow(DocumentFlow sm){
		TblDocumentFlow tblsm = new TblDocumentFlow();
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = null;
		try{
			tblsm.convertToTable(sm);
			tx = session.beginTransaction();
			session.delete(tblsm);
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
		return true;
		
	}
	/****
	 * save update document flow
	 * @param sm
	 * @param user
	 * @return
	 */
	public GECOObject saveUpdatesDocumentFlow(DocumentFlow sm,User user){
		if (sm.control() == null){
			Session session = HibernateUtils.getSessionFactory().openSession();
			Transaction tx = null;
			sm.setCompany(user.getCompany());
			try{
				tx = session.beginTransaction();
				TblDocumentFlow tblsm = new TblDocumentFlow();
				tblsm.convertToTable(sm);
				session.saveOrUpdate(tblsm);
				tx.commit();
			}catch(HibernateException e){
				System.err.println("ERROR IN LIST!!!!!!");
				if (tx!= null) tx.rollback();
				e.printStackTrace();
				return new GECOError(GECOParameter.ERROR_HIBERNATE, "Errore nel salvataggio dei dati");
			}finally{
				session.close();
			}
		}else{
			return sm.control();
		}
		return new GECOSuccess();
	}
}
