package it.progess.invoicecreator.vo;

import it.progess.invoicecreator.pojo.Itbl;
import it.progess.invoicecreator.pojo.TblDeliveryZone;



public class DeliveryZone implements Ivo {
	private int iddeliveryzone;
	private float price;
	private float bound;
	private Zone zone;
	private DeliveryDeliveryCountry deliverycountry;
	public int getIddeliveryzone() {
		return iddeliveryzone;
	}
	public void setIddeliveryzone(int iddeliveryzone) {
		this.iddeliveryzone = iddeliveryzone;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public float getBound() {
		return bound;
	}
	public void setBound(float bound) {
		this.bound = bound;
	}
	public Zone getZone() {
		return zone;
	}
	public void setZone(Zone zone) {
		this.zone = zone;
	}
	public DeliveryDeliveryCountry getDeliverycountry() {
		return deliverycountry;
	}
	public void setDeliverycountry(DeliveryDeliveryCountry deliverycountry) {
		this.deliverycountry = deliverycountry;
	}
	public void convertFromTable(Itbl tb){
		TblDeliveryZone dz = (TblDeliveryZone)tb;
		this.bound = dz.getBound();
		this.price = dz.getPrice();
		this.iddeliveryzone = dz.getIddeliveryzone();
		if (dz.getZone() != null){
			this.zone = new Zone();
			this.zone.convertFromTable(dz.getZone());
		}
		/*if(dz.getDeliverycountry() != null){
			this.deliverycountry = new DeliveryDeliveryCountry();
		}*/
	}
	public GECOObject control(){
		return null;
	}
}
