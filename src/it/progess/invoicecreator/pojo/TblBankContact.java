package it.progess.invoicecreator.pojo;

import it.progess.invoicecreator.vo.BankContact;
import it.progess.invoicecreator.vo.Ivo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tblbankcontact")
public class TblBankContact implements Itbl {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idBankContact")
	private int idBankContact;
	@Column(name="iban")
	private String iban;
	@Column(name="bicswift")
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
	public void convertToTable(Ivo obj){
		BankContact bc = (BankContact)obj;
		this.bicswift = bc.getBicswift();
		this.iban = bc.getIban();
		this.idBankContact = bc.getIdBankContact();
	}
}
