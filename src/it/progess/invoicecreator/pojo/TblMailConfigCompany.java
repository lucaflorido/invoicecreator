package it.progess.invoicecreator.pojo;

import it.progess.invoicecreator.vo.Ivo;
import it.progess.invoicecreator.vo.ListCustomer;
import it.progess.invoicecreator.vo.MailConfig;
import it.progess.invoicecreator.vo.MailConfigCompany;

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
@Table(name="tblmailconfig_company")
public class TblMailConfigCompany implements Itbl{
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idmailconfig_company")
	private int idMailConfigCompany;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idCompany")
	private TblCompany company;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idMailConfig")
	private TblMailConfig mailconfig;
	public int getIdMailConfigCompany() {
		return idMailConfigCompany;
	}
	public void setIdMailConfigCompany(int idMailConfigCompany) {
		this.idMailConfigCompany = idMailConfigCompany;
	}
	public TblCompany getCompany() {
		return company;
	}
	public void setCompany(TblCompany company) {
		this.company = company;
	}
	public TblMailConfig getMailconfig() {
		return mailconfig;
	}
	public void setMailconfig(TblMailConfig mailconfig) {
		this.mailconfig = mailconfig;
	}
	public void convertToTable(Ivo obj){
		MailConfigCompany mc = (MailConfigCompany)obj;
		this.idMailConfigCompany = mc.getIdMailConfigCompany();
		this.mailconfig = new TblMailConfig();
		if (mc.getMailconfig() != null){
			this.mailconfig.convertToTable(mc.getMailconfig());
		}
	}
	public void convertToTableForSaving(Ivo obj,Itbl itbl){
		convertToTable(obj);
		this.company = (TblCompany)itbl;
	}
}
