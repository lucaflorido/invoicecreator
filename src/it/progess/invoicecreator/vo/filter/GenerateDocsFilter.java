package it.progess.invoicecreator.vo.filter;

import it.progess.invoicecreator.vo.Customer;
import it.progess.invoicecreator.vo.Destination;
import it.progess.invoicecreator.vo.Document;
import it.progess.invoicecreator.vo.Supplier;
import it.progess.invoicecreator.vo.Transporter;

public class GenerateDocsFilter {
	private Document sourcedoc;
	private Supplier supplier;
	private Customer customer;
	private Destination destination;
	private Transporter transporter;
	private String fromDate;
	private String toDate;
	public Document getSourcedoc() {
		return sourcedoc;
	}
	public void setSourcedoc(Document sourcedoc) {
		this.sourcedoc = sourcedoc;
	}
	public Supplier getSupplier() {
		return supplier;
	}
	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Destination getDestination() {
		return destination;
	}
	public void setDestination(Destination destination) {
		this.destination = destination;
	}
	public Transporter getTransporter() {
		return transporter;
	}
	public void setTransporter(Transporter transporter) {
		this.transporter = transporter;
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
