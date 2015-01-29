package it.progess.invoicecreator.vo;

import java.util.Date;

import javax.persistence.Column;

import it.progess.invoicecreator.hibernate.DataUtilConverter;
import it.progess.invoicecreator.pojo.Itbl;
import it.progess.invoicecreator.pojo.TblListProduct;
import it.progess.invoicecreator.properties.GECOParameter;


public class ListProduct implements Ivo {
	private int idListProduct;
	private List list;
	private Product product;
	private float price;
	private String startdate;
	private boolean active;
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public int getIdListProduct() {
		return idListProduct;
	}
	public void setIdListProduct(int idListProduct) {
		this.idListProduct = idListProduct;
	}
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}

	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public void convertFromTable(Itbl obj){
		TblListProduct ltp = (TblListProduct)obj;
		this.idListProduct = ltp.getIdListProduct();
		this.price = ltp.getPrice();
		this.product = new Product();
		this.active = ltp.isActive();
		this.startdate = DataUtilConverter.convertStringFromDate(ltp.getStartdate());
		if (ltp.getProduct()!=null){
			this.product.convertFromTable(ltp.getProduct());
		}
	}
	public void copy(ListProduct ltp){
		this.price = ltp.getPrice();
		this.product = new Product();
		if (ltp.getProduct()!=null){
			this.product = ltp.getProduct();
		}
	}
	public void convertFromTableForSaving(Itbl obj){
		TblListProduct ltp = (TblListProduct)obj;
		this.idListProduct = ltp.getIdListProduct();
		this.price = ltp.getPrice();
		this.product = new Product();
		this.active = ltp.isActive();
		this.startdate = DataUtilConverter.convertStringFromDate(ltp.getStartdate());
		if (ltp.getProduct()!=null){
			this.product.convertFromTable(ltp.getProduct());
		}
	}
	public GECOObject control(){
		return null;
	}
}
