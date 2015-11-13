package it.progess.invoicecreator.vo;


import it.progess.invoicecreator.hibernate.HibernateUtils;

public class RowTotalCalculator {
	private double qta;
	private float price;
	private float taxrate;
	private float amount;
	private float taxamount;
	private float total;
	public double getQta() {
		return qta;
	}
	public void setQta(double qta) {
		this.qta = qta;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public float getTaxrate() {
		return taxrate;
	}
	public void setTaxrate(float taxrate) {
		this.taxrate = taxrate;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public float getTaxamount() {
		return taxamount;
	}
	public void setTaxamount(float taxamount) {
		this.taxamount = taxamount;
	}
	public float getTotal() {
		return total;
	}
	public void setTotal(float total) {
		this.total = total;
	}
	public void calculation(){
		this.amount = HibernateUtils.roundfloat( Float.parseFloat(String.valueOf(this.qta)) * this.price);
		this.taxamount =HibernateUtils.roundfloat( this.amount / 100 * this.taxrate);
		this.total = HibernateUtils.roundfloat(this.amount + this.taxamount);
	}
	public void singlecalculation(){
		this.amount = HibernateUtils.roundfloat(  this.price);
		this.taxamount =HibernateUtils.roundfloat( this.amount / 100 * this.taxrate);
		this.total = HibernateUtils.roundfloat(this.amount + this.taxamount);
	}
	public void rowCalculation(Row row){
		if (row.getType().equals("V")){
			this.qta = row.getQuantity();
			this.price = row.getPrice();
			this.taxrate = Float.parseFloat(String.valueOf(row.getTaxrate().getValue()));
			this.calculation();
			row.setTotal(total);
			row.setAmount(amount);
			row.setTaxamount(taxamount);
		}else{
			row.setTotal(0);
			row.setAmount(0);
			row.setTaxamount(0);
		}
	}
	public void addRowCalculation(Row row,UnitMeasureProduct ump){
		Product p = ump.getProduct();
		row.setProduct(p);
		row.setProductcode(ump.getCode());
		row.setProductdescription(p.getDescription());
		row.setUm(ump.getUm());
		row.setProductum(ump.getUm().getCode());
		row.setQuantity(ump.getQuantity());
		row.setPrice(p.getListprice());
		row.setType("V");
		row.setTaxrate(p.getTaxrate());
		if (row.getType().equals("V")){
			this.qta = row.getQuantity();
			this.price = row.getPrice();
			this.taxrate = Float.parseFloat(String.valueOf(row.getTaxrate().getValue()));
			this.singlecalculation();
			row.setTotal(HibernateUtils.roundfloat( total * ump.getConversion() * (float)this.qta));
			row.setAmount(HibernateUtils.roundfloat(amount * ump.getConversion() * (float)this.qta));
			row.setTaxamount(HibernateUtils.roundfloat(taxamount * ump.getConversion() * (float)this.qta));
		}else{
			row.setTotal(0);
			row.setAmount(0);
			row.setTaxamount(0);
		}
	}
}
