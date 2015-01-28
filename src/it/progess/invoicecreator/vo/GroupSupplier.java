package it.progess.invoicecreator.vo;

import it.progess.invoicecreator.pojo.Itbl;

import it.progess.invoicecreator.pojo.TblGroupSupplier;
import it.progess.invoicecreator.properties.GECOParameter;

public class GroupSupplier implements Ivo{
	private int idGroupSupplier;
	private String code;
	private String name;
	private String description;
	public int getIdGroupSupplier() {
		return idGroupSupplier;
	}
	public void setIdGroupSupplier(int idGroupSupplier) {
		this.idGroupSupplier = idGroupSupplier;
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
		TblGroupSupplier gc = (TblGroupSupplier) obj;
		this.code = gc.getCode();
		this.description = gc.getDescription();
		this.idGroupSupplier = gc.getIdGroupSupplier();
		this.name = gc.getName();
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
