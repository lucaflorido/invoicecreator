package it.progess.invoicecreator.pojo;

import it.progess.invoicecreator.vo.CounterYear;
import it.progess.invoicecreator.vo.Ivo;

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
@Table(name="tblcounteryear")
public class TblCounterYear implements Itbl {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idCounterYear")
	private int idCounterYear;
	@Column(name="year")
	private int year;
	@Column(name="value")
	private int value;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idCounter")
	private TblCounter counter;
	public TblCounter getCounter() {
		return counter;
	}
	public void setCounter(TblCounter counter) {
		this.counter = counter;
	}
	public int getIdCounterYear() {
		return idCounterYear;
	}
	public void setIdCounterYear(int idCounterYear) {
		this.idCounterYear = idCounterYear;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public void convertToTable(Ivo obj){
		CounterYear cy = (CounterYear)obj;
		this.idCounterYear = cy.getIdCounterYear();
		this.value = cy.getValue();
		this.year = cy.getYear();
	}
	public void convertToTableForSaving(Ivo obj,Itbl objToLink){
		CounterYear cy = (CounterYear)obj;
		this.idCounterYear = cy.getIdCounterYear();
		this.value = cy.getValue();
		this.year = cy.getYear();
		this.counter = (TblCounter)objToLink;
	}
	
}
