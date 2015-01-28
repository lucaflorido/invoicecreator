package it.progess.invoicecreator.vo;

import java.util.Set;

public class GenerateDocsObject {
	private Document generateDoc;
	private String date;
	private boolean groupBySupplier;
	private boolean groupByCustomer;
	private String rowtype;
	private boolean execute;
	private Set<Head> heads;
	private List list;
	private Customer customer;
	private Supplier supplier;
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Supplier getSupplier() {
		return supplier;
	}
	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
	public Document getGenerateDoc() {
		return generateDoc;
	}
	public void setGenerateDoc(Document generateDoc) {
		this.generateDoc = generateDoc;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public boolean isGroupBySupplier() {
		return groupBySupplier;
	}
	public void setGroupBySupplier(boolean groupBySupplier) {
		this.groupBySupplier = groupBySupplier;
	}
	public boolean isGroupByCustomer() {
		return groupByCustomer;
	}
	public void setGroupByCustomer(boolean groupByCustomer) {
		this.groupByCustomer = groupByCustomer;
	}
	public String getRowtype() {
		return rowtype;
	}
	public void setRowtype(String rowtype) {
		this.rowtype = rowtype;
	}
	public boolean isExecute() {
		return execute;
	}
	public void setExecute(boolean execute) {
		this.execute = execute;
	}
	public Set<Head> getHeads() {
		return heads;
	}
	public void setHeads(Set<Head> heads) {
		this.heads = heads;
	}
}
