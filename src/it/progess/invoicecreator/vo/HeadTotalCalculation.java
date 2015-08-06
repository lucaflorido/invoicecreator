package it.progess.invoicecreator.vo;

import it.progess.invoicecreator.hibernate.HibernateUtils;

import java.util.Iterator;
import java.util.Set;

public class HeadTotalCalculation {
	private float amount4;
	private float taxamount4;
	private float total4;
	private float amount10;
	private float taxamount10;
	private float total10;
	private float amount20;
	private float taxamount20;
	private float total20;
	private float amount;
	private float taxamount;
	private float total;
	private float withholdingtax;
	private float total0;
	private float amount0;
	public float getAmount0() {
		return amount0;
	}
	public void setAmount0(float amount0) {
		this.amount0 = amount0;
	}
	public float getTotal0() {
		return total0;
	}
	public void setTotal0(float total0) {
		this.total0 = total0;
	}
	public float getWithholdingtax() {
		return withholdingtax;
	}
	public void setWithholdingtax(float withholdingtax) {
		this.withholdingtax = withholdingtax;
	}
	private Set<Row> rows;
	public float getAmount4() {
		return amount4;
	}
	public void setAmount4(float amount4) {
		this.amount4 = amount4;
	}
	public float getTaxamount4() {
		return taxamount4;
	}
	public void setTaxamount4(float taxamount4) {
		this.taxamount4 = taxamount4;
	}
	public float getTotal4() {
		return total4;
	}
	public void setTotal4(float total4) {
		this.total4 = total4;
	}
	public float getAmount10() {
		return amount10;
	}
	public void setAmount10(float amount10) {
		this.amount10 = amount10;
	}
	public float getTaxamount10() {
		return taxamount10;
	}
	public void setTaxamount10(float taxamount10) {
		this.taxamount10 = taxamount10;
	}
	public float getTotal10() {
		return total10;
	}
	public void setTotal10(float total10) {
		this.total10 = total10;
	}
	public float getAmount20() {
		return amount20;
	}
	public void setAmount20(float amount20) {
		this.amount20 = amount20;
	}
	public float getTaxamount20() {
		return taxamount20;
	}
	public void setTaxamount20(float taxamount20) {
		this.taxamount20 = taxamount20;
	}
	public float getTotal20() {
		return total20;
	}
	public void setTotal20(float total20) {
		this.total20 = total20;
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
	public Set<Row> getRows() {
		return rows;
	}
	public void setRows(Set<Row> rows) {
		this.rows = rows;
	}
	public void calculation(){
		for(Iterator<Row> itr = rows.iterator();itr.hasNext();){
			Row row = itr.next();
			this.setTaxesAmount(row.getTaxrate(), row);
		}
		this.amount = this.amount4 + this.amount10+ this.amount20+this.amount0;
		this.taxamount = this.taxamount4 + this.taxamount10+ this.taxamount20;
		this.total = this.total4 + this.total10+ this.total20+this.total0;
		this.rows = null;
	}
	public void calculation(Head head){
		this.rows = head.getRows();
		for(Iterator<Row> itr = rows.iterator();itr.hasNext();){
			Row row = itr.next();
			this.setTaxesAmount(row.getTaxrate(), row);
		}
		
		this.amount = this.amount4 + this.amount10+ this.amount20+this.amount0;
		this.taxamount = this.taxamount4 + this.taxamount10+ this.taxamount20;
		if (head.getTaxrate() != null){
			float tax = Float.parseFloat(String.valueOf(head.getTaxrate().getValue()));
			this.withholdingtax = this.amount / 100f * tax; 
			head.setWithholdingtax(withholdingtax);
		}else{
			head.setWithholdingtax(0);
		}
		this.total = this.total4 + this.total10+ this.total20+this.total0-head.getWithholdingtax();
		head.setAmount(HibernateUtils.roundfloat(amount));
		head.setTaxamount(HibernateUtils.roundfloat(taxamount));
		head.setTotal(HibernateUtils.roundfloat(total));
		
		head.setAmount4(HibernateUtils.roundfloat(amount4));
		head.setTaxamount4(HibernateUtils.roundfloat(taxamount4));
		head.setTotal4(HibernateUtils.roundfloat(total4));
		
		head.setAmount10(HibernateUtils.roundfloat(amount10));
		head.setTaxamount10(HibernateUtils.roundfloat(taxamount10));
		head.setTotal10(HibernateUtils.roundfloat(total10));
		
		head.setAmount20(HibernateUtils.roundfloat(amount20));
		head.setTaxamount20(HibernateUtils.roundfloat(taxamount20));
		head.setTotal20(HibernateUtils.roundfloat(total20));
	}
	private void setTaxesAmount(TaxRate t,Row r){
		if (r.getType().endsWith("V")){
			if (r.getTaxrate().getValue() < 10 && r.getTaxrate().getValue() > 0 ){
				this.taxamount4 = this.taxamount4 + r.getTaxamount();
				this.amount4 = this.amount4 + r.getAmount();
				this.total4 = this.total4 + r.getTotal();
			}
			if (r.getTaxrate().getValue() >= 10 && r.getTaxrate().getValue() < 20){
				this.taxamount10 = this.taxamount10 + r.getTaxamount();
				this.amount10 = this.amount10 + r.getAmount();
				this.total10 = this.total10 + r.getTotal();
			}
			if (r.getTaxrate().getValue() >= 20){
				this.taxamount20 = this.taxamount20 + r.getTaxamount();
				this.amount20 = this.amount20 + r.getAmount();
				this.total20 = this.total20 + r.getTotal();
			}
			if (r.getTaxrate().getValue() == 0){
				this.amount0 = this.amount0+r.getTotal();
				this.total0 = this.total0+r.getTotal();
			}
		}
	}
}
