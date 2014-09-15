package it.progess.invoicecreator.vo;

public class ProductDatePrice {
	private String date;
	private double price;
	public ProductDatePrice(){
		
	}
	public ProductDatePrice(String dateVal,double priceVal){
		this.date = dateVal;
		this.price = priceVal;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
}
