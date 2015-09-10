package it.progess.invoicecreator.vo;

import it.progess.invoicecreator.hibernate.HibernateUtils;

import java.util.ArrayList;
import java.util.Iterator;

public class Draft {
	private String id;
	private ArrayList<DraftElement> products = new ArrayList<DraftElement>();
	private float total;
	private int status;
	private User user;
	private String key;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public ArrayList<DraftElement> getProducts() {
		return products;
	}
	public void setProducts(ArrayList<DraftElement> products) {
		this.products = products;
	}
	public float getTotal() {
		return total;
	}
	public void setTotal(float total) {
		this.total = total;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public void calculateTotal(){
		this.total = 0;
		for(Iterator<DraftElement> it = this.products.iterator();it.hasNext();){
			DraftElement de = it.next();
			de.calculateEndPrice(de.getProduct());
			this.total =HibernateUtils.roundfloat(  this.total +de.getEndprice());
		}
	}
}
