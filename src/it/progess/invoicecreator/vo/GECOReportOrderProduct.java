package it.progess.invoicecreator.vo;

import java.util.Set;

public class GECOReportOrderProduct {
	private String productcode;
	private String productdescription;
	private String productum;
	private Set<GECOReportOrderCustomerQuantity> customers;
	public String getProductcode() {
		return productcode;
	}
	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}
	public String getProductdescription() {
		return productdescription;
	}
	public void setProductdescription(String productdescription) {
		this.productdescription = productdescription;
	}
	public String getProductum() {
		return productum;
	}
	public void setProductum(String productum) {
		this.productum = productum;
	}
	public Set<GECOReportOrderCustomerQuantity> getCustomers() {
		return customers;
	}
	public void setCustomers(Set<GECOReportOrderCustomerQuantity> customers) {
		this.customers = customers;
	}
	
	
}
