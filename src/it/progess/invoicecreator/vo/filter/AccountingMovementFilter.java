package it.progess.invoicecreator.vo.filter;

import it.progess.invoicecreator.vo.Customer;
import it.progess.invoicecreator.vo.Supplier;

public class AccountingMovementFilter {
	private boolean customer;
	private boolean supplier;
	private boolean all;
	private Customer singlecustomer;
	private Supplier singlesupplier;
	private String fromDate;
	private String toDate;
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
	public boolean isAll() {
		return all;
	}
	public void setAll(boolean all) {
		this.all = all;
	}
	public Customer getSinglecustomer() {
		return singlecustomer;
	}
	public void setSinglecustomer(Customer singlecustomer) {
		this.singlecustomer = singlecustomer;
	}
	public Supplier getSinglesupplier() {
		return singlesupplier;
	}
	public void setSinglesupplier(Supplier singlesupplier) {
		this.singlesupplier = singlesupplier;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	
}
