package it.progess.invoicecreator.vo;

import it.progess.invoicecreator.pojo.Itbl;
import it.progess.invoicecreator.pojo.TblAddress;
import it.progess.invoicecreator.properties.GECOParameter;

public class Address implements Ivo {
	private int idaddress;
	private String address;
	private String city;
	private String zone;
	private String zipcode;
	private String number;
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
	public void convertFromTable(Itbl obj){
		TblAddress ad = (TblAddress)obj;
		this.address = ad.getAddress();
		this.city = ad.getCity();
		this.idaddress = ad.getIdaddress();
		this.zipcode = ad.getZipcode();
		this.zone = ad.getZone();
		this.number = ad.getNumber();
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
