package it.progess.invoicecreator.vo;

import it.progess.invoicecreator.pojo.Itbl;
import it.progess.invoicecreator.pojo.TblCategorySupplier;
import it.progess.invoicecreator.properties.GECOParameter;
public class CategorySupplier implements Ivo {
	private int idCategorySupplier;
	private String code;
	private String name;
	private String description;
	public int getIdCategorySupplier() {
		return idCategorySupplier;
	}
	public void setIdCategorySupplier(int idCategorySupplier) {
		this.idCategorySupplier = idCategorySupplier;
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
		TblCategorySupplier cc = (TblCategorySupplier)obj;
		this.code = cc.getCode();
		this.description = cc.getDescription();
		this.idCategorySupplier = cc.getIdCategorySupplier();
		this.name = cc.getName();
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
