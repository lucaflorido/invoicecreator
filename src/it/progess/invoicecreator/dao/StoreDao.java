package it.progess.invoicecreator.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import it.progess.invoicecreator.hibernate.DataUtilConverter;
import it.progess.invoicecreator.hibernate.HibernateUtils;
import it.progess.invoicecreator.pojo.TblEcPayment;
import it.progess.invoicecreator.pojo.TblHead;
import it.progess.invoicecreator.pojo.TblProductEcConfig;
import it.progess.invoicecreator.pojo.TblRow;
import it.progess.invoicecreator.pojo.TblStorage;
import it.progess.invoicecreator.pojo.TblStorageSerialCode;
import it.progess.invoicecreator.vo.EcPayment;
import it.progess.invoicecreator.vo.Head;
import it.progess.invoicecreator.vo.Product;
import it.progess.invoicecreator.vo.ProductEcConfig;
import it.progess.invoicecreator.vo.Storage;
import it.progess.invoicecreator.vo.StorageSerialCode;
import it.progess.invoicecreator.vo.UnitMeasureProduct;
import it.progess.invoicecreator.vo.filter.StoreFilter;






import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class StoreDao {
	public void saveStore(Storage store){
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			TblStorage st = new TblStorage();
			st.convertToTable(store);
			session.saveOrUpdate(st);
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
	public void saveStore(Storage store,Session session){
		
		try{
			TblStorage st = new TblStorage();
			st.convertToTable(store);
			session.saveOrUpdate(st);
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}
	}
	public void createStored(){
		
	}
	public Storage getStorageProduct(Product prod){
		Session session = HibernateUtils.getSessionFactory().openSession();
		Storage store = new Storage();
		try{
			Criteria cr = session.createCriteria(TblStorage.class,"store");
			cr.add(Restrictions.eq("store.product.idProduct",prod.getIdProduct()));
			List<TblStorage> stores = cr.list();
			if (stores.size() > 0){
				TblStorage ts = stores.get(0);
				store.convertFromTable(ts);
			}
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}finally{
			session.close();
		}
		return store;
	}
	public Storage getStorageProductNotEmpty(Product prod){
		Session session = HibernateUtils.getSessionFactory().openSession();
		Storage store = new Storage();
		try{
			Criteria cr = session.createCriteria(TblStorage.class,"store");
			cr.add(Restrictions.eq("store.product.idProduct",prod.getIdProduct()));
			cr.createAlias("storage.storagesc", "serials");
			cr.add(Restrictions.disjunction(Restrictions.isNull("serials"),Restrictions.gt("serials.stock", "0")));
			List<TblStorage> stores = cr.list();
			if (stores.size() > 0){
				TblStorage ts = stores.get(0);
				store.convertFromTable(ts);
			}
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}finally{
			session.close();
		}
		return store;
	}
	public ArrayList<Storage> getList(){
		Session session = HibernateUtils.getSessionFactory().openSession();
		
		ArrayList<Storage> list = new ArrayList<Storage>();
		try{
			Criteria cr = session.createCriteria(TblStorage.class,"store");
			List<TblStorage> stores = cr.list();
			if (stores.size() > 0){
				for (Iterator<TblStorage> iterator = stores.iterator(); iterator.hasNext();){
					TblStorage ts = iterator.next();
					Storage store = new Storage();
					store.convertFromTable(ts);
					list.add(store);
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
	public StorageSerialCode getStorageSerialCode(Storage st,String serialnumber){
		Session session = HibernateUtils.getSessionFactory().openSession();
		StorageSerialCode store = new StorageSerialCode();
		try{
			Criteria cr = session.createCriteria(TblStorageSerialCode.class,"store");
			cr.add(Restrictions.eq("store.storage.idStorage",st.getIdStorage()));
			cr.add(Restrictions.eq("store.serialnumber",serialnumber));
			cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List<TblStorageSerialCode> stores = cr.list();
			if (stores.size() > 0){
				TblStorageSerialCode ts = stores.get(0);
				store.convertFromTable(ts);
			}
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}finally{
			session.close();
		}
		return store;
	}
	public ArrayList<Storage> getListStorage(StoreFilter filter){
		Session session = HibernateUtils.getSessionFactory().openSession();
		
		ArrayList<Storage> list = new ArrayList<Storage>();
		try{
			Criteria cr = session.createCriteria(TblStorage.class,"store");
			cr.createAlias("store.product", "product");
			if (filter.getBrand() != null && filter.getBrand().getIdBrand() > 0){
				cr.add(Restrictions.eq("product.brand.idBrand", filter.getBrand().getIdBrand()));
			}
			if (filter.getGroup() != null && filter.getGroup().getIdGroupProduct() > 0){
				cr.add(Restrictions.eq("product.group.idGroupProduct", filter.getGroup().getIdGroupProduct()));
			}
			if (filter.getCategory() != null && filter.getCategory().getIdCategoryProduct() > 0){
				cr.add(Restrictions.eq("product.category.idCategoryProduct", filter.getCategory().getIdCategoryProduct()));
			}
			if (filter.getSubcategory() != null && filter.getSubcategory().getIdSubCategoryProduct() > 0){
				cr.add(Restrictions.eq("product.subcategory.idSubCategoryProduct", filter.getSubcategory().getIdSubCategoryProduct()));
			}
			if (filter.getSearchString() != null && filter.getSearchString() != ""){
				cr.createAlias("product.ums", "ums");
				cr.add(Restrictions.disjunction(Restrictions.ilike("product.description", filter.getSearchString()),Restrictions.ilike("ums.code", filter.getSearchString())));
			}
			cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List<TblStorage> stores = cr.list();
			if (stores.size() > 0){
				for (Iterator<TblStorage> iterator = stores.iterator(); iterator.hasNext();){
					TblStorage ts = iterator.next();
					Storage store = new Storage();
					store.convertFromTable(ts);
					list.add(store);
				}
			}
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}finally{
			session.close();
		}
		if ((filter.getFromDate() != null && filter.getFromDate().equals("") != true) || ( filter.getToDate() != null &&   filter.getToDate().equals("") != true )){
			for (Iterator<Storage> it = list.iterator();it.hasNext();){
				Storage s = it.next();
				double load = setQuantity(filter,s,null,true);
				s.setLoad(load);
				double unload = setQuantity(filter,s,null,false);
				s.setUnload(unload);
				if (s.getStoragesc() != null){
					for (Iterator<StorageSerialCode> itsc = s.getStoragesc().iterator();itsc.hasNext();){
						StorageSerialCode ssc = itsc.next();
						double loadsc = setQuantity(filter, s, ssc,true);
						ssc.setLoad(loadsc);
						double unloadsc = setQuantity(filter, s, ssc,false);
						ssc.setUnload(unloadsc);
					}
				}
			}
		}
		return list;
	}
	private void criteriaSetup(Criteria cr,StoreFilter filter,String code,boolean isLoad){
		cr.createAlias("row.head", "head");
		cr.createAlias("head.document", "document");
		cr.createAlias("row.product", "product");
		cr.createAlias("document.storemovement", "storemovement");
		cr.add(Restrictions.eq("row.productcode", code));
		if (isLoad == true)
			cr.add(Restrictions.disjunction(Restrictions.eq("storemovement.genericLoad", true),Restrictions.eq("storemovement.comebackLoad", true),Restrictions.eq("storemovement.internalLoad", true),Restrictions.eq("storemovement.supplierLoad", true)));
		else
			cr.add(Restrictions.disjunction(Restrictions.eq("storemovement.genericUnload", true),Restrictions.eq("storemovement.expiredUnload", true),Restrictions.eq("storemovement.internalUnload", true),Restrictions.eq("storemovement.customerUnload", true)));	
		if (filter.getFromDate() != null && filter.getFromDate().equals("") != true && filter.getToDate() != null &&   filter.getToDate().equals("") != true  ){
			cr.add(Restrictions.between("head.date", DataUtilConverter.convertDateFromString(filter.getFromDate()), DataUtilConverter.convertDateFromString(filter.getToDate())));
		}else if (filter.getFromDate() != null && filter.getFromDate().equals("") != true){
			cr.add(Restrictions.eq("head.date", DataUtilConverter.convertDateFromString(filter.getFromDate())));
		}else if (filter.getToDate() != null && filter.getToDate().equals("") != true){
			cr.add(Restrictions.eq("head.date", DataUtilConverter.convertDateFromString(filter.getToDate())));
		}
	}
	private double setQuantity(StoreFilter filter,Storage s,StorageSerialCode sc,boolean isLoad){
		double qta=0;
		for (Iterator<UnitMeasureProduct> i = s.getProduct().getUms().iterator();i.hasNext(); ){
			UnitMeasureProduct ssc = i.next();
			qta = qta + (setQuantityProductCode(filter, ssc.getCode(), sc, isLoad)*ssc.getConversion());
		}
		return qta;
	}
	private double setQuantityProductCode(StoreFilter filter,String code,StorageSerialCode sc,boolean isLoad){
		double qta=0;
		Session session = HibernateUtils.getSessionFactory().openSession();
		ArrayList<Storage> list = new ArrayList<Storage>();
		try{
			Criteria cr = session.createCriteria(TblRow.class,"row");
			criteriaSetup(cr, filter, code,isLoad);
			if (sc != null){
				cr.add(Restrictions.eq("row.serialnumber", sc.getSerialcode()));
			}
			try{
				qta =(Double)cr.setProjection(Projections.sum("row.quantity")).uniqueResult();
			}catch(Exception e){
				qta = 0;
			}
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}finally{
			session.close();
		}
		return qta;
	}
	public void saveEcCommerce(ProductEcConfig ecp){
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			TblProductEcConfig st = new TblProductEcConfig();
			st.convertToTable(ecp);
			session.saveOrUpdate(st);
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
}
