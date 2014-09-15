package it.progess.invoicecreator.pojo;

import it.progess.invoicecreator.vo.Ivo;

import it.progess.invoicecreator.vo.TaxRate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tbltaxrate")
public class TblTaxrate implements Itbl {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idTaxRate")
	private int idtaxrate;
	@Column(name="description")
	private String description;
	@Column(name="value")
	private double value;
	public int getIdtaxrate() {
		return idtaxrate;
	}
	public void setIdtaxrate(int idtaxrate) {
		this.idtaxrate = idtaxrate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public void convertToTable(Ivo ivo){
		TaxRate taxrate = (TaxRate)ivo;
		this.setIdtaxrate(taxrate.getIdtaxrate());
		this.setDescription(taxrate.getDescription());
		this.setValue(taxrate.getValue());
	}
}
