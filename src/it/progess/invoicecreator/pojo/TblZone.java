package it.progess.invoicecreator.pojo;

import it.progess.invoicecreator.vo.Ivo;
import it.progess.invoicecreator.vo.Zone;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="tblzone")
public class TblZone implements Itbl {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idzone")
	private int idzone;
	@Column(name="name")
	private String name;
	@ManyToOne
	@JoinColumn(name = "idcountry")
	private TblCountry country;
	
	public int getIdZone() {
		return idzone;
	}

	public void setIdZone(int idZone) {
		this.idzone = idZone;
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

	public void convertToTable(Ivo vo){
		Zone z = (Zone)vo;
	    this.idzone = z.getIdZone();
	    this.name = z.getName();
	    if (z.getCountry() != null){
	    	this.country = new TblCountry();
	    	this.country.convertToTable(z.getCountry());
	    }
	}
}
