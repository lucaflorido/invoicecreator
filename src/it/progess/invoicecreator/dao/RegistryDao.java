package it.progess.invoicecreator.dao;

import it.progess.invoicecreator.hibernate.DataUtilConverter;
import it.progess.invoicecreator.hibernate.HibernateUtils;
import it.progess.invoicecreator.pojo.TblCompany;
import it.progess.invoicecreator.pojo.TblBank;
import it.progess.invoicecreator.pojo.TblContact;
import it.progess.invoicecreator.pojo.TblCustomer;
import it.progess.invoicecreator.pojo.TblDestination;
import it.progess.invoicecreator.pojo.TblEcDelivery;
import it.progess.invoicecreator.pojo.TblHead;
import it.progess.invoicecreator.pojo.TblList;
import it.progess.invoicecreator.pojo.TblListCustomer;
import it.progess.invoicecreator.pojo.TblListProduct;
import it.progess.invoicecreator.pojo.TblPaymentSolution;
import it.progess.invoicecreator.pojo.TblProduct;
import it.progess.invoicecreator.pojo.TblPromoter;
import it.progess.invoicecreator.pojo.TblRow;
import it.progess.invoicecreator.pojo.TblSupplier;
import it.progess.invoicecreator.pojo.TblTransporter;
import it.progess.invoicecreator.pojo.TblUnitMeasureProduct;
import it.progess.invoicecreator.properties.GECOParameter;
import it.progess.invoicecreator.properties.MailParameter;
import it.progess.invoicecreator.vo.Address;
import it.progess.invoicecreator.vo.BankContact;
import it.progess.invoicecreator.vo.CategoryCustomer;
import it.progess.invoicecreator.vo.Company;
import it.progess.invoicecreator.vo.Bank;
import it.progess.invoicecreator.vo.Contact;
import it.progess.invoicecreator.vo.Customer;
import it.progess.invoicecreator.vo.Destination;
import it.progess.invoicecreator.vo.DraftElement;
import it.progess.invoicecreator.vo.GECOError;
import it.progess.invoicecreator.vo.GECOObject;
import it.progess.invoicecreator.vo.GECOSuccess;
import it.progess.invoicecreator.vo.GroupCustomer;
import it.progess.invoicecreator.vo.Head;
import it.progess.invoicecreator.vo.ListCustomer;
import it.progess.invoicecreator.vo.ListProduct;
import it.progess.invoicecreator.vo.NewList;
import it.progess.invoicecreator.vo.PaginationObject;
import it.progess.invoicecreator.vo.PaymentSolution;
import it.progess.invoicecreator.vo.Product;
import it.progess.invoicecreator.vo.ProductDatePrice;
import it.progess.invoicecreator.vo.Promoter;
import it.progess.invoicecreator.vo.Role;
import it.progess.invoicecreator.vo.Row;
import it.progess.invoicecreator.vo.Supplier;
import it.progess.invoicecreator.vo.Transporter;
import it.progess.invoicecreator.vo.UnitMeasure;
import it.progess.invoicecreator.vo.UnitMeasureProduct;
import it.progess.invoicecreator.vo.User;
import it.progess.invoicecreator.vo.UserCustomer;
import it.progess.invoicecreator.vo.UserPromoter;
import it.progess.invoicecreator.vo.filter.HeadFilter;
import it.progess.invoicecreator.vo.filter.PagesFilter;
import it.progess.invoicecreator.vo.filter.customer.SelectCustomerList;
import it.progess.invoicecreator.vo.filter.product.SelectProductsFilter;
import it.progess.invoicecreator.vo.filter.supplier.SelectSupplierList;
import it.progess.invoicecreator.vo.helpobject.ProductBasicPricesCalculation;

import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import javamailhelper.config.SMTPServerConfiguration;
import javamailhelper.message.EMailMessage;
import javamailhelper.runner.EMailSender;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;



