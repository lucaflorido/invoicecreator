package it.progess.invoicecreator.pojo;

import it.progess.invoicecreator.vo.Company;
import it.progess.invoicecreator.vo.Contact;
import it.progess.invoicecreator.vo.Ivo;
import it.progess.invoicecreator.vo.Promoter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="tblpromoter")
public class TblPromoter implements Itbl {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idPromoter")
	private int idPromoter;
	@Column(name="surname")
	private String surname;
	@Column(name="name")
	private String name;
	@Column(name="code")
	private String code;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idContact")
	private TblContact contact;
	@ManyToOne
	@JoinColumn(name = "idCompany")
	private TblCompany company;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getIdPromoter() {
		return idPromoter;
	}
	public void setIdPromoter(int idPromoter) {
		this.idPromoter = idPromoter;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public TblContact getContact() {
		return contact;
	}
	public void setContact(TblContact contact) {
		this.contact = contact;
	}
	public TblCompany getCompany() {
		return company;
	}
	public void setCompany(TblCompany company) {
		this.company = company;
	}
	public void convertToTable(Ivo obj){
		Promoter p = (Promoter)obj;
		this.name = p.getName();
		this.surname = p.getSurname();
		this.code = p.getCode();
		this.idPromoter = p.getIdPromoter();
		if (p.getContact() != null){
			this.contact = new TblContact();
			this.contact.convertToTable(p.getContact());
		}
		if (p.getCompany() != null){
			this.company = new TblCompany();
			this.company.convertToTable(p.getCompany());
		}
	}
}
