package it.progess.invoicecreator.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import it.progess.invoicecreator.vo.Country;
import it.progess.invoicecreator.vo.Ivo;
@Entity
@Table(name="tblcountry")
public class TblCountry implements Itbl {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idcountry")
	private int idcountry;
	@Column(name="name")
	private String name;
	public int getIdCountry() {
		return idcountry;
	}
	public void setIdCountry(int idCountry) {
		this.idcountry = idCountry;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public void convertToTable(Ivo vo){
		Country c = (Country)vo;
		this.idcountry = c.getIdCountry();
		this.name = c.getName();
	}
}
