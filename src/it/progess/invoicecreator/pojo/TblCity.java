package it.progess.invoicecreator.pojo;

import it.progess.invoicecreator.vo.City;
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
@Table(name="tblcity")
public class TblCity implements Itbl {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idcity")
	private int idCity;
	@Column(name="name")
	private String name;
	@ManyToOne
	@JoinColumn(name = "idcountry")
	private TblCountry country;
	@ManyToOne
	@JoinColumn(name = "idzone")
	private TblZone zone;
	
	public int getIdCity() {
		return idCity;
	}

	public void setIdCity(int idCity) {
		this.idCity = idCity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TblCountry getCountry() {
		return country;
	}

	public void setCountry(TblCountry country) {
		this.country = country;
	}

	public TblZone getZone() {
		return zone;
	}

	public void setZone(TblZone zone) {
		this.zone = zone;
	}

	public void convertToTable(Ivo vo){
		City c = (City)vo;
		this.idCity = c.getIdCity();
		this.name = c.getName();
		if (c.getZone() != null){
			this.zone = new TblZone();
			this.zone.convertToTable(c.getZone());
		}
		if (c.getCountry() != null){
			this.country = new TblCountry();
			this.country.convertToTable(c.getCountry());
		}
	}
}
