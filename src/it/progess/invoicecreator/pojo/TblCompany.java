package it.progess.invoicecreator.pojo;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import it.progess.invoicecreator.vo.Company;
import it.progess.invoicecreator.vo.Currency;
import it.progess.invoicecreator.vo.EcDelivery;
import it.progess.invoicecreator.vo.EcPaymentCompany;
import it.progess.invoicecreator.vo.Ivo;



import it.progess.invoicecreator.vo.ListCustomer;
import it.progess.invoicecreator.vo.MailConfig;
import it.progess.invoicecreator.vo.MailConfigCompany;
import it.progess.invoicecreator.vo.UnitMeasureProduct;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name="tblcompany")
public class TblCompany implements Itbl {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idCompany")
	private int idCompany;
	@Column(name="companyname")
	private String companyname;
	@Column(name="companycode")
	private String companycode;
	@Column(name="companyzone")
	private String companyzone;
	@Column(name="companynumber")
	private String companynumber;
	@Column(name="taxcode")
	private String taxcode;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="idContact")
	private TblContact contact;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="idAddress")
	private TblAddress address;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="idBankContact")
	private TblBankContact bankcontact;
	@OneToMany(fetch = FetchType.EAGER,mappedBy = "company",cascade = CascadeType.ALL)
	private Set<TblMailConfigCompany> mailconfig;
	@Column(name="code")
	private String code;
	@OneToMany(fetch = FetchType.EAGER,mappedBy = "company",cascade = CascadeType.ALL)
	@Fetch(FetchMode.SELECT)
	private Set<TblEcPaymentCompany> ecpayments;
	@ManyToOne
	@JoinColumn(name="idEcDelivery", insertable = false, updatable = false)
	@Fetch(FetchMode.SELECT)
	private TblEcDelivery ecdelivery;
	@ManyToOne
	@JoinColumn(name="idcurrency")
	private TblCurrency currency;
	
	public TblCurrency getCurrency() {
		return currency;
	}
	public void setCurrency(TblCurrency currency) {
		this.currency = currency;
	}
	public TblEcDelivery getEcdelivery() {
		return ecdelivery;
	}
	public void setEcdelivery(TblEcDelivery ecdelivery) {
		this.ecdelivery = ecdelivery;
	}
	public Set<TblEcPaymentCompany> getEcpayments() {
		return ecpayments;
	}
	public void setEcpayments(Set<TblEcPaymentCompany> ecpayments) {
		this.ecpayments = ecpayments;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Set<TblMailConfigCompany> getMailconfig() {
		return mailconfig;
	}
	public void setMailconfig(Set<TblMailConfigCompany> mailconfig) {
		this.mailconfig = mailconfig;
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
	public TblContact getContact() {
		return contact;
	}
	public void setContact(TblContact contact) {
		this.contact = contact;
	}
	public TblAddress getAddress() {
		return address;
	}
	public void setAddress(TblAddress address) {
		this.address = address;
	}
	public TblBankContact getBankcontact() {
		return bankcontact;
	}
	public void setBankcontact(TblBankContact bankcontact) {
		this.bankcontact = bankcontact;
	}
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
	public void convertToTable(Ivo obj){
		Company co = (Company)obj;
		this.idCompany = co.getIdCompany();
		this.companyname = co.getCompanyname();
		this.companynumber = co.getCompanynumber();
		this.companycode = co.getCompanycode();
		this.companyzone = co.getCompanyzone();
		this.taxcode = co.getTaxcode();
		this.code = co.getCode();
		if(co.getContact() != null){
			this.contact = new TblContact();
			this.contact.convertToTable(co.getContact());
		}
		if (co.getAddress() != null){
			this.address = new TblAddress();
			this.address.convertToTable(co.getAddress());
		}
		if(co.getBankcontact() != null){
			this.bankcontact = new TblBankContact();
			this.bankcontact.convertToTable(co.getBankcontact());
		}
		
		if (co.getEcpayments() != null ){
			this.ecpayments = new HashSet<TblEcPaymentCompany>();
			for (Iterator<EcPaymentCompany> iterator = co.getEcpayments().iterator(); iterator.hasNext();){
				EcPaymentCompany ecp = iterator.next();
				TblEcPaymentCompany ecpt = new TblEcPaymentCompany();
				ecpt.convertToTable(ecp);
				this.ecpayments.add(ecpt);
			}
		}else{
			this.ecpayments = null;
		}
		if(co.getMailconfig() != null){
			this.mailconfig = new HashSet<TblMailConfigCompany>();
			for (Iterator<MailConfigCompany> iterator = co.getMailconfig().iterator(); iterator.hasNext();){
				MailConfigCompany mailconf = iterator.next();
				TblMailConfigCompany listp = new TblMailConfigCompany();
				listp.convertToTable(mailconf);
				
				this.mailconfig.add(listp);
			}
		}
		if (co.getEcdelivery() != null){
			this.ecdelivery = new TblEcDelivery();
			this.ecdelivery.convertToTable(co.getEcdelivery());
		}
		if (co.getCurrency() != null){
			this.currency = new TblCurrency();
			this.currency.convertToTable(co.getCurrency());
		}
	}
	
	public void convertToTableSave(Ivo obj){
		
		convertToTable(obj);
		if(this.mailconfig != null){
			
			for (Iterator<TblMailConfigCompany> iterator = this.mailconfig.iterator(); iterator.hasNext();){
			
				TblMailConfigCompany listp = iterator.next();;
				
				listp.setCompany(this);
				
			}
		}
		Company co = (Company)obj;
		if (co.getEcpayments() != null ){
			this.ecpayments = new HashSet<TblEcPaymentCompany>();
			for (Iterator<EcPaymentCompany> iterator = co.getEcpayments().iterator(); iterator.hasNext();){
				EcPaymentCompany ecp = iterator.next();
				TblEcPaymentCompany ecpt = new TblEcPaymentCompany();
				ecpt.convertToTableForSaving(ecp, this);
				this.ecpayments.add(ecpt);
			}
		}else{
			this.ecpayments = null;
		}
		
	}
}
