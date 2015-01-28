package it.progess.invoicecreator.pojo;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import it.progess.invoicecreator.vo.Bank;

import it.progess.invoicecreator.vo.Ivo;
@Entity
@Table(name="tblbank")
public class TblBank implements Itbl{
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idBank")
	private int idBank;
	@Column(name="bankname")
	private String bankname;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="idContact")
	private TblContact contact;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="idAddress")
	private TblAddress address;
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
	public TblContact getContact() {
		return contact;
	}
	public void setContact(TblContact contact) {
		this.contact = contact;
	}
	public TblAddress getAddress() {
		return address;
	}
	public void setAddress(TblAddress address) {
		this.address = address;
	}
	public void convertToTable(Ivo obj){
		Bank bk = (Bank)obj;
		this.bankname = bk.getBankname();
		this.idBank = bk.getIdBank();
		if(bk.getContact() != null){
			this.contact = new TblContact();
			this.contact.convertToTable(bk.getContact());
		}
		if (bk.getAddress() != null){
			this.address = new TblAddress();
			this.address.convertToTable(bk.getAddress());
		}
	}
}
