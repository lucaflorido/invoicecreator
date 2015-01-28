package it.progess.invoicecreator.vo;

import it.progess.invoicecreator.pojo.Itbl;
import it.progess.invoicecreator.pojo.TblBankContact;
import it.progess.invoicecreator.properties.GECOParameter;

public class BankContact implements Ivo {
	private int idBankContact;
	private String iban;
	private String bicswift;
	public int getIdBankContact() {
		return idBankContact;
	}
	public void setIdBankContact(int idBankContact) {
		this.idBankContact = idBankContact;
	}
	public String getIban() {
		return iban;
	}
	public void setIban(String iban) {
		this.iban = iban;
	}
	public String getBicswift() {
		return bicswift;
	}
	public void setBicswift(String bicswift) {
		this.bicswift = bicswift;
	}
	public void convertFromTable(Itbl obj ){
		TblBankContact bc = (TblBankContact)obj;
		this.bicswift = bc.getBicswift();
		this.iban = bc.getIban();
		this.idBankContact = bc.getIdBankContact();
	}
	public GECOObject control(){
		if (this.iban == null || this.iban.equals("") == true){
			return new GECOError(GECOParameter.ERROR_VALUE_MISSING,"IBAN Mancante");
		}
		if (this.bicswift == null || this.bicswift.equals("") == true){
			return new GECOError(GECOParameter.ERROR_VALUE_MISSING,"BICSWIFT Mancante");
		}
		return null;
	}
}
