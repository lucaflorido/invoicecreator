package it.progess.invoicecreator.print;

public class TaxRateCollection implements Comparable<TaxRateCollection> {
	public String codice;
	public String imponibile;
	public String totale;
	public float impo;
	public float tot;
	public TaxRateCollection(){
		
	}
	public TaxRateCollection(String code,Float impo,Float tot){
		this.codice = code;
		this.impo = impo;
		this.tot = tot;
	}
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
	public void setValues(){
		this.imponibile = forceTwoDecimal(String.valueOf(impo));
		this.totale = forceTwoDecimal(String.valueOf(tot));
	}
	private String forceTwoDecimal(String value){
		String[] numberArray = value.split("\\."); 
		if (numberArray.length == 1){
			return numberArray[0]+",00";
		}else if (numberArray.length == 2){
			if (numberArray[1].length() == 1){
				return numberArray[0]+","+numberArray[1]+"0";
			}else{
				return numberArray[0]+","+numberArray[1];
			}
		}else{
			return value;
		}
	}
}
