package it.progess.invoicecreator.dao;

import it.progess.invoicecreator.hibernate.HibernateUtils;
import it.progess.invoicecreator.pojo.TblCategorySupplier;
import it.progess.invoicecreator.pojo.TblCompany;
import it.progess.invoicecreator.pojo.TblDeliveryCity;
import it.progess.invoicecreator.pojo.TblDeliveryDeliveryCountry;
import it.progess.invoicecreator.pojo.TblDeliveryZone;
import it.progess.invoicecreator.pojo.TblEcDelivery;
import it.progess.invoicecreator.properties.GECOParameter;
import it.progess.invoicecreator.vo.Address;
import it.progess.invoicecreator.vo.CategorySupplier;
import it.progess.invoicecreator.vo.Company;
import it.progess.invoicecreator.vo.DeliveryCost;
import it.progess.invoicecreator.vo.EcDelivery;
import it.progess.invoicecreator.vo.GECOObject;
import it.progess.invoicecreator.vo.GECOSuccess;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;

public class DeliveryDao {
	public GECOObject saveUpdatesEcDeliveryCompany(Company sms){
		GECOObject go = saveUpdatesEcCompany(sms.getEcdelivery());
		if (go.type.equals(GECOParameter.SUCCESS_TYPE)){
			TblEcDelivery ed = (TblEcDelivery)((GECOSuccess)go).success;
			return new RegistryDao().saveUpdatesCompany(sms,ed);
		}else{
			return go;
		}
	}
	public GECOObject saveUpdatesEcCompany(EcDelivery sms){
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = null;
		TblEcDelivery tblsm = new TblEcDelivery();
		try{
			tx = session.beginTransaction();
				if (sms.control() == null ){
					
					tblsm.convertToTableToSave(sms);
					if (tblsm.getIdEcDelivery() == 0){
						session.save(tblsm);
						//sms.setIdEcDelivery(tblsm.getIdEcDelivery());
					}else{
						session.saveOrUpdate(tblsm);
					}
				}else{
					if (tx!= null) tx.rollback();
					//session.close();
					return sms.control();
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
		return new GECOSuccess(tblsm);
	}
	public GECOObject getDeliveryPrice(Address address,String key){
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = null;
		Company c = new RegistryDao().getCompany(key);
		DeliveryCost dc = new DeliveryCost();
		try{
			Criteria cr = session.createCriteria(TblEcDelivery.class,"ec");
			cr.add(Restrictions.eq("ec.idEcDelivery", c.getEcdelivery().getIdEcDelivery()));
			cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List<TblEcDelivery> ecdelivers = cr.list();
			if (ecdelivers.size() > 0){
				dc = getDeliveryCost(ecdelivers.get(0),address);
			}
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}finally{
			session.close();
		}
		return new GECOSuccess(dc);
	}
	private DeliveryCost getDeliveryCost(TblEcDelivery tec,Address a){
		DeliveryCost dc = new DeliveryCost();
		dc.setBound(tec.getBound_country());
		dc.setPrice(tec.getDefault_country());
		if (a.getCountryObj() != null){
			for (Iterator<TblDeliveryDeliveryCountry> it = tec.getDeliverycountry().iterator();it.hasNext();){
				TblDeliveryDeliveryCountry tddc = it.next();
				if (tddc.getDeliverycountry().getCountry().getIdCountry() == a.getCountryObj().getIdCountry()){
					dc.setPrice(tddc.getDeliverycountry().getPrice());
					dc.setBound(tddc.getDeliverycountry().getBound());
					if (a.getZoneObj() != null){
						for (Iterator<TblDeliveryZone> itz = tddc.getDeliveryzones().iterator();itz.hasNext();){
							TblDeliveryZone dz = itz.next();
							if (dz.getZone().getIdZone() == a.getZoneObj().getIdZone()){
								dc.setPrice(dz.getPrice());
								dc.setBound(dz.getBound());
								break;
							}
							
						}
					}
					if (a.getCityObj() != null){
						for (Iterator<TblDeliveryCity> itc = tddc.getDeliverycities().iterator();itc.hasNext();){
							TblDeliveryCity tdc = itc.next();
							if (tdc.getCity().getIdCity() == a.getCityObj().getIdCity()){
								dc.setPrice(tdc.getPrice());
								dc.setBound(tdc.getBound());
								break;
							}
							
						}
					}
				}
			}
			
			
		}
		
		return dc;
	}
}
