package it.progess.invoicecreator.vo;

import it.progess.invoicecreator.pojo.Itbl;
import it.progess.invoicecreator.pojo.TblCounterYear;


public class CounterYear implements Ivo {
	private int idCounterYear;
	private int year;
	private int value;
	private Counter counter;
	public int getIdCounterYear() {
		return idCounterYear;
	}
	public Counter getCounter() {
		return counter;
	}
	public void setCounter(Counter counter) {
		this.counter = counter;
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
	public void convertFromTable(Itbl obj){
		TblCounterYear cy = (TblCounterYear)obj;
		this.idCounterYear = cy.getIdCounterYear();
		this.value = cy.getValue();
		this.year = cy.getYear();
	}
	public GECOObject control(){
		
		return null;
	}
}
