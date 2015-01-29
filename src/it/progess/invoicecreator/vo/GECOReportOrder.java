package it.progess.invoicecreator.vo;

import java.util.Set;

public class GECOReportOrder {
	private String suppliername;
	private Set<GECOReportOrderProduct> products;
	public String getSuppliername() {
		return suppliername;
	}
	public void setSuppliername(String suppliername) {
		this.suppliername = suppliername;
	}
	public Set<GECOReportOrderProduct> getProducts() {
		return products;
	}
	public void setProducts(Set<GECOReportOrderProduct> products) {
		this.products = products;
	}
	
}
