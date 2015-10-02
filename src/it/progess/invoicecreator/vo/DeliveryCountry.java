package it.progess.invoicecreator.vo;

import it.progess.invoicecreator.pojo.Itbl;

import it.progess.invoicecreator.pojo.TblDeliveryCountry;

public class DeliveryCountry implements Ivo{
	private int iddeliverycountry;
	
	private float price;
	
	private float bound;
	
	private Country country;
	
	public int getIddeliverycountry() {
		return iddeliverycountry;
	}

	public void setIddeliverycountry(int iddeliverycountry) {
		this.iddeliverycountry = iddeliverycountry;
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

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}
	public void convertFromTable(Itbl tb){
		TblDeliveryCountry dc = (TblDeliveryCountry)tb;
		this.iddeliverycountry = dc.getIddeliverycountry();
		this.bound = dc.getBound();
		this.price = dc.getPrice();
		if (dc.getCountry() != null){
			this.country = new Country();
			this.country.convertFromTable(dc.getCountry());
		}
	}
	public GECOObject control(){
		return null;
	}
}
