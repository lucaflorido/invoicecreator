package it.progess.invoicecreator.vo;

import it.progess.invoicecreator.pojo.Itbl;
import it.progess.invoicecreator.pojo.TblContact;


public class Contact implements Ivo {
	private int idcontact;
	private String phone1;
	private String phone2;
	private String phone3;
	private String mobile1;
	private String mobile2;
	private String email1;
	private String email2;
	private String reference;
	private String fax;
	private Customer customer;
	private Transporter transporter;
	private User user;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Transporter getTransporter() {
		return transporter;
	}
	public void setTransporter(Transporter transporter) {
		this.transporter = transporter;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public int getIdcontact() {
		return idcontact;
	}
	public void setIdcontact(int idcontact) {
		this.idcontact = idcontact;
	}
	public String getPhone1() {
		return phone1;
	}
	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}
	public String getPhone2() {
		return phone2;
	}
	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}
	public String getPhone3() {
		return phone3;
	}
	public void setPhone3(String phone3) {
		this.phone3 = phone3;
	}
	public String getMobile1() {
		return mobile1;
	}
	public void setMobile1(String mobile1) {
		this.mobile1 = mobile1;
	}
	public String getMobile2() {
		return mobile2;
	}
	public void setMobile2(String mobile2) {
		this.mobile2 = mobile2;
	}
	public String getEmail1() {
		return email1;
	}
	public void setEmail1(String email1) {
		this.email1 = email1;
	}
	public String getEmail2() {
		return email2;
	}
	public void setEmail2(String email2) {
		this.email2 = email2;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}

	public void convertFromTable(Itbl obj){
		TblContact co = (TblContact)obj;
		this.email1 = co.getEmail1();
		this.email2 = co.getEmail2();
		this.fax = co.getFax();
		this.idcontact = co.getIdcontact();
		this.mobile1 = co.getMobile1();
		this.mobile2 = co.getMobile2();
		this.phone1 = co.getPhone1();
		this.phone2 = co.getPhone2();
		this.reference = co.getReference();
	}
	public void convertFromTableUser(Itbl obj){
		this.convertFromTable(obj);
		TblContact co = (TblContact)obj;
		if (co.getCustomer() != null){
			this.customer = new Customer();
			this.customer.convertFromTable(co.getCustomer());
		}
		if (co.getTransporter() != null){
			this.transporter = new Transporter();
			this.transporter.convertFromTable(co.getTransporter());
		}
	}
	public GECOError control(){
		GECOError er = null;
		return er;
	}
}
