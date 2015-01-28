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

import it.progess.invoicecreator.vo.Ivo;
import it.progess.invoicecreator.vo.Transporter;
@Entity
@Table(name="tbltransporter")
public class TblTransporter implements Itbl {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idTransporter")
	private int idTransporter;
	@Column(name="transportercode")
	private String transportercode;
	@Column(name="transportername")
	private String transportername;
	@Column(name="transportersurname")
	private String transportersurname;
	@Column(name="transporterdescription")
	private String transporterdescription;
	@Column(name="platenumber")
	private String platenumber;
	@Column(name="taxcode")
	private String taxcode;
	@Column(name="serialnumber")
	private String serialnumber;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idAddress")
	private TblAddress address;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idContact")
	private TblContact contact;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idBankContact")
	private TblBankContact bankcontact;
	public int getIdTransporter() {
		return idTransporter;
	}
	public void setIdTransporter(int idTransporter) {
		this.idTransporter = idTransporter;
	}
	public String getTransportercode() {
		return transportercode;
	}
	public void setTransportercode(String transportercode) {
		this.transportercode = transportercode;
	}
	public String getTransportername() {
		return transportername;
	}
	public void setTransportername(String transportername) {
		this.transportername = transportername;
	}
	public String getTransportersurname() {
		return transportersurname;
	}
	public void setTransportersurname(String transportersurname) {
		this.transportersurname = transportersurname;
	}
	public String getTransporterdescription() {
		return transporterdescription;
	}
	public void setTransporterdescription(String transporterdescription) {
		this.transporterdescription = transporterdescription;
	}
	public String getPlatenumber() {
		return platenumber;
	}
	public void setPlatenumber(String platenumber) {
		this.platenumber = platenumber;
	}
	
	public TblAddress getAddress() {
		return address;
	}
	public void setAddress(TblAddress address) {
		this.address = address;
	}
	public TblContact getContact() {
		return contact;
	}
	public void setContact(TblContact contact) {
		this.contact = contact;
	}
	public TblBankContact getBankcontact() {
		return bankcontact;
	}
	public void setBankcontact(TblBankContact bankcontact) {
		this.bankcontact = bankcontact;
	}
	public String getTaxcode() {
		return taxcode;
	}
	public void setTaxcode(String taxcode) {
		this.taxcode = taxcode;
	}
	public String getSerialnumber() {
		return serialnumber;
	}
	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}
	public void convertToTable(Ivo obj){
	    Transporter t = (Transporter)obj;
	    this.idTransporter = t.getIdTransporter();
	    this.transportercode = t.getTransportercode();
	    this.transportername = t.getTransportername();
	    this.transportersurname = t.getTransportersurname();
	    this.transporterdescription = t.getTransporterdescription();
	    this.platenumber = t.getPlatenumber();
	    this.taxcode  = t.getTaxcode();
	    this.serialnumber = t.getSerialnumber();
	    if (t.getAddress() != null){
			this.address = new TblAddress();
			this.address.convertToTable(t.getAddress());
		}
		if (t.getBankcontact()!= null){
			this.bankcontact = new TblBankContact();
			this.bankcontact.convertToTable(t.getBankcontact());
		}
		if(t.getContact() != null){
			this.contact = new TblContact();
			this.contact.convertToTable(t.getContact());
		}
		
	}
	
}
