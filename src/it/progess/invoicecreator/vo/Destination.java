package it.progess.invoicecreator.vo;
import it.progess.invoicecreator.pojo.Itbl;
import it.progess.invoicecreator.pojo.TblDestination;
import it.progess.invoicecreator.properties.GECOParameter;
import it.progess.transport.check.ProgessCheck;
import it.progess.transport.vo.ProgessError;
import it.progess.validator.CFPIValidator;
public class Destination implements Ivo {
	private int idDestination;
	private String destinationname;
	private String destinationcode;
	private boolean active;
	private Contact contact;
	private Address address;
	private String taxcode;
	private String serialnumber;
	private BankContact bankcontact;
	private Customer customer;
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
	public BankContact getBankcontact() {
		return bankcontact;
	}
	public void setBankcontact(BankContact bankcontact) {
		this.bankcontact = bankcontact;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public void convertFromTable(Itbl obj){
		TblDestination c = (TblDestination)obj;
		this.active = c.isActive();
		
		if (c.getAddress() != null){
			this.address = new Address();
			this.address.convertFromTable(c.getAddress());
		}
		if (c.getBankcontact()!= null){
			this.bankcontact = new BankContact();
			this.bankcontact.convertFromTable(c.getBankcontact());
		}
		if(c.getContact() != null){
			this.contact = new Contact();
			this.contact.convertFromTable(c.getContact());
		}
		if(c.getCustomer() != null){
			this.customer = new Customer();
			this.customer.convertFromTable(c.getCustomer());
		}
		this.destinationcode = c.getDestinationcode();
		this.destinationname= c.getDestinationname();
		this.idDestination = c.getIdDestination();
		this.serialnumber = c.getSerialnumber();
		this.taxcode = c.getTaxcode();
	}
	public GECOError control(){
		GECOError er = null;
		if (this.destinationname == null || this.destinationname ==""){
			er = new GECOError(GECOParameter.ERROR_VALUE_MISSING,"Ragione Sociale mancante");
		}
		if (this.destinationcode == null || this.destinationcode ==""){
			er = new GECOError(GECOParameter.ERROR_VALUE_MISSING,"Codice mancante");
		}
		if (this.address == null){
			er = new GECOError(GECOParameter.ERROR_VALUE_MISSING,"Indirizzo mancante");
		}else if (this.address.control().type == GECOParameter.ERROR_TYPE){
			er = (GECOError)this.address.control();
		}
		if (ProgessCheck.basicCheck(CFPIValidator.checkCFPI(this.taxcode, this.serialnumber)) == false){
			ProgessError pe = (ProgessError)CFPIValidator.checkCFPI(this.taxcode, this.serialnumber);
			return new GECOError(pe.getErrorName(),pe.getErrorMessage());
		}
		if (this.serialnumber == null || this.serialnumber ==""){
			er = new GECOError(GECOParameter.ERROR_VALUE_MISSING,"Partita Iva mancante");
		}else{
			if (this.serialnumber.length() != 11){
				er = new GECOError(GECOParameter.ERROR_WRONG_SIZE,"Partita Iva non conforme");
			}
		}
		return er;
	}
}
