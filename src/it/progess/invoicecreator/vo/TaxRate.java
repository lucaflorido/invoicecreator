package it.progess.invoicecreator.vo;

import it.progess.invoicecreator.pojo.Itbl;
import it.progess.invoicecreator.pojo.TblTaxrate;
import it.progess.invoicecreator.properties.GECOParameter;



public class TaxRate implements Ivo {
	private int idtaxrate;
	private String description;
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
	public void convertFromTable(Itbl itbl){
		TblTaxrate taxrate = (TblTaxrate)itbl;
		this.setIdtaxrate(taxrate.getIdtaxrate());
		this.setDescription(taxrate.getDescription());
		this.setValue(taxrate.getValue());
	}
	public GECOError control(){
		GECOError ge = null;
		
		if (this.getDescription() == null || this.getDescription() == "" ){
			ge = new GECOError(GECOParameter.ERROR_VALUE_MISSING,"Descrizione Mancante");
		}
		if (this.getValue() == 0  ){
			ge = new GECOError(GECOParameter.ERROR_VALUE_MISSING,"Valore Mancante");
		}
		return ge;
	}
}
