package it.progess.invoicecreator.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;

import it.progess.invoicecreator.hibernate.HibernateUtils;
import it.progess.invoicecreator.pojo.TblCustomer;
import it.progess.invoicecreator.vo.Customer;
import it.progess.invoicecreator.vo.GECOError;
import it.progess.invoicecreator.vo.GECOObject;
import it.progess.invoicecreator.vo.GECOSuccess;
import it.progess.invoicecreator.vo.User;

public class ControlDao {
	public GECOObject checkProduct(){
		return null;
	}
	public static GECOObject checkCustomer(Customer c,User loggeduser){
		GECOSuccess go = new GECOSuccess();
		Session session = HibernateUtils.getSessionFactory().openSession();
		ArrayList<Customer> list = new ArrayList<Customer>();
		try{
			Criteria cr = session.createCriteria(TblCustomer.class,"customer");
			cr.add(Restrictions.eq("customer.company.idCompany", loggeduser.getCompany().getIdCompany()));
			Disjunction r = Restrictions.disjunction();
			r.add(Restrictions.eq("customer.customername", c.getCustomername()))
			.add(Restrictions.eq("customer.customercode", c.getCustomercode()));
			if (c.getSerialnumber() != null && !c.getSerialnumber().equals("") ){
				r.add(Restrictions.eq("customer.serialnumber", c.getSerialnumber()));
			}
			if (c.getTaxcode() != null && !c.getTaxcode().equals("")  ){
				r.add(Restrictions.eq("customer.taxcode", c.getTaxcode()));
			}
			/*cr.add(Restrictions.disjunction()
					.add(Restrictions.eq("customer.customername", c.getCustomername()))
					.add(Restrictions.eq("customer.customercode", c.getCustomercode()))
					.add(Restrictions.eq("customer.serialnumber", c.getSerialnumber()))
					.add(Restrictions.eq("customer.taxcode", c.getTaxcode())));*/
			cr.add(r);
			List<TblCustomer> customers = cr.list();
			if (customers.size() > 0){
				for (Iterator<TblCustomer> iterator = customers.iterator(); iterator.hasNext();){
					TblCustomer tblcustomer = iterator.next();
					if (tblcustomer.getIdCustomer() != c.getIdCustomer()){
						if (tblcustomer.getCustomercode().equals(c.getCustomercode())){
							return new GECOError("Errore", "Codice cliente già inserito");
						}
						if (tblcustomer.getCustomername().equals(c.getCustomername())){
							return new GECOError("Errore", "Ragione sociale già inserita");
						}
						if (tblcustomer.getSerialnumber().equals(c.getSerialnumber())){
							return new GECOError("Errore", "partita iva già inserita");
						}
						if (tblcustomer.getTaxcode().equals(c.getTaxcode())){
							return new GECOError("Errore", "Codice fiscale già inserito");
						}
					}
					//customer = getMockCustomer();
					
				}
			}
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}finally{
			session.close();
		}
		return go;
	}
}
