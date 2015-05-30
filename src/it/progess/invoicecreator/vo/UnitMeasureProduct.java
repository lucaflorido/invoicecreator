package it.progess.invoicecreator.vo;

import it.progess.invoicecreator.pojo.Itbl;
import it.progess.invoicecreator.pojo.TblUnitMeasureProduct;
import it.progess.invoicecreator.properties.GECOParameter;

public class UnitMeasureProduct implements Ivo {
	private int idUnitMeasureProduct;
	private boolean preference;
	private float conversion;
	private Product product;
	private UnitMeasure um;
	private String code;
	private double quantity;
	private int status;
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public double getQuantity() {
		return quantity;
	}
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getIdUnitMeasureProduct() {
		return idUnitMeasureProduct;
	}
	public void setIdUnitMeasureProduct(int idUnitMeasureProduct) {
		this.idUnitMeasureProduct = idUnitMeasureProduct;
	}
	public boolean isPreference() {
		return preference;
	}
	public void setPreference(boolean preference) {
		this.preference = preference;
	}
	public float getConversion() {
		return conversion;
	}
	public void setConversion(float conversion) {
		this.conversion = conversion;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public UnitMeasure getUm() {
		return um;
	}
	public void setUm(UnitMeasure um) {
		this.um = um;
	}
	public void convertFromTable(Itbl obj){
		TblUnitMeasureProduct um = (TblUnitMeasureProduct)obj;
		this.conversion = um.getConversion();
		this.idUnitMeasureProduct = um.getIdUnitMeasureProduct();
		this.preference = um.isPreference();
		this.code = um.getCode();
		if (um.getUm() != null){
			this.um = new UnitMeasure();
			this.um.convertFromTable(um.getUm());
		}
	}
	public void convertFromTableSearch(Itbl obj){
		convertFromTable(obj);
		TblUnitMeasureProduct um = (TblUnitMeasureProduct)obj;
		if (um.getProduct() != null){
			this.product = new Product();
			this.product.convertFromTable(um.getProduct());
		}
	}
	public GECOObject control(){
		return null;
	}
}
