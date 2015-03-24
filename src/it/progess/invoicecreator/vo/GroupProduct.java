package it.progess.invoicecreator.vo;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import it.progess.invoicecreator.pojo.Itbl;
import it.progess.invoicecreator.pojo.TblGroupProduct;
import it.progess.invoicecreator.properties.GECOParameter;

public class GroupProduct implements Ivo {
	
	private int idGroupProduct;
	
	private String code;
	
	private String description;
	
	private String name;
	
	private String note;
	private Company company;
	
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public int getIdGroupProduct() {
		return idGroupProduct;
	}
	public void setIdGroupProduct(int idGroupProduct) {
		this.idGroupProduct = idGroupProduct;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public void convertFromTable(Itbl obj){
		TblGroupProduct gp = (TblGroupProduct)obj;
		this.code = gp.getCode();
		this.description = gp.getDescription();
		this.idGroupProduct = gp.getIdGroupProduct();
		this.name = gp.getName();
		this.note = gp.getNote();
		if (gp.getCompany() != null){
			this.company = new Company();
			this.company.convertFromTable(gp.getCompany());
		}
	}
	public GECOObject control(){
		if (this.code == null || this.code.equals("") == true){
			return new GECOError(GECOParameter.ERROR_VALUE_MISSING,"Codice Mancante");
		}
		if (this.name == null || this.name.equals("") == true){
			return new GECOError(GECOParameter.ERROR_VALUE_MISSING,"Nome Mancante");
		}
		return null;
		
	}
	public GECOObject control(User user){
		if (this.idGroupProduct == 0){
			this.company = user.getCompany();
		}
		return control();
	}
}
