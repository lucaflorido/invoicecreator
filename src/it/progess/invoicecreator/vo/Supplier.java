package it.progess.invoicecreator.vo;

import it.progess.invoicecreator.pojo.Itbl;
import it.progess.invoicecreator.pojo.TblSupplier;
import it.progess.invoicecreator.pojo.TblListSupplier;
import it.progess.invoicecreator.properties.GECOParameter;
import it.progess.transport.check.ProgessCheck;
import it.progess.transport.vo.ProgessError;
import it.progess.validator.CFPIValidator;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Supplier implements Ivo{
	private int idSupplier;
	private String suppliername;
	private String suppliercode;
	private boolean active;
	private Contact contact;
	private Address address;
	private String taxcode;
	private String serialnumber;
	private GroupSupplier group;
	private CategorySupplier category;
	private BankContact bankcontact;
	private Set<ListSupplier> lists;
	private Payment payment;
	
	public Payment getPayment() {
		return payment;
	}
	public void setPayment(Payment payment) {
		this.payment = payment;
	}
	public int getIdSupplier() {
		return idSupplier;
	}
	public void setIdSupplier(int idSupplier) {
		this.idSupplier = idSupplier;
	}
	public String getSuppliername() {
		return suppliername;
	}
	public void setSuppliername(String suppliername) {
		this.suppliername = suppliername;
	}
	public String getSuppliercode() {
		return suppliercode;
	}
	public void setSuppliercode(String suppliercode) {
		this.suppliercode = suppliercode;
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
	public GroupSupplier getGroup() {
		return group;
	}
	public void setGroup(GroupSupplier group) {
		this.group = group;
	}
	public CategorySupplier getCategory() {
		return category;
	}
	public void setCategory(CategorySupplier category) {
		this.category = category;
	}
	public BankContact getBankcontact() {
		return bankcontact;
	}
	public void setBankcontact(BankContact bankcontact) {
		this.bankcontact = bankcontact;
	}
	public Set<ListSupplier> getLists() {
		return lists;
	}
	public void setLists(Set<ListSupplier> lists) {
		this.lists = lists;
	}
	public void convertFromTable(Itbl obj){
		TblSupplier c = (TblSupplier)obj;
		this.active = c.isActive();
		
		if (c.getAddress() != null){
			this.address = new Address();
			this.address.convertFromTable(c.getAddress());
		}
		
		if (c.getBankcontact()!= null){
			this.bankcontact = new BankContact();
			this.bankcontact.convertFromTable(c.getBankcontact());
		}
		
		if(c.getCategory() != null){
			this.category = new CategorySupplier();
			this.category.convertFromTable(c.getCategory());
		}
		
		if(c.getContact() != null){
			this.contact = new Contact();
			this.contact.convertFromTable(c.getContact());
		}
		this.suppliercode = c.getSuppliercode();
		this.suppliername = c.getSuppliername();
		
		if (c.getGroup() != null){
			this.group = new GroupSupplier();
			this.group.convertFromTable(c.getGroup());
		}
		if (c.getPayment() != null){
			this.payment = new Payment();
			this.payment.convertFromTable(c.getPayment());
		}
		this.idSupplier = c.getIdSupplier();
		this.serialnumber = c.getSerialnumber();
		this.taxcode = c.getTaxcode();
		this.lists = new HashSet<ListSupplier>();
	}
	public void convertFromTableSingle(Itbl obj){
		TblSupplier c = (TblSupplier)obj;
		this.convertFromTable(obj);
		this.lists = new HashSet<ListSupplier>();
		if (c.getLists() != null){
			for (Iterator<TblListSupplier> iterator = c.getLists().iterator(); iterator.hasNext();){
				TblListSupplier listproduct = iterator.next();
				ListSupplier listp = new ListSupplier();
				listp.convertFromTable(listproduct);
				this.lists.add(listp);
			}
		}
		
	}
	public GECOError control(){
		GECOError er = null;
		if (this.suppliercode == null || this.suppliercode ==""){
			er = new GECOError(GECOParameter.ERROR_VALUE_MISSING,"Codice mancante");
		}
		if (this.suppliername == null || this.suppliername ==""){
			er = new GECOError(GECOParameter.ERROR_VALUE_MISSING,"Ragione Sociale mancante");
		}
		if (this.payment == null){
			er = new GECOError(GECOParameter.ERROR_VALUE_MISSING,"Pagamento Mancante");
		}
		if (ProgessCheck.basicCheck(CFPIValidator.checkCFPI(this.taxcode, this.serialnumber)) == false){
			ProgessError pe = (ProgessError)CFPIValidator.checkCFPI(this.taxcode, this.serialnumber);
			return new GECOError(pe.getErrorName(),pe.getErrorMessage());
		}
		
		return er;
	}
}
