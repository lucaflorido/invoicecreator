package it.progess.invoicecreator.vo;

import it.progess.invoicecreator.pojo.Itbl;
import it.progess.invoicecreator.pojo.TblRegion;

import it.progess.invoicecreator.properties.GECOParameter;

public class Region implements Ivo {
	private int idRegion;
	
	private String code;
	
	private String description;
	
	private String name;
	
	private String note;
	private Company company;
	public int getIdRegion() {
		return idRegion;
	}
	public void setIdRegion(int idRegion) {
		this.idRegion = idRegion;
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
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public void convertFromTable(Itbl obj){
		TblRegion re = (TblRegion)obj;
		this.idRegion = re.getIdRegion();
		this.description = re.getDescription();
		this.code = re.getCode();
		this.name = re.getName();
		if (re.getCompany() != null){
			this.company = new Company();
			this.company.convertFromTable(re.getCompany());
		}
	}
	public GECOObject control(User user){
		if (this.idRegion == 0){
			this.company = user.getCompany();
		}
		return control();
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
}
