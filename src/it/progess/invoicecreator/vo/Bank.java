package it.progess.invoicecreator.vo;

import it.progess.invoicecreator.pojo.Itbl;
import it.progess.invoicecreator.pojo.TblBank;
import it.progess.invoicecreator.properties.GECOParameter;

public class Bank implements Ivo {
	private int idBank;
	private String bankname;
	private Contact contact;
	private Address address;
	public int getIdBank() {
		return idBank;
	}
	public void setIdBank(int idBank) {
		this.idBank = idBank;
	}
	public String getBankname() {
		return bankname;
	}
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	
	public Contact getContact() {
		return contact;
	}
	public void setContact(Contact contact) {
		this.contact = contact;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public void convertFromTable(Itbl obj){
		TblBank bk = (TblBank)obj;
		this.bankname = bk.getBankname();
		this.idBank = bk.getIdBank();
		if(bk.getContact() != null){
			this.contact = new Contact();
			this.contact.convertFromTable(bk.getContact());
		}
		if (bk.getAddress() != null){
			this.address = new Address();
			this.address.convertFromTable(bk.getAddress());
		}
	}
	public GECOObject control(){
		if (this.bankname == null || this.bankname.equals("") == true){
			return new GECOError(GECOParameter.ERROR_VALUE_MISSING,"Nome della Banca Mancante");
		}
		
		return null;
	}
}
