package it.progess.invoicecreator.vo.helpobject;

import it.progess.invoicecreator.hibernate.HibernateUtils;
import it.progess.invoicecreator.vo.ListProduct;
import it.progess.invoicecreator.vo.Product;

import java.math.BigDecimal;

public class ProductListPricesCalculation {
	private float price;
	private float inc;
	private boolean isPercentage;
	private boolean isEndPrice;
	private float endprice;
	private float taxrate;
	private float originalPercentage;
	
	public float getOriginalPercentage() {
		return originalPercentage;
	}
	public void setOriginalPercentage(float originalPercentage) {
		this.originalPercentage = originalPercentage;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public float getEndprice() {
		return endprice;
	}
	public void setEndprice(float endprice) {
		this.endprice = endprice;
	}
	public boolean isEndPrice() {
		return isEndPrice;
	}
	public void setEndPrice(boolean isEndPrice) {
		this.isEndPrice = isEndPrice;
	}
	public float getTaxrate() {
		return taxrate;
	}
	public void setTaxrate(float taxrate) {
		this.taxrate = taxrate;
	}
	public float getInc() {
		return inc;
	}
	public void setInc(float inc) {
		this.inc = inc;
	}
	public boolean isPercentage() {
		return isPercentage;
	}
	public void setPercentage(boolean isPercentage) {
		this.isPercentage = isPercentage;
	}
	public void calculatePercentage(){
		if (this.inc > 0){
			if (this.isEndPrice == false){
				float increment = HibernateUtils.calculatePercentage(this.price, this.inc);
				increment = HibernateUtils.roundfloat(increment);
				this.price = this.price + increment;
				calculateEndPrice();
			}else{
				float increment = HibernateUtils.calculatePercentage(this.endprice, this.inc);
				increment = HibernateUtils.roundfloat(increment);
				this.endprice = this.endprice + increment;
				this.price = HibernateUtils.calculateFromPercentage(this.endprice, this.taxrate);
			}
			
		}
	}
	public void calculateFixed(){
		if (this.inc > 0){
			if (this.isEndPrice == false){
				this.price = this.price + this.inc;
				calculateEndPrice();
			}else{
				this.endprice = this.endprice + this.inc;
				this.price = HibernateUtils.calculateFromPercentage(this.endprice, this.taxrate);
			}
			
		}
	}
	private void calculateEndPrice(){
		float increment = this.price/100 * this.taxrate;
		BigDecimal bd = new BigDecimal(increment);
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        increment = bd.floatValue();
        this.endprice = this.price + increment;
	}
	
	
	private void copyToListProd(ListProduct prod){
		prod.setEndprice(this.endprice);
		prod.setPrice(this.price);
		prod.setPercentage(HibernateUtils.calculatePercentageFromPrices(prod.getProduct().getPurchaseprice(), this.price));
	}
	private void copyFromListProd(ListProduct prod){
		this.price = prod.getPrice();
		this.taxrate = (float)prod.getProduct().getTaxrate().getValue();
		this.endprice = prod.getEndprice();
		this.originalPercentage = prod.getPercentage();
	}
	public void calculateIncrement(boolean isEndPrice,boolean isPercentage,float increment ,ListProduct listProduct ){
		this.isEndPrice = isEndPrice;
		this.isPercentage = isPercentage;
		this.inc = increment;
		copyFromListProd(listProduct);
		if (this.isPercentage == true){
			calculatePercentage();
		}else{
			calculateFixed();
		}
		copyToListProd(listProduct);
	}
}
