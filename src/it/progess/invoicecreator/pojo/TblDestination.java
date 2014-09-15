package it.progess.invoicecreator.pojo;
import it.progess.invoicecreator.vo.Destination;
import it.progess.invoicecreator.vo.Ivo;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table(name="tbldestination")
public class TblDestination implements Itbl {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idDestination")
	private int idDestination;
	@Column(name="destinationname")
	private String destinationname;
	@Column(name="destinationcode")
	private String destinationcode;
	@Column(name="active")
	private boolean active;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idContact")
	private TblContact contact;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idAddress")
	private TblAddress address;
	@Column(name="taxcode")
	private String taxcode;
	@Column(name="serialnumber")
	private String serialnumber;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idBankContact")
	private TblBankContact bankcontact;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idCustomer")
	private TblCustomer customer;
	public int getIdDestination() {
		return idDestination;
	}
	public void setIdDestination(int idDestination) {
		this.idDestination = idDestination;
	}
	public String getDestinationname() {
		return destinationname;
	}
	public void setDestinationname(String destinationname) {
		this.destinationname = destinationname;
	}
	public String getDestinationcode() {
		return destinationcode;
	}
	public void setDestinationcode(String destinationcode) {
		this.destinationcode = destinationcode;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
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
	public TblBankContact getBankcontact() {
		return bankcontact;
	}
	public void setBankcontact(TblBankContact bankcontact) {
		this.bankcontact = bankcontact;
	}
	public TblCustomer getCustomer() {
		return customer;
	}
	public void setCustomer(TblCustomer customer) {
		this.customer = customer;
	}
	public void convertToTable(Ivo obj){
		Destination c = (Destination)obj;
		this.active = c.isActive();
		if (c.getAddress() != null){
			this.address = new TblAddress();
			this.address.convertToTable(c.getAddress());
		}
		if (c.getBankcontact()!= null){
			this.bankcontact = new TblBankContact();
			this.bankcontact.convertToTable(c.getBankcontact());
		}
		if(c.getContact() != null){
			this.contact = new TblContact();
			this.contact.convertToTable(c.getContact());
		}
		if(c.getCustomer() != null){
			this.customer = new TblCustomer();
			this.customer.convertToTable(c.getCustomer());
		}
		this.destinationcode = c.getDestinationcode();
		this.destinationname = c.getDestinationname();
		this.idDestination = c.getIdDestination();
		this.serialnumber = c.getSerialnumber();
		this.taxcode = c.getTaxcode();
	}
	
}
