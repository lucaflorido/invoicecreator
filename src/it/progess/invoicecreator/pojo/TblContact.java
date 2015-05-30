package it.progess.invoicecreator.pojo;

import it.progess.invoicecreator.vo.Contact;
import it.progess.invoicecreator.vo.Ivo;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="tblcontact")
public class TblContact implements Itbl  {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idContact")
	private int idcontact;
	@Column(name="phone1")
	private String phone1;
	@Column(name="phone2")
	private String phone2;
	@Column(name="phone3")
	private String phone3;
	@Column(name="mobile1")
	private String mobile1;
	@Column(name="mobile2")
	private String mobile2;
	@Column(name="email1")
	private String email1;
	@Column(name="email2")
	private String email2;
	@Column(name="reference")
	private String reference;
	@Column(name="fax")
	private String fax;
	@OneToOne
	@JoinColumn(name="idUser")
	private TblUser user;
	@OneToOne(mappedBy="contact")
	private TblCustomer customer;
	@OneToOne(mappedBy="contact")
	private TblTransporter transporter;
	@OneToOne(mappedBy="contact")
	private TblPromoter promoter;
	
	public TblPromoter getPromoter() {
		return promoter;
	}
	public void setPromoter(TblPromoter promoter) {
		this.promoter = promoter;
	}
	public TblTransporter getTransporter() {
		return transporter;
	}
	public void setTransporter(TblTransporter transporter) {
		this.transporter = transporter;
	}
	public TblUser getUser() {
		return user;
	}
	public void setUser(TblUser user) {
		this.user = user;
	}
	public TblCustomer getCustomer() {
		return customer;
	}
	public void setCustomer(TblCustomer customer) {
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
	public void convertToTable(Ivo obj){
		Contact co = (Contact)obj;
		this.email1 = co.getEmail1();
		this.email2 = co.getEmail2();
		this.fax = co.getFax();
		this.idcontact = co.getIdcontact();
		this.mobile1 = co.getMobile1();
		this.mobile2 = co.getMobile2();
		this.phone1 = co.getPhone1();
		this.phone2 = co.getPhone2();
		this.reference = co.getReference();
		if (co.getUser() != null){
			this.user = new TblUser();
			this.user.convertToTableSave(co.getUser());
		}
	}
	
}
