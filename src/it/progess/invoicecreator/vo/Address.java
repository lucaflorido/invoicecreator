package it.progess.invoicecreator.vo;

import javax.persistence.Column;

import it.progess.invoicecreator.pojo.Itbl;
import it.progess.invoicecreator.pojo.TblAddress;
import it.progess.invoicecreator.pojo.TblCity;
import it.progess.invoicecreator.pojo.TblCountry;
import it.progess.invoicecreator.pojo.TblZone;
import it.progess.invoicecreator.properties.GECOParameter;

public class Address implements Ivo {
	private int idaddress;
	private String address;
	private String city;
	private String zone;
	private String zipcode;
	private String number;
	private Country countryObj;
	private Zone zoneObj;
	private City cityObj;
	private String country;
	private String name;
	
	private String code;
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public int getIdaddress() {
		return idaddress;
	}
	public void setIdaddress(int idaddress) {
		this.idaddress = idaddress;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	
	public Country getCountryObj() {
		return countryObj;
	}
	public void setCountryObj(Country countryObj) {
		this.countryObj = countryObj;
	}
	public Zone getZoneObj() {
		return zoneObj;
	}
	public void setZoneObj(Zone zoneObj) {
		this.zoneObj = zoneObj;
	}
	public City getCityObj() {
		return cityObj;
	}
	public void setCityObj(City cityObj) {
		this.cityObj = cityObj;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void convertFromTable(Itbl obj){
		TblAddress ad = (TblAddress)obj;
		this.address = ad.getAddress();
		this.city = ad.getCity();
		this.idaddress = ad.getIdaddress();
		this.zipcode = ad.getZipcode();
		this.zone = ad.getZone();
		this.number = ad.getNumber();
		this.country = ad.getCountry();
		this.code = ad.getCode();
		this.name = ad.getName();
		if (ad.getCountryObj() != null){
			this.countryObj = new Country();
			this.countryObj.convertFromTable(ad.getCountryObj());
		}
		if (ad.getZoneObj() != null){
			this.zoneObj = new Zone();
			this.zoneObj.convertFromTable(ad.getZoneObj());
		}
		if (ad.getCityObj() != null){
			this.cityObj = new City();
			this.cityObj.convertFromTable(ad.getCityObj());
		}
	}
	public GECOObject control(){
		if (this.address == null || this.address.equals("") == true){
			return new GECOError(GECOParameter.ERROR_VALUE_MISSING,"Indirizzo Mancante");
		}
		if (this.city == null || this.city.equals("") == true){
			return new GECOError(GECOParameter.ERROR_VALUE_MISSING,"Città Mancante");
		}
		return null;
	}
}
