package it.progess.invoicecreator.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.google.gson.Gson;

import it.progess.invoicecreator.hibernate.HibernateUtils;
import it.progess.invoicecreator.pojo.TblCompany;
import it.progess.invoicecreator.pojo.TblDraft;
import it.progess.invoicecreator.pojo.TblRole;
import it.progess.invoicecreator.pojo.TblUser;
import it.progess.invoicecreator.properties.MailParameter;
import it.progess.invoicecreator.vo.Company;
import it.progess.invoicecreator.vo.Contact;
import it.progess.invoicecreator.vo.Customer;
import it.progess.invoicecreator.vo.Draft;
import it.progess.invoicecreator.vo.DraftElement;
import it.progess.invoicecreator.vo.GECOError;
import it.progess.invoicecreator.vo.GECOObject;
import it.progess.invoicecreator.vo.GECOSuccess;
import it.progess.invoicecreator.vo.Head;
import it.progess.invoicecreator.vo.Product;
import it.progess.invoicecreator.vo.ProductEcConfig;
import it.progess.invoicecreator.vo.Role;
import it.progess.invoicecreator.vo.User;

public class DraftDao {
	/*gestisce id draft ed un oggetto draft in sessione*/
	public GECOObject setupDraft(String draftid,String key,HttpSession session){
		Draft draft = null;
		if (draftid != null && draftid != ""){
			draft = (Draft)session.getAttribute(draftid);
			if (draft == null){
				UUID ui = UUID.randomUUID();
				draftid = ui.toString();
				draft = new Draft();
				draft.setKey(key);
				draft.setId(draftid);
				draft.setProducts(new ArrayList<DraftElement>());
				session.setAttribute(draftid,draft);
			}
		}else{
			UUID ui = UUID.randomUUID();
			draftid = ui.toString();
			draft = new Draft();
			draft.setKey(key);
			draft.setId(draftid);
			draft.setProducts(new ArrayList<DraftElement>());
			session.setAttribute(draftid,draft);
		}
		return new GECOSuccess(draft);
	}
	/*aggiunge un oggetto al draft*/
	public GECOObject addToDraft(String draftid,HttpSession session,DraftElement product,String key){
		Draft draft = (Draft)session.getAttribute(draftid);
		StoreDao sd = new StoreDao();
		ProductEcConfig ecp = product.getProduct().getEcconfig();
		int qta = ecp.getQuantitymax() -  product.getQuantity();
		ecp.setQuantitymax(qta);
		sd.saveEcCommerce(ecp);
		if (draft == null){
			draft = new Draft();
			draft.setId(draftid);
			draft.setKey(key);
			session.setAttribute(draftid,draft);
		}
		draft.getProducts().add(product);
		draft.calculateTotal();
		persistenceDraft(draft);
		return new GECOSuccess(draft);
	}
	/*rimuove un oggetto dal draft*/
	public GECOObject removeFromDraft(String draftid,HttpSession session,DraftElement product,String key){
		Draft draft = (Draft)session.getAttribute(draftid);
		ProductEcConfig ecp = product.getProduct().getEcconfig();
		StoreDao sd = new StoreDao();
		int qta = ecp.getQuantitymax() +  product.getQuantity();
		ecp.setQuantitymax(qta);
		sd.saveEcCommerce(ecp);
		if (draft == null){
			draft = new Draft();
			draft.setId(draftid);
			draft.setKey(key);
			session.setAttribute(draftid,draft);
		}
		for (int j = draft.getProducts().size() - 1; j >= 0; j--) {
		   if (draft.getProducts().get(j).getProduct().getIdProduct() == product.getProduct().getIdProduct()){
			   draft.getProducts().remove(j);
		   }
		}
		draft.calculateTotal();
		persistenceDraft(draft);
		return new GECOSuccess(draft);
	}
	/*modifica oggetto dal draft*/
	public GECOObject updateDraft(String draftid,HttpSession session,DraftElement product){
		Draft draft = (Draft)session.getAttribute(draftid);
		for (int j = draft.getProducts().size() - 1; j >= 0; j--) {
		   if (draft.getProducts().get(j).getProduct().getIdProduct() == product.getProduct().getIdProduct()){
			   draft.getProducts().set(j, product);
		   }
		}
		draft.calculateTotal();
		persistenceDraft(draft);
		return new GECOSuccess(draft);
	}
	/*refresh draft*/
	public GECOObject refreshDraft(HttpSession session,Draft draft){
		draft.calculateTotal();
		session.setAttribute(draft.getId(),draft);
		persistenceDraft(draft);
		return new GECOSuccess(draft);
	}
	public void persistenceDraft(Draft draft){
		TblDraft td = getDraft(draft.getId());
		if (td == null){
			td = new TblDraft();
			storeDraft(draft);
		}else{
			storeDraft(td,draft);
		}
	}
	/*calcola totale*/
	/*chiude il carrello*/
	/*Paga il carrello*/
	public GECOObject confirmPayment(HttpSession session,ServletContext context,User user,String draftid,String paymentType){
		Draft draft = (Draft)session.getAttribute(draftid);
		draft.setUser(user);
		
		switch(paymentType){
			case "03":
				createEcUser(user,draft.getKey());
				DocumentDao dd = new DocumentDao();
				dd.createOrderFromDraft(context,user, draft);
				//dd.createInvoiceFromDraft(user, draft);
				return new GECOSuccess("confirmed");
			case "01":
				TblDraft td = getDraft(draft.getKey());
				if (storeDraft(td,draft) == true){
					return new GECOSuccess("paypal");
				}else{
					return new GECOError("ERP","Errore nel pagamento");
				}
				
				
		}
		/*Utente Registrato*/
		/*Utente non Registrato*/
		return new GECOSuccess();
	}
	public boolean storeDraft(Draft d){
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = null;
		Boolean result = false;
		try{
			tx = session.beginTransaction();
			TblDraft draft = new TblDraft();
			draft.setCode(d.getId());
			draft.setCreationdate(new Date());
			
			Gson gson = new Gson();
			draft.setValue(gson.toJson(d));
			session.save(draft);
		    tx.commit();
		    result = true;
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			if (tx!= null) tx.rollback();
			e.printStackTrace();
			session.close();
			return false;
		}finally{
			session.close();
		}
		
		return true;
	}
	public boolean storeDraft(TblDraft draft,Draft d){
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = null;
		Boolean result = false;
		try{
			tx = session.beginTransaction();
			
			draft.setCode(d.getId());
			//draft.setCreationdate(new Date());
			
			Gson gson = new Gson();
			draft.setValue(gson.toJson(d));
			session.saveOrUpdate(draft);
		    tx.commit();
		    result = true;
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			if (tx!= null) tx.rollback();
			e.printStackTrace();
			session.close();
			return false;
		}finally{
			session.close();
		}
		
		return true;
	}
	public TblDraft getDraft(String code){
		Session session = HibernateUtils.getSessionFactory().openSession();
		try{
			Criteria cr = session.createCriteria(TblDraft.class,"draft");
			cr.add(Restrictions.eq("draft.code", code));
			List<TblDraft> companys = cr.list();
			if (companys.size() > 0){
				return companys.get(0);
			}
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}finally{
			session.close();
		}
		return null;
	}
	public GECOObject confirmPayment(ServletContext context,String draftid){
		TblDraft td = getDraft(draftid);
		if (td == null){
			return new GECOError();
		}
		Draft draft = new Gson().fromJson(td.getValue(), Draft.class);
		User user = draft.getUser();
		createEcUser(user,draft.getKey());
		DocumentDao dd = new DocumentDao();
		dd.createOrderFromDraft(context,user, draft);
		//dd.createInvoiceFromDraft(user, draft);
		removeDraft(td);		
		/*Utente Registrato*/
		/*Utente non Registrato*/
		return new GECOSuccess();
	}
	private void createEcUser(User user,String key){
		RegistryDao rd = new RegistryDao();
		if (user.getIduser() == 0){
			user.set_iduser(new UserDao().createEcUser(user,key));
			GECOObject obj  = rd.createCustomerFromUser(user);
			if(obj instanceof GECOSuccess ){
				int idc = (int)((GECOSuccess)obj).success;
				Customer c = rd.getSingleCustomer(idc);
				Contact co = c.getContact();
				//co.setUser(user);
				//rd.updateContact(co);
				user.setContact(co);
				new UserDao().saveUpdate(user);
			}
		}else{
			rd.createDestinationFromUser(user);
		}
	}
	public GECOObject createEcUserForm(User user,String key){
		if (user.getIduser() == 0){
			RegistryDao rd = new RegistryDao();
			user.set_iduser(new UserDao().createEcUser(user,key));
			GECOObject obj  = rd.createCustomerFromUser(user);
			UserDao daou = new UserDao(); 
			if(obj instanceof GECOSuccess ){
				int idc = (int)((GECOSuccess)obj).success;
				Customer c = rd.getSingleCustomer(idc);
				Contact co = c.getContact();
				user.setContact(co);
				int iduser =  daou.saveUpdate(user);
				user = daou.getSingleUserVO(iduser);
				return new MailDao().sendNewCustomerUser(key,user,MailParameter.NEW_USER_CUSTOMER_MAIL);
			}else{
				return obj;
			}
				
		}else{
			return new GECOError("USR", "Utente già registrato");
		}
	}
	public void removeDraft(TblDraft td){
			Session session = HibernateUtils.getSessionFactory().openSession();
			Transaction tx = null;
			try{
				
				tx = session.beginTransaction();
				session.delete(td);
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
	public List<TblDraft> getAllDraft(){
		Session session = HibernateUtils.getSessionFactory().openSession();
		List<TblDraft> companys = null;
		try{
			Criteria cr = session.createCriteria(TblDraft.class,"draft");
			companys = cr.list();
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}finally{
			session.close();
		}
		return companys;
	}
	public void deleteOldDraft(){
		Date today = new Date();
		List<TblDraft> l = getAllDraft();
		StoreDao sd = new StoreDao();
		Gson gson = new Gson();
		if (l != null){
			for(Iterator<TblDraft> it = l.iterator();it.hasNext();){
				TblDraft td = it.next();
				long diff = today.getTime() - td.getCreationdate().getTime();
				diff = diff/60000;
				if (diff > 15){
					Draft draft = gson.fromJson(td.getValue(), Draft.class);
					//RIPRISTINA TUTTE LE QUANTITA'
					for (Iterator<DraftElement> ide = draft.getProducts().iterator();ide.hasNext();){
						DraftElement de = ide.next();
						Product product = de.getProduct();
						ProductEcConfig pec = product.getEcconfig();
						int qta = pec.getQuantitymax() +  de.getQuantity();
						pec.setQuantitymax(qta);
						sd.saveEcCommerce(pec);
					}
					removeDraft(td);
				}
			}
		}
	}
}
