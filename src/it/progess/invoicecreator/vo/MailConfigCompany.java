package it.progess.invoicecreator.vo;

import it.progess.invoicecreator.pojo.Itbl;

import it.progess.invoicecreator.pojo.TblMailConfigCompany;

public class MailConfigCompany implements Ivo{
	private int idMailConfigCompany;
	private Company company;
	private MailConfig mailconfig;
	public int getIdMailConfigCompany() {
		return idMailConfigCompany;
	}
	public void setIdMailConfigCompany(int idMailConfigCompany) {
		this.idMailConfigCompany = idMailConfigCompany;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public MailConfig getMailconfig() {
		return mailconfig;
	}
	public void setMailconfig(MailConfig mailconfig) {
		this.mailconfig = mailconfig;
	}
	public void convertFromTable(Itbl obj){
		TblMailConfigCompany mc = (TblMailConfigCompany)obj;
		this.idMailConfigCompany = mc.getIdMailConfigCompany();
		this.mailconfig = new MailConfig();
		if (mc.getMailconfig() != null){
			this.mailconfig.convertFromTable(mc.getMailconfig());
		}
	}
	public GECOObject control(){
		return null;
	}
	
}
