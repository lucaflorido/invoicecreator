package it.progess.invoicecreator.pojo;

import it.progess.invoicecreator.vo.City;
import it.progess.invoicecreator.vo.DeliveryCity;
import it.progess.invoicecreator.vo.DeliveryZone;
import it.progess.invoicecreator.vo.Ivo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="tbldelivery_city")
public class TblDeliveryCity implements Itbl {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="iddeliverycity")
	private int iddeliverycity;
	@Column(name="price")
	private float price;
	@Column(name="bound")
	private float bound;
	@ManyToOne
	@JoinColumn(name="idcity")
	private TblCity city;
	@ManyToOne
	@JoinColumn(name="idDeliveryDeliveryCountry")
	private TblDeliveryDeliveryCountry deliverycountry;
	
	
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
	
	
	public TblDeliveryDeliveryCountry getDeliverycountry() {
		return deliverycountry;
	}
	public void setDeliverycountry(TblDeliveryDeliveryCountry deliverycountry) {
		this.deliverycountry = deliverycountry;
	}
	
	public int getIddeliverycity() {
		return iddeliverycity;
	}
	public void setIddeliverycity(int iddeliverycity) {
		this.iddeliverycity = iddeliverycity;
	}
	public TblCity getCity() {
		return city;
	}
	public void setCity(TblCity city) {
		this.city = city;
	}
	public void convertToTable(Ivo vo){
		DeliveryCity dz = (DeliveryCity)vo;
		this.bound = dz.getBound();
		this.price = dz.getPrice();
		this.iddeliverycity = dz.getIddeliverycity();
		if (dz.getCity() != null){
			this.city = new TblCity();
			this.city.convertToTable(dz.getCity());
		}
	}
}