public class RegistryDao {
	/*****
	 * Get List of Company 
	 */
	public ArrayList<Company> getCompanyList(){
		Session session = HibernateUtils.getSessionFactory().openSession();
		ArrayList<Company> list = new ArrayList<Company>();
		try{
			Criteria cr = session.createCriteria(TblCompany.class);
			List<TblCompany> companys = cr.list();
			if (companys.size() > 0){
				for (Iterator<TblCompany> iterator = companys.iterator(); iterator.hasNext();){
					TblCompany tblcompany = iterator.next();
					Company company = new Company();
					company.convertFromTable(tblcompany);
					list.add(company);
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
	public Company getCompany(int idcompany){
		Session session = HibernateUtils.getSessionFactory().openSession();
		ArrayList<Company> list = new ArrayList<Company>();
		Company company = new Company();
		try{
			Criteria cr = session.createCriteria(TblCompany.class,"company");
			cr.add(Restrictions.eq("company.idCompany", idcompany));
			List<TblCompany> companys = cr.list();
			if (companys.size() > 0){
				for (Iterator<TblCompany> iterator = companys.iterator(); iterator.hasNext();){
					TblCompany tblcompany = iterator.next();
					
					company.convertFromTable(tblcompany);
					list.add(company);
				}
			}
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}finally{
			session.close();
		}
		return company;
	}
	public Company getCompany(String code){
		Session session = HibernateUtils.getSessionFactory().openSession();
		ArrayList<Company> list = new ArrayList<Company>();
		Company company = new Company();
		try{
			Criteria cr = session.createCriteria(TblCompany.class,"company");
			cr.add(Restrictions.eq("company.code", code));
			List<TblCompany> companys = cr.list();
			if (companys.size() > 0){
				for (Iterator<TblCompany> iterator = companys.iterator(); iterator.hasNext();){
					TblCompany tblcompany = iterator.next();
					
					company.convertFromTable(tblcompany);
					list.add(company);
				}
			}
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}finally{
			session.close();
		}
		return company;
	}
	/***
	 * Save update Companys
	 * **/
	public GECOObject saveUpdatesCompany(Company sms){
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
				if (sms.control() == null ){
					TblCompany tblsm = new TblCompany();
					tblsm.convertToTable(sms);
					if (tblsm.getCode() == null || tblsm.getCode() == ""){
						UUID ui = UUID.randomUUID();
						tblsm.setCode(ui.toString());
					}
					session.saveOrUpdate(tblsm);
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
		return new GECOSuccess();
	}
	public GECOObject saveUpdatesCompany(Company sms,TblEcDelivery tec){
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
				if (sms.control() == null ){
					TblCompany tblsm = new TblCompany();
					tblsm.convertToTableSave(sms);
					tblsm.setEcdelivery(tec);
					if (tblsm.getCode() == null || tblsm.getCode() == ""){
						UUID ui = UUID.randomUUID();
						tblsm.setCode(ui.toString());
					}
					session.saveOrUpdate(tblsm);
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
		return new GECOSuccess();
	}
	/***
	 * DELETE A SINGLE Tblcompany
	 * **/
	public Boolean deleteCompany(Company sm){
		TblCompany tblsm = new TblCompany();
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
	/*****
	 * Get List of Bank 
	 */
	public ArrayList<Bank> getBankList(){
		Session session = HibernateUtils.getSessionFactory().openSession();
		ArrayList<Bank> list = new ArrayList<Bank>();
		try{
			Criteria cr = session.createCriteria(TblBank.class);
			List<TblBank> banks = cr.list();
			if (banks.size() > 0){
				for (Iterator<TblBank> iterator = banks.iterator(); iterator.hasNext();){
					TblBank tblbank = iterator.next();
					Bank bank = new Bank();
					bank.convertFromTable(tblbank);
					list.add(bank);
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
	 * Save update Banks
	 * **/
	public GECOObject saveUpdatesBank(Bank sm){
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			if (sm.control() == null ){
					TblBank tblsm = new TblBank();
					tblsm.convertToTable(sm);
					session.saveOrUpdate(tblsm);
			}else{
				if (tx!= null) tx.rollback();
				//session.close();
				return sm.control();
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
		return new GECOSuccess();
	}
	/***
	 * DELETE A SINGLE Tblbank
	 * **/
	public Boolean deleteBank(Bank sm){
		TblBank tblsm = new TblBank();
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
	/**
	 * GET A SINGLE BANK
	 * **/
	public Bank getSingleBank(int idbank){
		Session session = HibernateUtils.getSessionFactory().openSession();
		Bank bank = new Bank();
		try{
			
			Criteria cr = session.createCriteria(TblBank.class,"bank");
			cr.add(Restrictions.eq("idBank", idbank));
			cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List banks = cr.list();
			if (banks.size() > 0){
				
				bank.convertFromTable((TblBank)banks.get(0));
				
			}
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
			
		}finally{
			session.close();
		}
		return bank;
	}
	

	public GECOObject getProductList(SelectProductsFilter filter,User user){
		Session session = HibernateUtils.getSessionFactory().openSession();
		TreeSet<Product> list = new TreeSet<Product>();
		GECOObject obj = null;
		try{
			Criteria cr = setProductCriteria(filter, session,user);
			List<TblProduct> products = cr.list();
			if (products.size() > 0){
				for (Iterator<TblProduct> iterator = products.iterator(); iterator.hasNext();){
					TblProduct tblproduct = iterator.next();
					Product product = new Product();
					product.convertFromTable(tblproduct);
					list.add(product);
				}
			}
			obj = new GECOSuccess(list);
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			obj = new GECOError(GECOParameter.ERROR_HIBERNATE,"Errore nei dati ");
		}finally{
			session.close();
		}
		return obj;
	}
	private Criteria setProductCriteria(SelectProductsFilter filter,Session session,User user){
		Criteria cr = session.createCriteria(TblProduct.class,"product");
		cr =  setProductCriteria(cr,filter,session,user.getCompany().getCode(),"product");
		cr.addOrder(Order.asc("product.code"));
		cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return cr;
	}
	private Criteria setProductCriteria(SelectProductsFilter filter,Session session,String key){
		Criteria cr = session.createCriteria(TblProduct.class,"product");
		cr =  setProductCriteria(cr,filter,session,key,"product");
		cr.addOrder(Order.asc("product.code"));
		cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return cr;
	}
	private Criteria setProductCriteria(Criteria cr,SelectProductsFilter filter,Session session,String key,String table_name){
		//cr.add(Restrictions.eq("product.company.code",key ));
		cr.createAlias("product.company", "company");
		cr.add(Restrictions.eq("company.code",key ));

		cr.setFirstResult(filter.getPagefilter().startelement);
		cr.setMaxResults(filter.getPagefilter().pageSize);
		if (filter.getBrand() != null){
			cr.add(Restrictions.eq(table_name+".brand.idBrand", filter.getBrand().getIdBrand()));
			
		}
		if (filter.getRegion() != null){
			cr.add(Restrictions.eq(table_name+".region.idRegion", filter.getRegion().getIdRegion()));
			
		}
		if (filter.getCategory() != null){
			cr.add(Restrictions.eq(table_name+".category.idCategoryProduct", filter.getCategory().getIdCategoryProduct()));
			
		}
		if (filter.getSubcategory() != null){
			cr.add(Restrictions.eq(table_name+".subcategory.idSubCategoryProduct", filter.getSubcategory().getIdSubCategoryProduct()));
		}
		if (filter.getGroup() != null && filter.getGroup().getIdGroupProduct() != 0){
			cr.add(Restrictions.eq(table_name+".group.idGroupProduct", filter.getGroup().getIdGroupProduct()));
		}
		if (filter.getSupplier() != null){
			cr.add(Restrictions.eq(table_name+".supplier.idSupplier", filter.getSupplier().getIdSupplier()));
		}
		if (filter.getSearchstring() != null && filter.getSearchstring().equals("") == false){
			cr.add(Restrictions.disjunction(Restrictions.like(table_name+".code","%"+  filter.getSearchstring()+"%"),Restrictions.like(table_name+".description","%"+ filter.getSearchstring()+"%")));
		}

		/*cr.addOrder(Order.asc(table_name+".code"));
		cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);*/
		return cr;
	}
	public ArrayList<Product> getProductList(User user){
		Session session = HibernateUtils.getSessionFactory().openSession();
		ArrayList<Product> list = new ArrayList<Product>();
		try{
			Criteria cr = session.createCriteria(TblProduct.class,"product");
			cr.add(Restrictions.eq("product.company.idCompany",user.getCompany().getIdCompany() ));
			cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List<TblProduct> products = cr.list();
			if (products.size() > 0){
				for (Iterator<TblProduct> iterator = products.iterator(); iterator.hasNext();){
					TblProduct tblproduct = iterator.next();
					Product product = new Product();
					product.convertFromTable(tblproduct);
					list.add(product);
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
	public ArrayList<it.progess.invoicecreator.vo.List> getPublicList(String key){
		 
			Session session = HibernateUtils.getSessionFactory().openSession();
			ArrayList<it.progess.invoicecreator.vo.List> list = new ArrayList<it.progess.invoicecreator.vo.List>();
			try{
				Criteria cr = session.createCriteria(TblList.class,"list");
				cr.add(Restrictions.eq("list.active",true));
				cr.add(Restrictions.eq("list.publish",true));
				cr.createAlias("list.company", "company");
				cr.add(Restrictions.eq("company.code",key ));
				cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
				List<TblList> lists = cr.list();
				if (lists.size() > 0){
					for (Iterator<TblList> iterator = lists.iterator(); iterator.hasNext();){
						TblList tbllist = iterator.next();
						it.progess.invoicecreator.vo.List listobj = new it.progess.invoicecreator.vo.List();
						listobj.convertFromTable(tbllist);
						list.add(listobj);
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
	public ArrayList<DraftElement> getProductPublicList(SelectProductsFilter filter,String key){
		Session session = HibernateUtils.getSessionFactory().openSession();
		ArrayList<DraftElement> list = new ArrayList<DraftElement>();
		ArrayList<it.progess.invoicecreator.vo.List> lists = getPublicList(key);
		try{
			Criteria cr = setProductCriteria(filter, session,key); /*session.createCriteria(TblProduct.class,"product");
			cr.createAlias("product.company", "company");
			cr.add(Restrictions.eq("company.code",key ));*/
			cr.add(Restrictions.eq("product.publish",true ));
			
			cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List<TblProduct> products = cr.list();
			if (products.size() > 0){
				for (Iterator<TblProduct> iterator = products.iterator(); iterator.hasNext();){
					TblProduct tblproduct = iterator.next();
					Product product = new Product();
					product.convertFromTable(tblproduct);
					product.setPhoto("data:image/png;base64,"+product.getPhoto());
					DraftElement de = new DraftElement();
					if (lists.size() > 0){
						it.progess.invoicecreator.vo.List l = lists.get(0);
						float price = getProductPriceList(product.getIdProduct(), l.getIdList());
						if (price > 0){
							product.setListprice(price);
						}else{
							product.setListprice(product.getSellprice());
						}
					}else{
						product.setListprice(product.getSellprice());
					}
					de.setPrice(product.getListprice());
					de.calculateEndPrice(product);
					list.add(de);
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
	public ArrayList<Product> getProductFilteredList(SelectProductsFilter filter,User user){
		Session session = HibernateUtils.getSessionFactory().openSession();
		ArrayList<Product> list = new ArrayList<Product>();
		try{
			Criteria cr = session.createCriteria(TblProduct.class,"product");
			
			
			cr.add(Restrictions.eq("product.company.idCompany",user.getCompany().getIdCompany() ));
			
			if (filter.getGroup() != null){
				cr.createAlias("product.group", "group");
				cr.add(Restrictions.eq("group.idGroupProduct", filter.getGroup().getIdGroupProduct()));
			}
				
			if (filter.getCategory() != null){
				cr.createAlias("product.category", "category");
				cr.add(Restrictions.eq("category.idCategoryProduct", filter.getCategory().getIdCategoryProduct()));
			}
				
			if (filter.getBrand() != null){
				cr.createAlias("product.brand", "brand");
				cr.add(Restrictions.eq("brand.idBrand", filter.getBrand().getIdBrand()));
			}
				
			if (filter.getSupplier() != null){
				cr.createAlias("product.supplier", "supplier");
				cr.add(Restrictions.eq("supplier.idSupplier", filter.getSupplier().getIdSupplier()));
			}
				
			List<TblProduct> products = cr.list();
			if (products.size() > 0){
				for (Iterator<TblProduct> iterator = products.iterator(); iterator.hasNext();){
					TblProduct tblproduct = iterator.next();
					Product product = new Product();
					product.convertFromTable(tblproduct);
					list.add(product);
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
	public GECOObject calculateIncrementProducts(SelectProductsFilter filter,User user){
		try{
			ArrayList<Product> prods = getProductFilteredList(filter,user);
			ProductBasicPricesCalculation c = new ProductBasicPricesCalculation();
			for (Iterator<Product> it = prods.iterator();it.hasNext();){
				Product prod = it.next();
				if (filter.isPercentage() == true)
					c.percentageIncrement(filter.getIncrement(), prod);
				else
					c.amountIncrement(filter.getIncrement(), prod);
				this.saveUpdatesProduct(prod,user);
			}
		}catch (Exception e){
			return new GECOError(GECOParameter.ERROR_CALCULATION, e.getMessage());
		}
		
		return new GECOSuccess(true);
	}
	public PaginationObject getListPagesNumber(int size,User user){
		int pages = 0;
		int counters = 0;
		Session session = HibernateUtils.getSessionFactory().openSession();
		try{
			Criteria cr = session.createCriteria(TblProduct.class,"prod");
			cr.add(Restrictions.eq("prod.company.idCompany",user.getCompany().getIdCompany() ));
			counters = ((Long) cr.setProjection(Projections.rowCount()).uniqueResult()).intValue();
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}finally{
			session.close();
		}
		if (counters > size){
			pages = counters / size;
		}else{
			pages = 0;
		}
		PaginationObject po = new PaginationObject();
		po.setPages(pages);
		po.setTotalitems(counters);
		return po;
	}
	public PaginationObject getListProductsPagesNumber(int size,int idlist){
		int pages = 0;
		int counters = 0;
		Session session = HibernateUtils.getSessionFactory().openSession();
		try{
			Criteria cr = session.createCriteria(TblListProduct.class,"prod");
			cr.add(Restrictions.eq("prod.list.idList", idlist));
			counters = ((Long) cr.setProjection(Projections.rowCount()).uniqueResult()).intValue();
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}finally{
			session.close();
		}
		if (counters > size){
			pages = counters / size;
		}else{
			pages = 0;
		}
		PaginationObject po = new PaginationObject();
		po.setPages(pages);
		po.setTotalitems(counters);
		return po;
	}

	public PaginationObject getListPublicPagesNumber(int size,String key){
		int pages = 0;
		int counters = 0;
		Session session = HibernateUtils.getSessionFactory().openSession();
		try{
			Criteria cr = session.createCriteria(TblProduct.class,"prod");
			cr.createAlias("prod.company","company" );
			cr.add(Restrictions.eq("company.code",key ));
			cr.add(Restrictions.eq("prod.publish",true));
			counters = ((Long) cr.setProjection(Projections.rowCount()).uniqueResult()).intValue();
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}finally{
			session.close();
		}
		if (counters > size){
			pages = counters / size;
		}else{
			pages = 0;
		}
		PaginationObject po = new PaginationObject();
		po.setPages(pages);
		po.setTotalitems(counters);
		return po;
	}
	public PaginationObject getListPublicProductsPagesNumber(int size,int idlist){
		int pages = 0;
		int counters = 0;
		Session session = HibernateUtils.getSessionFactory().openSession();
		try{
			Criteria cr = session.createCriteria(TblListProduct.class,"prod");
			cr.add(Restrictions.eq("prod.list.idList", idlist));
			cr.add(Restrictions.eq("prod.publish",true));
			counters = ((Long) cr.setProjection(Projections.rowCount()).uniqueResult()).intValue();
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}finally{
			session.close();
		}
		if (counters > size){
			pages = counters / size;
		}else{
			pages = 0;
		}
		PaginationObject po = new PaginationObject();
		po.setPages(pages);
		po.setTotalitems(counters);
		return po;
	}

	/*****
	 * Search Products 
	 */
	public ArrayList<UnitMeasureProduct> searchProducts(String value,User user){
		Session session = HibernateUtils.getSessionFactory().openSession();
		ArrayList<UnitMeasureProduct> list = new ArrayList<UnitMeasureProduct>();
		try{
			Criteria cr = session.createCriteria(TblUnitMeasureProduct.class,"um");
			cr.createAlias("um.product", "prod");
			cr.add(Restrictions.eq("prod.company.idCompany",user.getCompany().getIdCompany() ));
			cr.add(Restrictions.disjunction().add(Restrictions.like("um.code", "%" + value + "%")).add(Restrictions.like("prod.description", "%" + value + "%")).add(Restrictions.like("prod.code", "%" + value + "%")));
			cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List<TblUnitMeasureProduct> products = cr.list();
			if (products.size() > 0){
				for (Iterator<TblUnitMeasureProduct> iterator = products.iterator(); iterator.hasNext();){
					TblUnitMeasureProduct tblproduct = iterator.next();
					UnitMeasureProduct product = new UnitMeasureProduct();
					product.convertFromTableSearch(tblproduct);
					list.add(product);
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
	public ArrayList<UnitMeasureProduct> searchProducts(String value,Head h,User user){
		ArrayList<UnitMeasureProduct> list = searchProducts(value, user);
		for(Iterator<UnitMeasureProduct> it = list.iterator();it.hasNext();){
			UnitMeasureProduct ump = it.next();
			float price = new RegistryDao().getProductPriceList(ump.getProduct().getIdProduct(), h.getList().getIdList());
			if (price > 0){
				ump.getProduct().setListprice(price);
			}else{
				ump.getProduct().setListprice(ump.getProduct().getSellprice());
			}
			
			
		}
		for(Iterator<UnitMeasureProduct> it = list.iterator();it.hasNext();){
			UnitMeasureProduct umpf = it.next();
			if (h.getRows() != null){
				for (Iterator<Row> ir = h.getRows().iterator(); ir.hasNext();){
					Row r = ir.next();
					if (umpf.getCode().equals(r.getProductcode())){
						umpf.setStatus(1);
						umpf.setQuantity(r.getQuantity());
					}
				}
			}
		}
		return list;
	}
	/***
	 * Save update Products
	 * **/
	public GECOObject saveUpdatesProduct(Product sm,User user){
		int id=0;
		if (sm.control() == null){
			sm.updateCode();
			sm.setCompany(user.getCompany());
			Session session = HibernateUtils.getSessionFactory().openSession();
			Transaction tx = null;
			try{
				tx = session.beginTransaction();
				if (sm.getCode() != "" && sm.getCode() != null ){
						TblProduct tblsm = new TblProduct();
						tblsm.convertToTable(sm);
						session.saveOrUpdate(tblsm);
						id=tblsm.getIdProduct();
				}
				tx.commit();
			}catch(HibernateException e){
				System.err.println("ERROR IN LIST!!!!!!");
				if (tx!= null) tx.rollback();
				e.printStackTrace();
				session.close();
				return new GECOError(GECOParameter.ERROR_HIBERNATE,"Errore nel salvataggio dei dati");
			}finally{
				if (session.isOpen() == true)
					session.close();
			}
		}else{
			return sm.control();
		}
		return new GECOSuccess(id);
	}
	
	/***
	 * DELETE A SINGLE Tblproduct
	 * **/
	public Boolean deleteProduct(Product sm){
		TblProduct tblsm = new TblProduct();
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
	public Boolean deleteUMProduct(UnitMeasureProduct sm){
		TblUnitMeasureProduct tblsm = new TblUnitMeasureProduct();
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
	public Boolean deleteListProduct(ListProduct sm){
		TblListProduct tblsm = new TblListProduct();
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
	/**
	 * GET A SINGLE PRODUCT
	 * **/
	public Product getSingleProduct(int idproduct){
		Session session = HibernateUtils.getSessionFactory().openSession();
		Product product = new Product();
		try{
			
			Criteria cr = session.createCriteria(TblProduct.class,"product");
			cr.add(Restrictions.eq("idProduct", idproduct));
			cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List products = cr.list();
			if (products.size() > 0){
				
				product.convertFromTableSngle((TblProduct)products.get(0));
				
			}
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
			
		}finally{
			session.close();
		}
		return product;
	}
	/**
	 * GET A SINGLE PRODUCT
	 * **/
	public Product getSingleCodeProduct(String code,int idList,Head head,User user){
		Session session = HibernateUtils.getSessionFactory().openSession();
		Product product = new Product();
		try{			
			Criteria cr = session.createCriteria(TblProduct.class,"product");
			cr.createAlias("product.ums", "um");
			cr.add(Restrictions.eq("product.company.idCompany",user.getCompany().getIdCompany() ));
			cr.add(Restrictions.eq("um.code", code));
			cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List products = cr.list();
			if (products.size() == 1){
				product.convertFromTable((TblProduct)products.get(0));
				setPriceHistory(head, product, session);
			}
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
			
		}finally{
			session.close();
		}
		if (product.getIdProduct() > 0){
			product.setListprice(getProductPriceList(product.getIdProduct(), idList));
			product = calculateUM(product, code);
		}
		try{
			product.setStorage(new StoreDao().getStorageProduct(product));
		}catch(Exception e){
			
		}
		return product;
	}
	public UnitMeasureProduct getSingleCodeUMProduct(String code,User user){
		Session session = HibernateUtils.getSessionFactory().openSession();
		UnitMeasureProduct product = new UnitMeasureProduct();
		try{			
			Criteria cr = session.createCriteria(TblUnitMeasureProduct.class,"product");
			cr.add(Restrictions.eq("product.code", code));
			cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List products = cr.list();
			if (products.size() == 1){
				product.convertFromTableSearch((TblUnitMeasureProduct)products.get(0));
			}
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
			
		}finally{
			session.close();
		}
		
		return product;
	}
	public Product getSingleCodeProduct(String code,User user){
		Session session = HibernateUtils.getSessionFactory().openSession();
		Product product = new Product();
		try{			
			Criteria cr = session.createCriteria(TblProduct.class,"product");
			cr.createAlias("product.ums", "um");
			cr.add(Restrictions.eq("product.company.idCompany",user.getCompany().getIdCompany() ));
			cr.add(Restrictions.eq("um.code", code));
			cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List products = cr.list();
			if (products.size() == 1){
				product.convertFromTable((TblProduct)products.get(0));
			}
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
			
		}finally{
			session.close();
		}
		
		return product;
	}
	public Product getSingleCodeProductFull(String code,User user){
		Session session = HibernateUtils.getSessionFactory().openSession();
		Product product = new Product();
		try{			
			Criteria cr = session.createCriteria(TblProduct.class,"product");
			cr.createAlias("product.ums", "um");
			cr.add(Restrictions.eq("product.company.idCompany",user.getCompany().getIdCompany() ));
			cr.add(Restrictions.eq("um.code", code));
			cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List products = cr.list();
			if (products.size() == 1){
				product.convertFromTableSngle((TblProduct)products.get(0));
			}
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
			
		}finally{
			session.close();
		}
		
		return product;
	}
	private void setPriceHistory(Head head,Product prod,Session session){
		if (head == null){
			return; 
		}
		if (head.getCustomer() == null){
			return;
		}
		if (head.getDate() == null || head.getDate().equals("")){
			return;
		}
		prod.setPricehistory(new HashSet<ProductDatePrice>());
		Date toDate = DataUtilConverter.convertDateFromString(head.getDate());
		Calendar c = Calendar.getInstance(); 
		c.setTime(toDate); 
		c.add(Calendar.MONTH, -3);
		Date fromDate = c.getTime();
		try{			
			Criteria cr = session.createCriteria(TblRow.class,"row");
			cr.createAlias("row.head", "head");
			cr.add(Restrictions.eq("head.customer.idCustomer", head.getCustomer().getIdCustomer()));
			cr.add(Restrictions.eq("head.document.idDocument", head.getDocument().getIdDocument()));
			cr.add(Restrictions.eq("row.product.idProduct", prod.getIdProduct()));
			cr.add(Restrictions.between("head.date", fromDate, toDate));
			//cr.setProjection(Projections.distinct(Projections.property("row.price")));
			cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List<TblRow> rows = cr.list();
			for (Iterator<TblRow> it = rows.iterator();it.hasNext();){
				TblRow row = it.next();
				ProductDatePrice pdp = new ProductDatePrice(DataUtilConverter.convertStringFromDate(row.getHead().getDate()),HibernateUtils.roundfloat(row.getPrice()));
				prod.getPricehistory().add(pdp);
			}
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}
	}
	public float getProductPriceList(int idProduct,int idList){
		Session session = HibernateUtils.getSessionFactory().openSession();
		float price = 0;
		try{			
			Criteria cr = session.createCriteria(TblListProduct.class,"listproduct");
			cr.createAlias("listproduct.product", "prod");
			cr.createAlias("listproduct.list", "list");
			cr.add(Restrictions.eq("prod.idProduct", idProduct));
			cr.add(Restrictions.eq("list.idList", idList));
			cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List listproducts = cr.list();
			if (listproducts.size() > 0){
				price = ((TblListProduct)listproducts.get(0)).getPrice();
				//No Price in LIST take the purchaseprice
				
			}
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
			
		}finally{
			session.close();
		}
		return price;
	}
	private Product calculateUM(Product prod,String code){
		for (Iterator<UnitMeasureProduct> iterator = prod.getUms().iterator();iterator.hasNext();){
			UnitMeasureProduct ump = iterator.next();
			if(ump.getCode().equals(code)){
				prod.setUmselected(ump.getUm());
				prod.setConversionrate(ump.getConversion());
				break;
			}
		}
		if (prod.getListprice() == 0){
			prod.setListprice(HibernateUtils.roundfloat(prod.getSellprice()));
		}
		if (prod.getConversionrate() > 0){
			prod.setListprice(HibernateUtils.roundfloat(prod.getListprice() * prod.getConversionrate()));
		}
		return prod;
	}
	/*****
	 * Get List of List 
	 */
	public ArrayList<it.progess.invoicecreator.vo.List> getListList(User user){
		Session session = HibernateUtils.getSessionFactory().openSession();
		ArrayList<it.progess.invoicecreator.vo.List> list = new ArrayList<it.progess.invoicecreator.vo.List>();
		try{
			Criteria cr = session.createCriteria(TblList.class,"list");
			cr.add(Restrictions.eq("list.active",true));
			//cr.createAlias("list.listproduct", "listproduct");
			//cr.add(Restrictions.disjunction(Restrictions.isNull("listproduct"),Restrictions.eq("listproduct.active",true)));
			cr.add(Restrictions.eq("list.company.idCompany",user.getCompany().getIdCompany()));
			cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List<TblList> lists = cr.list();
			if (lists.size() > 0){
				for (Iterator<TblList> iterator = lists.iterator(); iterator.hasNext();){
					TblList tbllist = iterator.next();
					it.progess.invoicecreator.vo.List listobj = new it.progess.invoicecreator.vo.List();
					listobj.convertFromTable(tbllist);
					list.add(listobj);
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
	public ArrayList<it.progess.invoicecreator.vo.List> getListListNoProduct(User user,Product p){
		ArrayList<it.progess.invoicecreator.vo.List> list = new ArrayList<it.progess.invoicecreator.vo.List>();
		list.addAll(getListEmpty(user, p));
		//list.addAll(getListNoProduct(user, p));
		return list;
	}
	private ArrayList<it.progess.invoicecreator.vo.List> getListNoProduct(User user,Product p){
		Session session = HibernateUtils.getSessionFactory().openSession();
		ArrayList<it.progess.invoicecreator.vo.List> list = new ArrayList<it.progess.invoicecreator.vo.List>();
		try{
			
			
			DetachedCriteria subcr = DetachedCriteria.forClass(TblList.class,"list");
			subcr.add(Restrictions.eq("list.active",true));
			subcr.add(Restrictions.eq("list.company.idCompany",user.getCompany().getIdCompany()));
			subcr.createAlias("list.listproduct", "listproduct");
			subcr.add(Restrictions.eq("listproduct.product.idProduct", p.getIdProduct()));
			subcr.setProjection(Projections.property("idList"));
			
			Criteria cr = session.createCriteria(TblList.class,"list");
			cr.add(Restrictions.eq("list.active",true));
			//cr.createAlias("list.listproduct", "listproduct");
			//cr.add(Restrictions.disjunction(Restrictions.isNull("listproduct"),Restrictions.eq("listproduct.active",true)));
			cr.add(Restrictions.eq("list.company.idCompany",user.getCompany().getIdCompany()));
			//cr.createAlias("list.listproduct", "listproduct");
			
			//cr.add(Restrictions.ne("listproduct.product.idProduct", p.getIdProduct()));
			cr.add(Property.forName("idList").notIn(subcr));
			cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List<TblList> lists = cr.list();
			if (lists.size() > 0){
				for (Iterator<TblList> iterator = lists.iterator(); iterator.hasNext();){
					TblList tbllist = iterator.next();
					it.progess.invoicecreator.vo.List listobj = new it.progess.invoicecreator.vo.List();
					listobj.convertFromTable(tbllist);
					list.add(listobj);
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
	private ArrayList<it.progess.invoicecreator.vo.List> getListEmpty(User user,Product p){
		Session session = HibernateUtils.getSessionFactory().openSession();
		ArrayList<it.progess.invoicecreator.vo.List> list = new ArrayList<it.progess.invoicecreator.vo.List>();
		try{
			Criteria cr = session.createCriteria(TblList.class,"list");
			cr.add(Restrictions.eq("list.active",true));
			//cr.createAlias("list.listproduct", "listproduct");
			//cr.add(Restrictions.disjunction(Restrictions.isNull("listproduct"),Restrictions.eq("listproduct.active",true)));
			cr.add(Restrictions.eq("list.company.idCompany",user.getCompany().getIdCompany()));
			//cr.createAlias("list.listproduct", "listproduct");
			cr.add(Restrictions.isEmpty("list.listproduct"));
			cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List<TblList> lists = cr.list();
			if (lists.size() > 0){
				for (Iterator<TblList> iterator = lists.iterator(); iterator.hasNext();){
					TblList tbllist = iterator.next();
					it.progess.invoicecreator.vo.List listobj = new it.progess.invoicecreator.vo.List();
					listobj.convertFromTable(tbllist);
					list.add(listobj);
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
	 * Save update Lists
	 * **/
	public GECOObject saveUpdatesList(NewList nlist,User user){
		int id = 0;
		it.progess.invoicecreator.vo.List sm = nlist.getList();
		if (sm.getIdList() == 0 && sm.isFill()){
			this.setAllProductsInList(sm, user);
		}
		if (sm.control() == null){
			sm.setCompany(user.getCompany());
			Date lastDate = getDate(sm.getIdList());
			Session session = HibernateUtils.getSessionFactory().openSession();
			Transaction tx = null;
			tx = session.beginTransaction();
			try{
				if (lastDate != null){
					if (checkEqualDate(sm,lastDate) == true){
						sm.copyProducts(sm, false, nlist.isPercentage(), nlist.getValue());
					}else if (checkAfterDate(sm, lastDate) == true){
						sm.copyProducts(sm, true, nlist.isPercentage(), nlist.getValue());
					}
				}else{
					sm.setActive(true);
				}
				
				TblList tblsm = new TblList();
				tblsm.convertToTableForSaving(sm);
				session.saveOrUpdate(tblsm);
				id = tblsm.getIdList();
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
		return new GECOSuccess(id);
	}
	private void setAllProductsInList(it.progess.invoicecreator.vo.List l,User user){
		ArrayList<Product> prods = getProductList(user);
		l.setListproduct(new HashSet<ListProduct>());
		for (int i =0; i<prods.size();i++){
			Product p = prods.get(i);
			ListProduct lp = new ListProduct();
			lp.setProduct(p);
			float pricediff = p.getPurchaseprice() / 100f * l.getIncrement(); 
			lp.setPrice(p.getPurchaseprice() + pricediff);
			l.getListproduct().add(lp);
		}
	}
	public Date getDate(int idList){
		Session session = HibernateUtils.getSessionFactory().openSession();
		try{
			Criteria cr = session.createCriteria(TblList.class,"list");
			cr.add(Restrictions.eq("list.idList",idList));
			List<TblList> lists = cr.list();
			if (lists.size() > 0){
				return lists.get(0).getStartdate();
			}
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			session.close();
			throw new ExceptionInInitializerError(e);
		}finally{
			session.close();
		}
		
		return null;
	}
	public void saveOldList(it.progess.invoicecreator.vo.List list,Session session){
		try{
					TblList tblsm = new TblList();
					tblsm.convertToTableForSaving(list);
					tblsm.setActive(false);
					session.saveOrUpdate(tblsm);
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
		
			e.printStackTrace();
			session.close();
			throw new ExceptionInInitializerError(e);
		}finally{
			session.close();
		}
	
	}
	public boolean checkAfterDate(it.progess.invoicecreator.vo.List list,Date date){
		if (date != null)
		return DataUtilConverter.convertDateFromString(list.getStartdate()).after(date);
		else
			return true;
	}
	public boolean checkEqualDate(it.progess.invoicecreator.vo.List list,Date date){
		return DataUtilConverter.convertDateFromString(list.getStartdate()).equals(date);
	}
	/***
	 * DELETE A SINGLE Tbllist
	 * **/
	public Boolean deleteList(it.progess.invoicecreator.vo.List sm){
		TblList tblsm = new TblList();
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
	/**
	 * GET A SINGLE BANK
	 * **/
	public it.progess.invoicecreator.vo.List getSingleList(int idlist){
		Session session = HibernateUtils.getSessionFactory().openSession();
		it.progess.invoicecreator.vo.List list = new it.progess.invoicecreator.vo.List();
		try{
			
			Criteria cr = session.createCriteria(TblList.class,"list");
			cr.add(Restrictions.eq("list.idList", idlist));
			cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List lists = cr.list();
			if (lists.size() > 0){
				list.convertFromTable((TblList)lists.get(0));
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
	public it.progess.invoicecreator.vo.List getSingleListByCode(String code,User user){
		Session session = HibernateUtils.getSessionFactory().openSession();
		it.progess.invoicecreator.vo.List list = new it.progess.invoicecreator.vo.List();
		try{
			
			Criteria cr = session.createCriteria(TblList.class,"list");
			cr.add(Restrictions.eq("list.code", code));
			cr.add(Restrictions.eq("list.company.idCompany", user.getCompany().getIdCompany()));
			cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List lists = cr.list();
			if (lists.size() > 0){
				list.convertFromTable((TblList)lists.get(0));
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
	public it.progess.invoicecreator.vo.List getSingleList(int idlist, SelectProductsFilter filter,User user){
		Session session = HibernateUtils.getSessionFactory().openSession();
		it.progess.invoicecreator.vo.List list = new it.progess.invoicecreator.vo.List();
		try{
			
			Criteria cr = session.createCriteria(TblList.class,"list");
			cr.add(Restrictions.eq("list.idList", idlist));
			cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List lists = cr.list();
			if (lists.size() > 0){
				list.convertFromTable((TblList)lists.get(0));
			}
			
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
			
		}finally{
			session.close();
		}
		list.setListproduct(getSingleProductList(list.getIdList(),filter,user));
		return list;
	}
	public Set<ListProduct> getSingleProductList(int idlist, SelectProductsFilter filter,User user){
		Session session = HibernateUtils.getSessionFactory().openSession();
		Set<ListProduct> lists = new  HashSet<ListProduct>();
		try{
			
			Criteria cr = session.createCriteria(TblListProduct.class,"listproduct");
			cr.add(Restrictions.eq("listproduct.list.idList", idlist));
			cr.createAlias("listproduct.product", "product");
			cr = setProductCriteria(cr, filter, session, user.getCompany().getCode(), "product");

			cr.addOrder(Order.asc("product.code"));
			cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List list = cr.list();
			for (Iterator<TblListProduct> iterator = list.iterator(); iterator.hasNext();){
				TblListProduct tlp = iterator.next();
				ListProduct lp = new ListProduct();
				lp.convertFromTable(tlp);
				lists.add(lp);
			}
			
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
			
		}catch(Exception e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
			
		}finally{
			session.close();
		}
		return lists;
	}
	/*****
	 * Get List of List for Customer 
	 */
	public ArrayList<ListCustomer> getCustomerPriceList(int idCustomer){
		Session session = HibernateUtils.getSessionFactory().openSession();
		ArrayList<ListCustomer> list = new ArrayList<ListCustomer>();
		try{
			Criteria cr = session.createCriteria(TblListCustomer.class,"listcustomer");
			cr.add(Restrictions.eq("listcustomer.customer.idCustomer", idCustomer));
			List<TblListCustomer> listcustomers = cr.list();
			if (listcustomers.size() > 0){
				for (Iterator<TblListCustomer> iterator = listcustomers.iterator(); iterator.hasNext();){
					TblListCustomer listc = iterator.next();
					ListCustomer lc = new ListCustomer();
					//destination = getMockDestination();
					lc.convertFromTable(listc);
					list.add(lc);
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
	
	/*****
	 * Get List of Customer 
	 */
	public ArrayList<Customer> getCustomerList(User loggeduser){
		Session session = HibernateUtils.getSessionFactory().openSession();
		ArrayList<Customer> list = new ArrayList<Customer>();
		try{
			Criteria cr = session.createCriteria(TblCustomer.class,"customer");
			cr.add(Restrictions.eq("customer.company.idCompany", loggeduser.getCompany().getIdCompany()));
			List<TblCustomer> customers = cr.list();
			if (customers.size() > 0){
				for (Iterator<TblCustomer> iterator = customers.iterator(); iterator.hasNext();){
					TblCustomer tblcustomer = iterator.next();
					Customer customer = new Customer();
					//customer = getMockCustomer();
					customer.convertFromTable(tblcustomer);
					list.add(customer);
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
	public Customer getCustomerFromUser(User user){
		Session session = HibernateUtils.getSessionFactory().openSession();
		ArrayList<Customer> list = new ArrayList<Customer>();
		try{
			Criteria cr = session.createCriteria(TblCustomer.class,"customer");
			cr.add(Restrictions.eq("customer.contact.idcontact", user.getContact().getIdcontact()));
			List<TblCustomer> customers = cr.list();
			if (customers.size() > 0){
				for (Iterator<TblCustomer> iterator = customers.iterator(); iterator.hasNext();){
					TblCustomer tblcustomer = iterator.next();
					Customer customer = new Customer();
					customer.convertFromTableSingle(tblcustomer);
					list.add(customer);
				}
			}else{
				return null;
			}
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}finally{
			session.close();
		}
		return list.get(0);
	}
	/*****
	 * Get List of Customer 
	 */
	public ArrayList<Customer> getCustomerList(SelectCustomerList filter,User loggeduser){
		Session session = HibernateUtils.getSessionFactory().openSession();
		ArrayList<Customer> list = new ArrayList<Customer>();
		try{
			Criteria cr = session.createCriteria(TblCustomer.class,"customer");
			cr.add(Restrictions.eq("customer.company.idCompany", loggeduser.getCompany().getIdCompany()));
			if (filter.getGroup() != null)
				cr.add(Restrictions.eq("customer.group.idGroupCustomer", filter.getGroup().getIdGroupCustomer()));
			if (filter.getCategory() != null)
				cr.add(Restrictions.eq("customer.category.idCategoryCustomer", filter.getCategory().getIdCategoryCustomer()));
			cr.setFirstResult(filter.getPagefilter().startelement);
			cr.setMaxResults(filter.getPagefilter().pageSize);
			if (filter.getSearchstring() != null && filter.getSearchstring().equals("") == false){
				cr.add(Restrictions.disjunction(Restrictions.like("customer.customercode","%"+  filter.getSearchstring()+"%"),Restrictions.like("customer.customername","%"+ filter.getSearchstring()+"%")));
			}
			cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List<TblCustomer> customers = cr.list();
			if (customers.size() > 0){
				for (Iterator<TblCustomer> iterator = customers.iterator(); iterator.hasNext();){
					TblCustomer tblcustomer = iterator.next();
					Customer customer = new Customer();
					//customer = getMockCustomer();
					customer.convertFromTable(tblcustomer);
					list.add(customer);
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
	public ArrayList<Customer> getCustomerListByGroup(GroupCustomer gc){
		Session session = HibernateUtils.getSessionFactory().openSession();
		ArrayList<Customer> list = new ArrayList<Customer>();
		try{
			Criteria cr = session.createCriteria(TblCustomer.class,"customer");
			cr.add(Restrictions.eq("customer.group.idGroupCustomer", gc.getIdGroupCustomer()));
			List<TblCustomer> customers = cr.list();
			if (customers.size() > 0){
				for (Iterator<TblCustomer> iterator = customers.iterator(); iterator.hasNext();){
					TblCustomer tblcustomer = iterator.next();
					Customer customer = new Customer();
					//customer = getMockCustomer();
					customer.convertFromTable(tblcustomer);
					list.add(customer);
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
	 * Save update Customers
	 * **/
	public GECOObject saveUpdatesCustomer(Customer sm,User user){
		int id=0;
		if (sm.control() == null){
			GECOObject control = ControlDao.checkCustomer(sm, user);
			if (control.type.equals(GECOParameter.SUCCESS_TYPE)){
				Session session = HibernateUtils.getSessionFactory().openSession();
				Transaction tx = null;
				sm.setCompany(user.getCompany());
				try{
					tx = session.beginTransaction();
					
							TblCustomer tblsm = new TblCustomer();
							tblsm.convertToTableSingle(sm, tblsm);
							session.saveOrUpdate(tblsm);
							id=tblsm.getIdCustomer();
					
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
				return control;
			}
		}else{
			return sm.control();
		}
		return new GECOSuccess(id);
	}
	public GECOObject createUserCustomer(User loggeduser,UserCustomer uc,HttpServletRequest request){
		if (uc.control() != null){
			return uc.control();
		}
		User user = new User();
		Customer sm = uc.getCustomer();
		Role r = uc.getRole();
		user.setActive(false);

		user.setName(sm.getNameUser());
		user.setSurname(sm.getSurnameUser());
		user.setEmail(sm.getContact().getEmail1());
		user.setUsername(sm.getContact().getEmail1());
		user.setCompany(loggeduser.getCompany());
		user.setContact(sm.getContact());
		user.setRole(r);
		UserDao usdao = new UserDao();
		
		usdao.saveUpdate(user);
		/*EMailMessage message = new EMailMessage("lucaflorido@hotmail.com","lucaflorido@gmail.com","Test Email","http://localhost:8080/InvoiceCreator/rocchi/activate.html?code="+user.getCode());
		SMTPServerConfiguration config = new SMTPServerConfiguration("true","true","smtp.live.com");
		try{
			EMailSender.send(message, config, "svnf0rl0s");
			System.out.println("Email success");
		}catch(MessagingException ex){
			System.out.println(ex.getMessage());
		}*/
		user.setPassword(user.getUsername());
		return new MailDao().sendNewCustomerUser(loggeduser, user,MailParameter.NEW_USER_CUSTOMER_MAIL,request);
	}
	public GECOObject createCustomerFromUser(User user){
		RegistryDao rd = new RegistryDao();
		Customer sm = new Customer();
		if (user.getAddress() == null){
			user.setAddress(user.getDeliveryaddress());
		}
		sm.setCustomername(user.getName() + " " + user.getSurname());
		sm.setCustomercode(user.getTaxcode().substring(0,10));
		sm.setActive(true);
		sm.setCompany(user.getCompany());
		sm.setHasuser(true);
		user.setContact(new Contact());
		user.getContact().setEmail1(user.getEmail());
		user.getContact().setPhone1(user.getPhone());
		//user.getContact().setUser(user);
		sm.setIsprivate(true);
		sm.setContact(user.getContact());
		sm.setAddress(user.getAddress());
		sm.setTaxcode(user.getTaxcode());
		if(user.getDeliveryaddress().getCode() == null){
			UUID ui = UUID.randomUUID();
			user.getDeliveryaddress().setCode(ui.toString());
			Destination d = new Destination();
			d.setActive(true);
			d.setDestinationname(user.getDeliveryaddress().getAddress()+" "+user.getDeliveryaddress().getNumber());
			d.setDestinationcode("0001");
			d.setTaxcode(user.getTaxcode());
			d.setAddress(user.getDeliveryaddress());
			if (sm.getDestinations() == null){
				sm.setDestinations(new HashSet<Destination>());
			}
			sm.getDestinations().add(d);
		}
		return rd.saveUpdatesCustomer(sm, user);
	}
	public GECOObject createDestinationFromUser(User user){
		if(user.getDeliveryaddress().getCode() == null){
			RegistryDao rd = new RegistryDao();
			Customer sm = rd.getCustomerFromUser(user);
			UUID ui = UUID.randomUUID();
			user.getDeliveryaddress().setCode(ui.toString());
			Destination d = new Destination();
			d.setActive(true);
			d.setDestinationname(user.getDeliveryaddress().getAddress()+" "+user.getDeliveryaddress().getNumber());
			d.setDestinationcode("0001");
			d.setTaxcode(user.getTaxcode());
			d.setAddress(user.getDeliveryaddress());
			sm.setDestinations(new HashSet<Destination>(rd.getCustomerDestinationList(sm.getIdCustomer())));
			if (sm.getDestinations() == null){
				sm.setDestinations(new HashSet<Destination>());
			}
			sm.getDestinations().add(d);
			rd.saveUpdatesCustomer(sm, user);
			user.setContact(sm.getContact());
			new UserDao().saveUpdate(user);
		}
		return new GECOSuccess();
	}
	public GECOObject saveUpdatesCustomer(Customer sm,Session session){
		int id=0;
		if (sm.control() == null){
			try{
				if (sm.getCustomername() != "" && sm.getCustomername() != null ){
						TblCustomer tblsm = new TblCustomer();
						tblsm.convertToTableSingle(sm, tblsm);
						session.saveOrUpdate(tblsm);
						id=tblsm.getIdCustomer();
				}
			}catch(HibernateException e){
				System.err.println("ERROR IN LIST!!!!!!");
				e.printStackTrace();
				return new GECOError(GECOParameter.ERROR_HIBERNATE, "Errore nel salvataggio dei dati");
			}
		}else{
			return sm.control();
		}
		return new GECOSuccess(id);
	}
	/***
	 * DELETE A SINGLE Tblcustomer
	 * **/
	public Boolean deleteCustomer(Customer sm){
		TblCustomer tblsm = new TblCustomer();
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
	/**
	 * GET A SINGLE CUSTOMER
	 * **/
	public Customer getSingleCustomer(int idcustomer){
		Session session = HibernateUtils.getSessionFactory().openSession();
		Customer customer = new Customer();
		//customer = getMockCustomer();
		try{
			Criteria cr = session.createCriteria(TblCustomer.class,"customer");
			cr.add(Restrictions.eq("idCustomer", idcustomer));
			cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List customers = cr.list();
			if (customers.size() > 0){
				customer.convertFromTableSingle((TblCustomer)customers.get(0));
			}
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}finally{
			session.close();
		}
		return customer;
	}
	public Customer getSingleCustomerByTaxcode(String tc,String key){
		Session session = HibernateUtils.getSessionFactory().openSession();
		Customer customer = new Customer();
		//customer = getMockCustomer();
		try{
			Criteria cr = session.createCriteria(TblCustomer.class,"customer");
			cr.add(Restrictions.eq("customer.taxcode", tc));
			cr.createAlias("customer.company", "company");
			cr.add(Restrictions.eqOrIsNull("company.code",key));
			cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List customers = cr.list();
			if (customers.size() > 0){
				customer.convertFromTableSingle((TblCustomer)customers.get(0));
			}
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}finally{
			session.close();
		}
		return customer;
	}
	public Customer getSingleCustomer(String code,User user){
		Session session = HibernateUtils.getSessionFactory().openSession();
		Customer customer = new Customer();
		//customer = getMockCustomer();
		try{
			Criteria cr = session.createCriteria(TblCustomer.class,"customer");
			cr.add(Restrictions.eq("customer.customercode", code));
			cr.add(Restrictions.eq("customer.company.idCompany", user.getCompany().getIdCompany()));
			cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List customers = cr.list();
			if (customers.size() > 0){
				customer.convertFromTableSingle((TblCustomer)customers.get(0));
			}
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}finally{
			session.close();
		}
		return customer;
	}
	
	/*****
	 * Get List of Destination 
	 */
	public ArrayList<Destination> getDestinationList(){
		Session session = HibernateUtils.getSessionFactory().openSession();
		ArrayList<Destination> list = new ArrayList<Destination>();
		try{
			Criteria cr = session.createCriteria(TblDestination.class);
			List<TblDestination> destinations = cr.list();
			if (destinations.size() > 0){
				for (Iterator<TblDestination> iterator = destinations.iterator(); iterator.hasNext();){
					TblDestination tbldestination = iterator.next();
					Destination destination = new Destination();
					//destination = getMockDestination();
					destination.convertFromTable(tbldestination);
					list.add(destination);
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
	/*****
	 * Get List of Destination for Customer 
	 */
	public ArrayList<Destination> getCustomerDestinationList(int idCustomer){
		Session session = HibernateUtils.getSessionFactory().openSession();
		ArrayList<Destination> list = new ArrayList<Destination>();
		try{
			Criteria cr = session.createCriteria(TblDestination.class,"destination");
			cr.add(Restrictions.eq("destination.customer.idCustomer", idCustomer));
			List<TblDestination> destinations = cr.list();
			if (destinations.size() > 0){
				for (Iterator<TblDestination> iterator = destinations.iterator(); iterator.hasNext();){
					TblDestination tbldestination = iterator.next();
					Destination destination = new Destination();
					//destination = getMockDestination();
					destination.convertFromTable(tbldestination);
					list.add(destination);
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
	 * Save update Destinations
	 * **/
	public GECOObject saveUpdatesDestination(Destination sm){
		int id=0;
		if(sm.control()==null){
			Session session = HibernateUtils.getSessionFactory().openSession();
			Transaction tx = null;
			
			try{
				tx = session.beginTransaction();
				if (sm.getDestinationname() != "" && sm.getDestinationname() != null ){
						TblDestination tblsm = new TblDestination();
						tblsm.convertToTable(sm);
						session.saveOrUpdate(tblsm);
						id=tblsm.getIdDestination();
				}
				tx.commit();
			}catch(HibernateException e){
				System.err.println("ERROR IN LIST!!!!!!");
				if (tx!= null) tx.rollback();
				e.printStackTrace();
				throw new ExceptionInInitializerError(e);
			}finally{
				session.close();
			}
		}else{
			return sm.control();
		}
		return new GECOSuccess(id);
	}
	/***
	 * DELETE A SINGLE Tbldestination
	 * **/
	public Boolean deleteDestination(Destination sm){
		TblDestination tblsm = new TblDestination();
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
	/**
	 * GET A SINGLE CUSTOMER
	 * **/
	public Destination getSingleDestination(int iddestination){
		Session session = HibernateUtils.getSessionFactory().openSession();
		Destination destination = new Destination();
		//destination = getMockDestination();
		try{
			
			Criteria cr = session.createCriteria(TblDestination.class,"destination");
			cr.add(Restrictions.eq("idDestination", iddestination));
			cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List destinations = cr.list();
			if (destinations.size() > 0){
				
				destination.convertFromTable((TblDestination)destinations.get(0));
				
			}
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
			
		}finally{
			session.close();
		}
		
		return destination;
	}
	
	
	/*****
	 * Get List of Supplier 
	 */
	public ArrayList<Supplier> getSupplierList(){
		Session session = HibernateUtils.getSessionFactory().openSession();
		ArrayList<Supplier> list = new ArrayList<Supplier>();
		try{
			Criteria cr = session.createCriteria(TblSupplier.class);
			List<TblSupplier> suppliers = cr.list();
			if (suppliers.size() > 0){
				for (Iterator<TblSupplier> iterator = suppliers.iterator(); iterator.hasNext();){
					TblSupplier tblsupplier = iterator.next();
					Supplier supplier = new Supplier();
					//supplier = getMockSupplier();
					supplier.convertFromTable(tblsupplier);
					list.add(supplier);
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
	public ArrayList<Supplier> getSupplierList(SelectSupplierList filter){
		Session session = HibernateUtils.getSessionFactory().openSession();
		ArrayList<Supplier> list = new ArrayList<Supplier>();
		try{
			Criteria cr = session.createCriteria(TblSupplier.class,"supplier");
			if (filter.getGroup() != null)
				cr.add(Restrictions.eq("supplier.group.idGroupSupplier", filter.getGroup().getIdGroupSupplier()));
			if (filter.getCategory() != null)
				cr.add(Restrictions.eq("supplier.category.idCategorySupplier", filter.getCategory().getIdCategorySupplier()));
			cr.setFirstResult(filter.getPagefilter().startelement);
			cr.setMaxResults(filter.getPagefilter().pageSize);
			if (filter.getSearch() != null && filter.getSearch().equals("") == false){
				cr.add(Restrictions.disjunction(Restrictions.like("supplier.suppliercode","%"+  filter.getSearch()+"%"),Restrictions.like("supplier.suppliername","%"+ filter.getSearch()+"%")));
			}
			cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List<TblSupplier> suppliers = cr.list();
			if (suppliers.size() > 0){
				for (Iterator<TblSupplier> iterator = suppliers.iterator(); iterator.hasNext();){
					TblSupplier tblsupplier = iterator.next();
					Supplier supplier = new Supplier();
					//supplier = getMockSupplier();
					supplier.convertFromTable(tblsupplier);
					list.add(supplier);
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
	 * Save update Suppliers
	 * **/
	public GECOObject saveUpdatesSupplier(Supplier sm){
		int id=0;
		if (sm.control() == null){
			Session session = HibernateUtils.getSessionFactory().openSession();
			Transaction tx = null;
			
			try{
				tx = session.beginTransaction();
				if (sm.getSuppliername() != "" && sm.getSuppliername() != null ){
						TblSupplier tblsm = new TblSupplier();
						tblsm.convertToTableSingle(sm, tblsm);
						session.saveOrUpdate(tblsm);
						id=tblsm.getIdSupplier();
				}
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
		return new GECOSuccess(id);
	}
	/***
	 * DELETE A SINGLE Tblsupplier
	 * **/
	public Boolean deleteSupplier(Supplier sm){
		TblSupplier tblsm = new TblSupplier();
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
	/**
	 * GET A SINGLE CUSTOMER
	 * **/
	public Supplier getSingleSupplier(int idsupplier){
		Session session = HibernateUtils.getSessionFactory().openSession();
		Supplier supplier = new Supplier();
		//supplier = getMockSupplier();
		try{
			
			Criteria cr = session.createCriteria(TblSupplier.class,"supplier");
			cr.add(Restrictions.eq("idSupplier", idsupplier));
			cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List suppliers = cr.list();
			if (suppliers.size() > 0){
				
				supplier.convertFromTableSingle((TblSupplier)suppliers.get(0));
				
			}
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
			
		}finally{
			session.close();
		}
		
		return supplier;
	}
	
	
	

	/*****
	 * Get List of Transporter 
	 */
	public ArrayList<Transporter> getTransporterList(){
		Session session = HibernateUtils.getSessionFactory().openSession();
		ArrayList<Transporter> list = new ArrayList<Transporter>();
		try{
			Criteria cr = session.createCriteria(TblTransporter.class);
			List<TblTransporter> transporters = cr.list();
			if (transporters.size() > 0){
				for (Iterator<TblTransporter> iterator = transporters.iterator(); iterator.hasNext();){
					TblTransporter tbltransporter = iterator.next();
					Transporter transporter = new Transporter();
					//transporter = getMockTransporter();
					transporter.convertFromTable(tbltransporter);
					list.add(transporter);
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
	 * Save update Transporters
	 * **/
	public GECOObject createUserTransporter(Transporter sm,User loggeduser,Role r){
		User user = new User();
		user.setActive(true);
		user.setName(sm.getTransportername());
		user.setSurname(sm.getTransportersurname());
		user.setEmail(sm.getContact().getEmail1());
		user.setUsername(sm.getTransportersurname());
		user.setCompany(loggeduser.getCompany());
		user.setContact(sm.getContact());
		user.setRole(r);
		UserDao usdao = new UserDao();
		usdao.saveUpdate(user);
		return new GECOSuccess();
	}
	public GECOObject saveUpdatesTransporter(Transporter sm){
		int id=0;
		if (sm.control() == null){
			Session session = HibernateUtils.getSessionFactory().openSession();
			Transaction tx = null;
			
			try{
				tx = session.beginTransaction();
				if (sm.getTransportername() != "" && sm.getTransportername() != null ){
						TblTransporter tblsm = new TblTransporter();
						tblsm.convertToTable(sm);
						session.saveOrUpdate(tblsm);
						id=tblsm.getIdTransporter();
				}
				tx.commit();
			}catch(HibernateException e){
				System.err.println("ERROR IN LIST!!!!!!");
				if (tx!= null) tx.rollback();
				e.printStackTrace();
				throw new ExceptionInInitializerError(e);
			}finally{
				session.close();
			}
		}else{
			return sm.control();
		}
		return new GECOSuccess(id);
	}
	/***
	 * DELETE A SINGLE Tbltransporter
	 * **/
	public Boolean deleteTransporter(Transporter sm){
		TblTransporter tblsm = new TblTransporter();
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
	/**
	 * GET A SINGLE CUSTOMER
	 * **/
	public Transporter getSingleTransporter(int idtransporter){
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transporter transporter = new Transporter();
		//transporter = getMockTransporter();
		try{
			
			Criteria cr = session.createCriteria(TblTransporter.class,"transporter");
			cr.add(Restrictions.eq("idTransporter", idtransporter));
			cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List transporters = cr.list();
			if (transporters.size() > 0){
				transporter.convertFromTable((TblTransporter)transporters.get(0));
			}
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
			
		}finally{
			session.close();
		}
		
		return transporter;
	}
	/*********************
	 * *
	 * */
	public ArrayList<Promoter> getPromoterList(User loggeduser){
		Session session = HibernateUtils.getSessionFactory().openSession();
		ArrayList<Promoter> list = new ArrayList<Promoter>();
		try{
			Criteria cr = session.createCriteria(TblPromoter.class,"promoter");
			cr.add(Restrictions.eq("promoter.company.idCompany", loggeduser.getCompany().getIdCompany()));
			cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List<TblPromoter> promoters = cr.list();
			if (promoters.size() > 0){
				for (Iterator<TblPromoter> iterator = promoters.iterator(); iterator.hasNext();){
					TblPromoter tblpromoter = iterator.next();
					Promoter promoter = new Promoter();
					//customer = getMockCustomer();
					promoter.convertFromTable(tblpromoter);
					list.add(promoter);
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
	public GECOObject saveUpdatesPromoter(Promoter sm,User user){
		int id=0;
		if (sm.control() == null){
			Session session = HibernateUtils.getSessionFactory().openSession();
			Transaction tx = null;
			sm.setCompany(user.getCompany());
			try{
				tx = session.beginTransaction();
				if (sm.control() == null){
						TblPromoter tblsm = new TblPromoter();
						tblsm.convertToTable(sm);
						session.saveOrUpdate(tblsm);
						id=tblsm.getIdPromoter();
				}
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
		return new GECOSuccess(id);
	}
	public GECOObject createUserPromoter(User loggeduser,UserPromoter up){
		User user = new User();
		user.setActive(false);

		user.setName(up.getPromoter().getName());
		user.setSurname(up.getPromoter().getSurname());
		user.setEmail(up.getPromoter().getContact().getEmail1());
		user.setUsername(up.getPromoter().getSurname());
		user.setCompany(loggeduser.getCompany());
		user.setContact(up.getPromoter().getContact());
		user.setRole(up.getRole());
		UserDao usdao = new UserDao();
		usdao.saveUpdate(user);
		return new GECOSuccess();
	} 
	public Boolean deletePromoter(Promoter sm){
		TblPromoter tblsm = new TblPromoter();
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
	
	public Promoter getSinglePromoter(int idpromoter){
		Session session = HibernateUtils.getSessionFactory().openSession();
		Promoter promoter = new Promoter();
		//customer = getMockCustomer();
		try{
			
			Criteria cr = session.createCriteria(TblPromoter.class,"promoter");
			cr.add(Restrictions.eq("promoter.idPromoter", idpromoter));
			cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List promoters = cr.list();
			if (promoters.size() > 0){
				
				promoter.convertFromTable((TblPromoter)promoters.get(0));
				
			}
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
			
		}finally{
			session.close();
		}
		
		return promoter;
	}
	public Promoter getSinglePromoter(String code,User user){
		Session session = HibernateUtils.getSessionFactory().openSession();
		Promoter promoter = new Promoter();
		//customer = getMockCustomer();
		try{
			
			Criteria cr = session.createCriteria(TblPromoter.class,"promoter");
			cr.add(Restrictions.eq("promoter.code", code));
			cr.add(Restrictions.eq("promoter.company.idCompany", user.getCompany().getIdCompany()));
			cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List promoters = cr.list();
			if (promoters.size() > 0){
				
				promoter.convertFromTable((TblPromoter)promoters.get(0));
				
			}
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
			
		}finally{
			session.close();
		}
		
		return promoter;
	}
	public ArrayList<PaymentSolution> getPaymentSolutionList(){
		Session session = HibernateUtils.getSessionFactory().openSession();
		ArrayList<PaymentSolution> list = new ArrayList<PaymentSolution>();
		try{
			Criteria cr = session.createCriteria(TblPaymentSolution.class);
			List<TblPaymentSolution> ps = cr.list();
			if (ps.size() > 0){
				for (Iterator<TblPaymentSolution> iterator = ps.iterator(); iterator.hasNext();){
					TblPaymentSolution tblts = iterator.next();
					PaymentSolution pays = new PaymentSolution();
					pays.convertFromTable(tblts);
					list.add(pays);
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
	public Boolean updateContact(Contact sm){
		TblContact tblsm = new TblContact();
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = null;
		try{
			tblsm.convertToTable(sm);
			tx = session.beginTransaction();
			session.save(tblsm);
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

}
