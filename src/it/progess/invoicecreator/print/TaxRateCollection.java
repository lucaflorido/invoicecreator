package it.progess.invoicecreator.print;

public class TaxRateCollection implements Comparable<TaxRateCollection> {
	public String codice;
	public String imponibile;
	public String totale;
	public String getCodice() {
		return codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
	public String getImponibile() {
		return imponibile;
	}
	public void setImponibile(String imponibile) {
		this.imponibile = imponibile;
	}
	public String getTotale() {
		return totale;
	}
	public void setTotale(String totale) {
		this.totale = totale;
	}
	@Override
	public int compareTo(TaxRateCollection p){
		return this.codice.compareTo(p.getCodice());
	}
}
