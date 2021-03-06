package it.progess.invoicecreator.vo;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import it.progess.invoicecreator.pojo.Itbl;
import it.progess.invoicecreator.pojo.TblCompany;
import it.progess.invoicecreator.pojo.TblMailConfigCompany;
import it.progess.invoicecreator.properties.GECOParameter;
import it.progess.transport.check.ProgessCheck;
import it.progess.transport.vo.ProgessError;
import it.progess.validator.CFPIValidator;

public class Company implements Ivo {
	private int idCompany;
	private String companyname;
	private String companynumber;
	private String companycode;
	private String companyzone;
	private String taxcode;
	private Contact contact;
	private Address address;
	private BankContact bankcontact;
	private Set<MailConfigCompany> mailconfig;
	public String getCompanycode() {
		return companycode;
	}
	public void setCompanycode(String companycode) {
		this.companycode = companycode;
	}
	public String getCompanyzone() {
		return companyzone;
	}
	public void setCompanyzone(String companyzone) {
		this.companyzone = companyzone;
	}
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
	public Set<MailConfigCompany> getMailconfig() {
		return mailconfig;
	}
	public void setMailconfig(Set<MailConfigCompany> mailconfig) {
		this.mailconfig = mailconfig;
	}
	public void convertFromTable(Itbl obj){
		TblCompany co = (TblCompany)obj;
		this.idCompany = co.getIdCompany();
		this.companyname = co.getCompanyname();
		this.companynumber = co.getCompanynumber();
		this.taxcode = co.getTaxcode();
		this.companycode = co.getCompanycode();
		this.companyzone = co.getCompanyzone();
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
		if(co.getMailconfig() != null){
			this.mailconfig = new HashSet<MailConfigCompany>();
			for (Iterator<TblMailConfigCompany> iterator = co.getMailconfig().iterator(); iterator.hasNext();){
				TblMailConfigCompany mailconf = iterator.next();
				MailConfigCompany listp = new MailConfigCompany();
				listp.convertFromTable(mailconf);
				this.mailconfig.add(listp);
			}
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
		if (ProgessCheck.basicCheck(CFPIValidator.checkCFPI(this.taxcode, this.companynumber,false,true)) == false){
			ProgessError pe = (ProgessError)CFPIValidator.checkCFPI(this.taxcode, this.companynumber,false,true);
			return new GECOError(pe.getErrorName(),pe.getErrorMessage());
		}
		if (this.companynumber == null || this.companynumber ==""){
			er = new GECOError(GECOParameter.ERROR_VALUE_MISSING,"Partita iva Mancante");
		}else{
			if (this.companynumber.length() != 11){
				er = new GECOError(GECOParameter.ERROR_WRONG_SIZE,"Partita iva non conforme");
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
