package it.progess.invoicecreator.pojo;

import it.progess.invoicecreator.vo.DeliveryZone;
import it.progess.invoicecreator.vo.Ivo;
import it.progess.invoicecreator.vo.Zone;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="tbldelivery_zone")
public class TblDeliveryZone implements Itbl {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="iddeliveryzone")
	private int iddeliveryzone;
	@Column(name="price")
	private float price;
	@Column(name="bound")
	private float bound;
	@ManyToOne
	@JoinColumn(name="idzone")
	private TblZone zone;
	@ManyToOne
	@JoinColumn(name="idDeliveryDeliveryCountry")
	private TblDeliveryDeliveryCountry deliverycountry;
	
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
	public TblZone getZone() {
		return zone;
	}
	public void setZone(TblZone zone) {
		this.zone = zone;
	}
	
	public TblDeliveryDeliveryCountry getDeliverycountry() {
		return deliverycountry;
	}
	public void setDeliverycountry(TblDeliveryDeliveryCountry deliverycountry) {
		this.deliverycountry = deliverycountry;
	}
	public void convertToTable(Ivo vo){
		DeliveryZone dz = (DeliveryZone)vo;
		this.bound = dz.getBound();
		this.price = dz.getPrice();
		this.iddeliveryzone = dz.getIddeliveryzone();
		if (dz.getZone() != null){
			this.zone = new TblZone();
			this.zone.convertToTable(dz.getZone());
		}
	}
	
}
