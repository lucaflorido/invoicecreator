package it.progess.invoicecreator.vo;

import it.progess.invoicecreator.pojo.Itbl;

import it.progess.invoicecreator.pojo.TblComposition;

public class Composition implements Ivo {
	private int idComposition;
	
	private String code;
	
	private String name;
	
	private String description;
	
	private Company company;
	
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public int getIdComposition() {
		return idComposition;
	}
	public void setIdComposition(int idComposition) {
		this.idComposition = idComposition;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void convertFromTable(Itbl tbl){
		TblComposition c = (TblComposition)tbl;
		this.idComposition = c.getIdComposition();
		this.code = c.getCode();
		this.name = c.getName();
		this.description = c.getDescription();
		if (c.getCompany() != null){
			this.company = new Company();
			this.company.convertFromTable(c.getCompany());
		}
	}
	public GECOObject control(){
		return null;
	}
}
