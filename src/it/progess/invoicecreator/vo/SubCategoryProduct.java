package it.progess.invoicecreator.vo;

import it.progess.invoicecreator.pojo.Itbl;
import it.progess.invoicecreator.pojo.TblSubCategoryProduct;
import it.progess.invoicecreator.properties.GECOParameter;

public class SubCategoryProduct implements Ivo{
	private int idSubCategoryProduct;
	private String code;
	private String name;
	private String description;
	public int getIdSubCategoryProduct() {
		return idSubCategoryProduct;
	}
	public void setIdSubCategoryProduct(int idSubCategoryProduct) {
		this.idSubCategoryProduct = idSubCategoryProduct;
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
		TblSubCategoryProduct scp = (TblSubCategoryProduct)obj;
		this.code = scp.getCode();
		this.description = scp.getDescription();
		this.idSubCategoryProduct = scp.getIdSubCategoryProduct();
		this.name = scp.getName();
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
