package it.progess.invoicecreator.print;

import it.progess.invoicecreator.vo.Company;
import it.progess.invoicecreator.vo.Head;
import it.progess.invoicecreator.vo.Row;

public class PrintCompany {
	public String azienda_nome;
	public String azienda_indirizzo;
	public String azienda_indirizzo2;
	public String azienda_pi;
	public String azienda_cf;
	public String azienda_telefono;
	public String azienda_fax;
	public String azienda_email;
	public String azienda_codice;
	public String azienda_provincia;
	public String azienda_cellulare;
	
	
	public String getAzienda_cellulare() {
		return azienda_cellulare;
	}
	public void setAzienda_cellulare(String azienda_cellulare) {
		this.azienda_cellulare = azienda_cellulare;
	}
	public String getAzienda_codice() {
		return azienda_codice;
	}
	public void setAzienda_codice(String azienda_codice) {
		this.azienda_codice = azienda_codice;
	}
	public String getAzienda_provincia() {
		return azienda_provincia;
	}
	public void setAzienda_provincia(String azienda_provincia) {
		this.azienda_provincia = azienda_provincia;
	}
	public String getAzienda_telefono() {
		return azienda_telefono;
	}
	public void setAzienda_telefono(String azienda_telefono) {
		this.azienda_telefono = azienda_telefono;
	}
	public String getAzienda_fax() {
		return azienda_fax;
	}
	public void setAzienda_fax(String azienda_fax) {
		this.azienda_fax = azienda_fax;
	}
	public String getAzienda_email() {
		return azienda_email;
	}
	public void setAzienda_email(String azienda_email) {
		this.azienda_email = azienda_email;
	}
	public String getAzienda_nome() {
		return azienda_nome;
	}
	public void setAzienda_nome(String azienda_nome) {
		this.azienda_nome = azienda_nome;
	}
	public String getAzienda_indirizzo() {
		return azienda_indirizzo;
	}
	public void setAzienda_indirizzo(String azienda_indirizzo) {
		this.azienda_indirizzo = azienda_indirizzo;
	}
	public String getAzienda_indirizzo2() {
		return azienda_indirizzo2;
	}
	public void setAzienda_indirizzo2(String azienda_indirizzo2) {
		this.azienda_indirizzo2 = azienda_indirizzo2;
	}
	public String getAzienda_pi() {
		return azienda_pi;
	}
	public void setAzienda_pi(String azienda_pi) {
		this.azienda_pi = azienda_pi;
	}
	public String getAzienda_cf() {
		return azienda_cf;
	}
	public void setAzienda_cf(String azienda_cf) {
		this.azienda_cf = azienda_cf;
	}
	public void setFromObject(Company comp){
		if (comp != null){
			this.azienda_cf = this.getValue(comp.getTaxcode());
			this.azienda_indirizzo = this.getValue(comp.getAddress().getAddress()+" "+comp.getAddress().getNumber());
			this.azienda_indirizzo2 = this.getValue(comp.getAddress().getZipcode()+" "+comp.getAddress().getCity()+" ("+comp.getAddress().getZone()+")");
			this.azienda_nome =this.getValue(comp.getCompanyname());
			this.azienda_telefono = setupPhones(comp.getContact().getPhone1(), comp.getContact().getPhone2());
			this.azienda_cellulare = setupPhones(comp.getContact().getMobile1(), comp.getContact().getMobile2());
			this.azienda_fax = this.getValue(comp.getContact().getFax());
			this.azienda_email = this.getValue(comp.getContact().getEmail1());
			this.azienda_codice = this.getValue(comp.getCompanycode());
			this.azienda_provincia = this.getValue(comp.getCompanyzone());
			this.azienda_pi = this.getValue(comp.getCompanynumber());
			
		}
	}
	public String getValue(String value){
		return getValue(value,"");
	}
	public String getValue(String value,String defaultVal){
		if (value != null){
			return value;
		}else{
			return defaultVal;
		}
	}
	public String setupPhones(String phone1,String phone2){
		if ((phone1 == null || phone1.equals("")) && (phone2 == null || phone2.equals(""))){
			return "";
		}else{
			if (phone1 != null && phone1.equals("") == false){
				if (phone2 != null && phone2.equals("") == false){
					return phone1 +" - "+phone2;
				}else{
					return phone1;
				}
			}else if (phone2 != null && phone2.equals("") == false){
				return phone2;
			}else{
				return "";
			}

		}
	}
}
