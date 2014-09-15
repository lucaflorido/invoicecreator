package it.progess.invoicecreator.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import it.progess.invoicecreator.vo.Address;
import it.progess.invoicecreator.vo.Ivo;
@Entity
@Table(name="tbladdress")
public class TblAddress implements Itbl {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idAddress")
	private int idaddress;
	@Column(name="address")
	private String address;
	@Column(name="city")
	private String city;
	@Column(name="zone")
	private String zone;
	@Column(name="zipcode")
	private String zipcode;
	@Column(name="number")
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
	public void convertToTable(Ivo obj){
		Address ad = (Address)obj;
		this.address = ad.getAddress();
		this.city = ad.getCity();
		this.idaddress = ad.getIdaddress();
		this.zipcode = ad.getZipcode();
		this.zone = ad.getZone();
		this.number = ad.getNumber();
	}
}
