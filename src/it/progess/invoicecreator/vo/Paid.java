package it.progess.invoicecreator.vo;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import it.progess.invoicecreator.hibernate.DataUtilConverter;
import it.progess.invoicecreator.pojo.Itbl;
import it.progess.invoicecreator.pojo.TblPaid;
import it.progess.invoicecreator.pojo.TblPaidSuspended;
import it.progess.invoicecreator.pojo.TblRow;
import it.progess.invoicecreator.pojo.TblSupplier;
import it.progess.invoicecreator.properties.GECOParameter;



public class Paid implements Ivo,Comparable<Paid> {
	private int idPaid;
	private String date;
	private String description;
	private String note;
	private boolean customer;
	private boolean supplier;
    private double amount;
	private Customer customer_paid;
	private Supplier supplier_paid;
	private Set<PaidSuspended> paids;
	private Set<Suspended> susp;
	public Supplier getSupplier_paid() {
		return supplier_paid;
	}
	public void setSupplier_paid(Supplier supplier_paid) {
		this.supplier_paid = supplier_paid;
	}
	
	public Set<Suspended> getSusp() {
		return susp;
	}
	public void setSusp(Set<Suspended> susp) {
		this.susp = susp;
	}
	public Set<PaidSuspended> getPaids() {
		return paids;
	}
	public void setPaids(Set<PaidSuspended> paids) {
		this.paids = paids;
	}
	public Customer getCustomer_paid() {
		return customer_paid;
	}
	public void setCustomer_paid(Customer customer_paid) {
		this.customer_paid = customer_paid;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public int getIdPaid() {
		return idPaid;
	}
	public void setIdPaid(int idPaid) {
		this.idPaid = idPaid;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public boolean isCustomer() {
		return customer;
	}
	public void setCustomer(boolean customer) {
		this.customer = customer;
	}
	public boolean isSupplier() {
		return supplier;
	}
	public void setSupplier(boolean supplier) {
		this.supplier = supplier;
	}
	public void convertFromTable(Itbl obj){
		TblPaid p = (TblPaid)obj;
		this.customer = p.isCustomer();
		this.date = DataUtilConverter.convertStringFromDate(p.getDate());
		this.description = p.getDescription();
		this.idPaid = p.getIdPaid();
		this.note = p.getNote();
		this.supplier = p.isSupplier();
		this.amount = p.getAmount();
		if (p.getCustomer_paid() != null){
			this.customer_paid = new Customer();
			this.customer_paid.convertFromTable(p.getCustomer_paid());
		}
		if (p.getSupplier_paid() != null){
			this.supplier_paid = new Supplier();
			this.supplier_paid.convertFromTable(p.getSupplier_paid());
		}
		if (p.getPaids() != null){
			this.paids = new HashSet<PaidSuspended>();
			for (Iterator<TblPaidSuspended> iterator = p.getPaids().iterator(); iterator.hasNext();){
				
				TblPaidSuspended paidsusp = iterator.next();
				PaidSuspended pstbl = new PaidSuspended();
				pstbl.convertFromTable(paidsusp);
				pstbl.setPaid(this);
				this.paids.add(pstbl);
			}
		}
	}
	public void convertFromTableForList(Itbl obj){
		TblPaid p = (TblPaid)obj;
		this.customer = p.isCustomer();
		this.date = DataUtilConverter.convertStringFromDate(p.getDate());
		this.description = p.getDescription();
		this.idPaid = p.getIdPaid();
		this.note = p.getNote();
		this.supplier = p.isSupplier();
		this.amount = p.getAmount();
		if (p.getCustomer_paid() != null){
			this.customer_paid = new Customer();
			this.customer_paid.convertFromTable(p.getCustomer_paid());
		}
		if (p.getSupplier_paid() != null){
			this.supplier_paid = new Supplier();
			this.supplier_paid.convertFromTable(p.getSupplier_paid());
		}
	
	}
	@Override
	public int compareTo(Paid s){
		if (DataUtilConverter.convertDateFromString(s.getDate()).before(DataUtilConverter.convertDateFromString(this.getDate())))
            return 1;
		else
			return -1;
	}
	public GECOError control(){
		GECOError er = null;
		if (this.date == null || this.date ==""){
			er = new GECOError(GECOParameter.ERROR_VALUE_MISSING,"Data mancante");
		}
		if (this.supplier_paid == null && this.customer_paid == null ){
			er = new GECOError(GECOParameter.ERROR_VALUE_MISSING,"Destinatario o Emissario del pagamento mancante");
		}
		return er;
	}
}
