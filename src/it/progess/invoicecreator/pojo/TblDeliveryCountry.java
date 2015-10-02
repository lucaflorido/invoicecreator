package it.progess.invoicecreator.pojo;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import it.progess.invoicecreator.vo.Country;
import it.progess.invoicecreator.vo.DeliveryCountry;
import it.progess.invoicecreator.vo.Ivo;
@Entity
@Table(name="tbldelivery_country")
public class TblDeliveryCountry implements Itbl{
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="iddeliverycountry")
	private int iddeliverycountry;
	@Column(name="price")
	private float price;
	@Column(name="bound")
	private float bound;
	@ManyToOne
	@JoinColumn(name="idcountry")
	private TblCountry country;
	
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

	public TblCountry getCountry() {
		return country;
	}

	public void setCountry(TblCountry country) {
		this.country = country;
	}

	public void convertToTable(Ivo vo){
		DeliveryCountry dc = (DeliveryCountry)vo;
		this.iddeliverycountry = dc.getIddeliverycountry();
		this.bound = dc.getBound();
		this.price = dc.getPrice();
		if (dc.getCountry() != null){
			this.country = new TblCountry();
			this.country.convertToTable(dc.getCountry());
		}
	}
}
