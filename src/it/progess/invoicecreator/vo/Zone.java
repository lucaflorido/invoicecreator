package it.progess.invoicecreator.vo;

import it.progess.invoicecreator.pojo.Itbl;
import it.progess.invoicecreator.pojo.TblCountry;
import it.progess.invoicecreator.pojo.TblZone;
public class Zone implements Ivo {
	private int idZone;
	private String name;
	private Country country;
	
	public int getIdZone() {
		return idZone;
	}
	public void setIdZone(int idZone) {
		this.idZone = idZone;
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
	public void convertFromTable(Itbl tbl){
		TblZone z = (TblZone)tbl;
	    this.idZone = z.getIdZone();
	    this.name = z.getName();
	    if (z.getCountry() != null){
	    	this.country = new Country();
	    	this.country.convertFromTable(z.getCountry());
	    }
	}
	public GECOObject control(){
		return null;
	}
}
