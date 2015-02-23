package it.progess.invoicecreator.vo.helpobject;

import it.progess.invoicecreator.hibernate.DataUtilConverter;
import it.progess.invoicecreator.hibernate.HibernateUtils;
import it.progess.invoicecreator.vo.Product;

import java.math.BigDecimal;

public class ProductBasicPricesCalculation {
	private float purchaseprice;
	private float percentage;
	private float sellprice;
	private float endprice;
	private float taxrate;
	
	public float getEndprice() {
		return endprice;
	}
	public void setEndprice(float endprice) {
		this.endprice = endprice;
	}
	public float getTaxrate() {
		return taxrate;
	}
	public void setTaxrate(float taxrate) {
		this.taxrate = taxrate;
	}
	public float getPurchaseprice() {
		return purchaseprice;
	}
	public void setPurchaseprice(float purchaseprice) {
		this.purchaseprice = purchaseprice;
	}
	public float getPercentage() {
		return percentage;
	}
	public void setPercentage(float percentage) {
		this.percentage = percentage;
	}
	public float getSellprice() {
		return sellprice;
	}
	public void setSellprice(float sellprice) {
		this.sellprice = sellprice;
	}
	public void calculatePercentage(){
		float inc = this.sellprice - this.purchaseprice;
		if (inc > 0){
			this.percentage =inc*100/this.purchaseprice;
			BigDecimal bd = new BigDecimal(this.percentage);
	        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
	        this.percentage= bd.floatValue();
		}else{
			this.percentage = 0;
		}
		float taxdiff = HibernateUtils.calculatePercentage(sellprice, this.taxrate);
        this.endprice = this.sellprice+taxdiff;
	}
	public void calculateSellPrice(){
		this.sellprice =(this.purchaseprice/100*this.percentage)+this.purchaseprice;
		BigDecimal bd = new BigDecimal(this.sellprice);
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        this.sellprice= bd.floatValue();
        float taxdiff = HibernateUtils.calculatePercentage(sellprice, this.taxrate);
        this.endprice = this.sellprice+taxdiff;
	}
	public void calculateFromTotalPrice(){
		this.sellprice =HibernateUtils.calculateFromPercentage(this.endprice, this.taxrate);
		BigDecimal bd = new BigDecimal(this.sellprice);
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        this.sellprice= bd.floatValue();
        float inc = this.sellprice - this.purchaseprice;
		if (inc > 0){
			this.percentage =inc*100/this.purchaseprice;
			BigDecimal bdr = new BigDecimal(this.percentage);
	        bdr = bdr.setScale(2, BigDecimal.ROUND_HALF_UP);
	        this.percentage= bdr.floatValue();
		}else{
			this.percentage = 0;
		}
        
	}
	public void percentageIncrement(float inc,Product prod){
		copyFromProd(prod);
		calculatePercentage();
		this.purchaseprice =(this.purchaseprice/100*inc)+this.purchaseprice;
		calculateSellPrice();
		copyToProd(prod);
	}
	public void amountIncrement(float inc,Product prod){
		copyFromProd(prod);
		this.sellprice = this.sellprice + inc;
		this.purchaseprice = this.purchaseprice + inc;
		copyToProd(prod);
	}
	private void copyToProd(Product prod){
		prod.setSellprice(this.sellprice);
		prod.setPurchaseprice(this.purchaseprice);
	}
	private void copyFromProd(Product prod){
		this.sellprice = prod.getSellprice();
		this.purchaseprice = prod.getPurchaseprice();
	}
}
