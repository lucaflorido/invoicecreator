package it.progess.invoicecreator.vo;

import it.progess.invoicecreator.pojo.Itbl;
import it.progess.invoicecreator.pojo.TblBrand;
import it.progess.invoicecreator.properties.GECOParameter;

public class Brand implements Ivo {
	private int idBrand;
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
	public int getIdBrand() {
		return idBrand;
	}
	public void setIdBrand(int idBrand) {
		this.idBrand = idBrand;
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
	public void convertFromTable(Itbl obj){
		TblBrand b = (TblBrand)obj;
		this.idBrand = b.getIdBrand();
		this.code = b.getCode();
		this.name = b.getName();
		this.description  = b.getDescription();
		if (b.getCompany() != null){
			this.company = new Company();
			this.company.convertFromTable(b.getCompany());
		}
	}
	public GECOObject control(User user){
		if (this.idBrand == 0){
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
