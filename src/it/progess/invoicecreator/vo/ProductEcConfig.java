package it.progess.invoicecreator.vo;

import it.progess.invoicecreator.pojo.Itbl;
import it.progess.invoicecreator.pojo.TblProductEcConfig;
import it.progess.invoicecreator.pojo.TblUnitMeasureProduct;


public class ProductEcConfig implements Ivo{
	private int idProductEcConfig;
	private int deliverydays;
	private int orderdays;
	private int quantitymin;
	private int quantitymax;
	private UnitMeasureProduct umproduct;
	public int getIdProductEcConfig() {
		return idProductEcConfig;
	}
	public void setIdProductEcConfig(int idProductEcConfig) {
		this.idProductEcConfig = idProductEcConfig;
	}
	public int getDeliverydays() {
		return deliverydays;
	}
	public void setDeliverydays(int deliverydays) {
		this.deliverydays = deliverydays;
	}
	public int getOrderdays() {
		return orderdays;
	}
	public void setOrderdays(int orderdays) {
		this.orderdays = orderdays;
	}
	public int getQuantitymin() {
		return quantitymin;
	}
	public void setQuantitymin(int quantitymin) {
		this.quantitymin = quantitymin;
	}
	public int getQuantitymax() {
		return quantitymax;
	}
	public void setQuantitymax(int quantitymax) {
		this.quantitymax = quantitymax;
	}
	public UnitMeasureProduct getUmproduct() {
		return umproduct;
	}
	public void setUmproduct(UnitMeasureProduct umproduct) {
		this.umproduct = umproduct;
	}
	public void convertFromTable(Itbl table){
		TblProductEcConfig pec = (TblProductEcConfig)table;
		this.deliverydays = pec.getDeliverydays();
		this.idProductEcConfig = pec.getIdProductEcConfig();
		this.orderdays = pec.getOrderdays();
		this.quantitymax = pec.getQuantitymax();
		this.quantitymin = pec.getQuantitymin();
		if (pec.getUmproduct() != null){
			this.umproduct = new UnitMeasureProduct();
			this.umproduct.convertFromTable(pec.getUmproduct());
		}
	}
	public GECOObject control(){
		return null;
	}
	
}
