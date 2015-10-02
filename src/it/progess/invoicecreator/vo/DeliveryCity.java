package it.progess.invoicecreator.vo;

import it.progess.invoicecreator.pojo.Itbl;
import it.progess.invoicecreator.pojo.TblDeliveryCity;
import it.progess.invoicecreator.pojo.TblDeliveryZone;

public class DeliveryCity implements Ivo {
	private int iddeliverycity;
	private float price;
	private float bound;
	private City city;
	private DeliveryDeliveryCountry deliverycountry;
	
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
	
	public DeliveryDeliveryCountry getDeliverycountry() {
		return deliverycountry;
	}
	public void setDeliverycountry(DeliveryDeliveryCountry deliverycountry) {
		this.deliverycountry = deliverycountry;
	}
	
	public int getIddeliverycity() {
		return iddeliverycity;
	}
	public void setIddeliverycity(int iddeliverycity) {
		this.iddeliverycity = iddeliverycity;
	}
	public City getCity() {
		return city;
	}
	public void setCity(City city) {
		this.city = city;
	}
	public void convertFromTable(Itbl tb){
		TblDeliveryCity dz = (TblDeliveryCity)tb;
		this.bound = dz.getBound();
		this.price = dz.getPrice();
		this.iddeliverycity = dz.getIddeliverycity();
		if (dz.getCity() != null){
			this.city = new City();
			this.city.convertFromTable(dz.getCity());
		}
		/*if(dz.getDeliverycountry() != null){
			this.deliverycountry = new DeliveryDeliveryCountry();
		}*/
	}
	public GECOObject control(){
		return null;
	}
}
