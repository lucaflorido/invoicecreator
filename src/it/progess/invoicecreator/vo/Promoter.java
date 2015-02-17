package it.progess.invoicecreator.vo;

import it.progess.invoicecreator.pojo.Itbl;
import it.progess.invoicecreator.pojo.TblPromoter;
import it.progess.invoicecreator.properties.GECOParameter;

public class Promoter implements Ivo {
	private int idPromoter;
	
	private String surname;
	
	private String name;
	
	private Contact contact;
	
	private Company company;
	private String code;
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
	public Contact getContact() {
		return contact;
	}
	public void setContact(Contact contact) {
		this.contact = contact;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public void convertFromTable(Itbl obj){
		TblPromoter p = (TblPromoter)obj;
		this.name = p.getName();
		this.surname = p.getSurname();
		this.code = p.getCode();
		this.idPromoter = p.getIdPromoter();
		if (p.getContact() != null){
			this.contact = new Contact();
			this.contact.convertFromTable(p.getContact());
		}
		if (p.getCompany() != null){
			this.company = new Company();
			this.company.convertFromTable(p.getCompany());
		}
	}
	public GECOError control(User user){
		if (this.idPromoter == 0){
			this.company = user.getCompany();
		}
		return control();
	}
	public GECOError control(){
		GECOError er = null;
		if (this.name == null || this.name ==""){
			er = new GECOError(GECOParameter.ERROR_VALUE_MISSING,"Nome mancante");
		}
		if (this.surname == null || this.surname ==""){
			er = new GECOError(GECOParameter.ERROR_VALUE_MISSING,"Cognome mancante");
		}
		
		return er;
	}
}
