package it.progess.invoicecreator.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="tblmailtext")
public class TblMailText {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idmailtext")
	private int idmailtext;
	@Column(name="code")
	private String code;
	@Column(name="text")
	private String text;
	@Column(name="object")
	private String object;
	@ManyToOne
	@JoinColumn(name = "idCompany")
	private TblCompany company;
	
	public TblCompany getCompany() {
		return company;
	}
	public void setCompany(TblCompany company) {
		this.company = company;
	}
	public String getObject() {
		return object;
	}
	public void setObject(String object) {
		this.object = object;
	}
	public int getIdmailtext() {
		return idmailtext;
	}
	public void setIdmailtext(int idmailtext) {
		this.idmailtext = idmailtext;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
}
