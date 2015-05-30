package it.progess.invoicecreator.vo;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;

import it.progess.invoicecreator.hibernate.DataUtilConverter;
import it.progess.invoicecreator.hibernate.HibernateUtils;
import it.progess.invoicecreator.pojo.Itbl;
import it.progess.invoicecreator.pojo.TblListProduct;
import it.progess.invoicecreator.properties.GECOParameter;


public class ListProduct implements Ivo,Comparable<ListProduct> {
	private int idListProduct;
	private List list;
	private Product product;
	private float price;
	private String startdate;
	private boolean active;
	private float percentage;
	private float endprice;
	public float getEndprice() {
		return endprice;
	}
	public void setEndprice(float endprice) {
		this.endprice = endprice;
	}
	public float getPercentage() {
		return percentage;
	}
	public void setPercentage(float percentage) {
		this.percentage = percentage;
	}
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
		this.percentage = ltp.getPercentage();
		
		this.startdate = DataUtilConverter.convertStringFromDate(ltp.getStartdate());
		if (ltp.getProduct()!=null){
			this.product.convertFromTable(ltp.getProduct());
			setEndPrice( (float)this.product.getTaxrate().getValue());
		}
		if (ltp.getList() != null){
			this.list = new List();
			this.list.convertFromTable(ltp.getList());
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
		this.percentage = ltp.getPercentage();
		this.startdate = DataUtilConverter.convertStringFromDate(ltp.getStartdate());
		if (ltp.getProduct()!=null){
			this.product.convertFromTable(ltp.getProduct());
		}
	}
	public void setEndPrice(float taxrate){
		
		if (taxrate > 0){
			float diff  =this.price/100 *taxrate;
			BigDecimal bd = new BigDecimal(diff);
	        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
	        diff= bd.floatValue();
	        this.endprice = diff+this.price;
		}else{
			this.endprice = this.price;
		}
	}
	public void calculatePrices(float taxrate){
		float increment = HibernateUtils.calculatePercentage(this.getProduct().getPurchaseprice(), this.getPercentage());
		this.price = this.getProduct().getPurchaseprice()+increment;
		setEndPrice(taxrate);
	}
	
	public GECOObject control(){
		return null;
	}
	@Override
	public int compareTo(ListProduct p){
		return this.getProduct().getCode().compareTo(p.getProduct().getCode());
	}
}
