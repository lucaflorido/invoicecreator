package it.progess.invoicecreator.vo;

import it.progess.invoicecreator.pojo.Itbl;
import it.progess.invoicecreator.pojo.TblCity;


public class City implements Ivo {
	private int idCity;
	private String name;
	private Country country;
	private Zone zone;
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
	public Country getCountry() {
		return country;
	}
	public void setCountry(Country country) {
		this.country = country;
	}
	public Zone getZone() {
		return zone;
	}
	public void setZone(Zone zone) {
		this.zone = zone;
	}
	public void convertFromTable(Itbl tbl){
		TblCity c = (TblCity)tbl;
		this.idCity = c.getIdCity();
		this.name = c.getName();
		if (c.getZone() != null){
			this.zone = new Zone();
			this.zone.convertFromTable(c.getZone());
		}
		if (c.getCountry() != null){
			this.country = new Country();
			this.country.convertFromTable(c.getCountry());
		}
	}
	public GECOObject control(){
		return null;
	}
}
