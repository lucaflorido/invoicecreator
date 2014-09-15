package it.progess.invoicecreator.vo;

import it.progess.invoicecreator.pojo.Itbl;
import it.progess.invoicecreator.pojo.TblCompany;
import it.progess.invoicecreator.properties.GECOParameter;

public class Company implements Ivo {
	private int idCompany;
	private String companyname;
	private String companynumber;
	private String taxcode;
	private Contact contact;
	private Address address;
	private BankContact bankcontact;
	public BankContact getBankcontact() {
		return bankcontact;
	}
	public void setBankcontact(BankContact bankcontact) {
		this.bankcontact = bankcontact;
	}
	public int getIdCompany() {
		return idCompany;
	}
	public void setIdCompany(int idCompany) {
		this.idCompany = idCompany;
	}
	public String getCompanyname() {
		return companyname;
	}
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
	public String getCompanynumber() {
		return companynumber;
	}
	public void setCompanynumber(String companynumber) {
		this.companynumber = companynumber;
	}
	public String getTaxcode() {
		return taxcode;
	}
	public void setTaxcode(String taxcode) {
		this.taxcode = taxcode;
	}
	public Contact getContact() {
		return contact;
	}
	public void setContact(Contact contact) {
		this.contact = contact;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public void convertFromTable(Itbl obj){
		TblCompany co = (TblCompany)obj;
		this.idCompany = co.getIdCompany();
		this.companyname = co.getCompanyname();
		this.companynumber = co.getCompanynumber();
		this.taxcode = co.getTaxcode();
		if(co.getContact() != null){
			this.contact = new Contact();
			this.contact.convertFromTable(co.getContact());
		}
		if (co.getAddress() != null){
			this.address = new Address();
			this.address.convertFromTable(co.getAddress());
		}
		if(co.getBankcontact() != null){
			this.bankcontact = new BankContact();
			this.bankcontact.convertFromTable(co.getBankcontact());
		}
	}
	public GECOError control(){
		GECOError er = null;
		if (this.companyname == null || this.companyname ==""){
			er = new GECOError(GECOParameter.ERROR_VALUE_MISSING,"Ragione Sociale mancante");
		}
		if (this.address == null){
			er = new GECOError(GECOParameter.ERROR_VALUE_MISSING,"Indirizzo mancante");
		}else if (this.address.control() != null){
			er = (GECOError)this.address.control();
		}
		if (this.taxcode == null || this.taxcode ==""){
			er = new GECOError(GECOParameter.ERROR_VALUE_MISSING,"Codice Fiscale Mancante");
		}else{
			if (this.taxcode.length() != 16){
				er = new GECOError(GECOParameter.ERROR_WRONG_SIZE,"Codice Fiscale non conforme");
			}
		}
		if (this.bankcontact == null){
			er = new GECOError(GECOParameter.ERROR_VALUE_MISSING,"Dati Bancari mancanti");
		}else if (this.bankcontact.control() != null){
			er = (GECOError)this.bankcontact.control();
		}
		
		return er;
	}
}
