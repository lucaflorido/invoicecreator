package it.progess.invoicecreator.vo;

import it.progess.invoicecreator.hibernate.DataUtilConverter;
import it.progess.invoicecreator.hibernate.HibernateUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.poi.ss.usermodel.DateUtil;

public class DraftElement {
	private Product product;
	private float price;
	private TaxRate taxrate;
	private float endprice;
	private int quantity;
	private ArrayList<Integer> range;
	private String deliverydate;
	private boolean directdelivery;
	public String getDeliverydate() {
		return deliverydate;
	}
	public void setDeliverydate(String deliverydate) {
		this.deliverydate = deliverydate;
	}
	public boolean isDirectdelivery() {
		return directdelivery;
	}
	public void setDirectdelivery(boolean directdelivery) {
		this.directdelivery = directdelivery;
	}
	public ArrayList<Integer> getRange() {
		return range;
	}
	public void setRange(ArrayList<Integer> range) {
		this.range = range;
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
	public TaxRate getTaxrate() {
		return taxrate;
	}
	public void setTaxrate(TaxRate taxrate) {
		this.taxrate = taxrate;
	}
	public float getEndprice() {
		return endprice;
	}
	public void setEndprice(float endprice) {
		this.endprice = endprice;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public void calculateEndPrice(Product product){
		this.product = product;
		this.range = new ArrayList<Integer>();
		if (this.product.getEcconfig() != null ){
			if (this.product.getEcconfig().getQuantitymin() > 0 && this.product.getEcconfig().getQuantitymax() > 0){
				for (int i = this.product.getEcconfig().getQuantitymin();i <= this.product.getEcconfig().getQuantitymax();i++){
					range.add(i);
				}
			}else{
				for (int i = 1;i <= 100;i++){
					range.add(i);
				}
			}
		}
		if (this.quantity <= 0)
				this.quantity = 1;
		this.price = product.getListprice();
		this.setTaxrate(product.getTaxrate());
		float increment = this.price/100 * (float)this.taxrate.getValue();
		BigDecimal bd = new BigDecimal(increment);
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        increment = bd.floatValue();
        Date delivery = new Date();
        GregorianCalendar c = new GregorianCalendar();
    	c.setTime(delivery);
        if (product.getEcconfig().getQuantitymax() > 0){
        	c.add(GregorianCalendar.DATE, product.getEcconfig().getDeliverydays());
        	delivery = c.getTime();
        	this.directdelivery = true;
        }else{
        	c.add(GregorianCalendar.DATE, product.getEcconfig().getOrderdays()+product.getEcconfig().getDeliverydays());
        	delivery = c.getTime();
        	this.directdelivery = false;
        }
        this.deliverydate =  DataUtilConverter.convertStringFromDate(delivery);
        this.endprice = HibernateUtils.roundfloat( (this.price + increment)*quantity);
	}
}
