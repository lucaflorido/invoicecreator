package it.progess.invoicecreator.vo;

import java.util.ArrayList;

public class Draft {
	private String id;
	private ArrayList<Product> products;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public ArrayList<Product> getProducts() {
		return products;
	}
	public void setProducts(ArrayList<Product> products) {
		this.products = products;
	}
}
