package it.progess.invoicecreator.vo;

import it.progess.invoicecreator.hibernate.DataUtilConverter;
import it.progess.invoicecreator.hibernate.HibernateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.DateUtil;

public class Draft {
	private String id;
	private ArrayList<DraftElement> products = new ArrayList<DraftElement>();
	private float total;
	private int status;
	private User user;
	private String key;
	private DeliveryCost deliverycost;
	private float delcost;
	private float amount;
	private float commission;
	private float commissionamount;
	private String deliverydate;
	public String getDeliverydate() {
		return deliverydate;
	}
	public void setDeliverydate(String deliverydate) {
		this.deliverydate = deliverydate;
	}
	public float getCommissionamount() {
		return commissionamount;
	}
	public void setCommissionamount(float commissionamount) {
		this.commissionamount = commissionamount;
	}
	public float getCommission() {
		return commission;
	}
	public void setCommission(float commission) {
		this.commission = commission;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public float getDelcost() {
		return delcost;
	}
	public void setDelcost(float delcost) {
		this.delcost = delcost;
	}
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
	
	public DeliveryCost getDeliverycost() {
		return deliverycost;
	}
	public void setDeliverycost(DeliveryCost deliverycost) {
		this.deliverycost = deliverycost;
	}
	public void calculateTotal(){
		this.total = 0;
		this.amount = 0;
		this.deliverydate = null;
		for(Iterator<DraftElement> it = this.products.iterator();it.hasNext();){
			DraftElement de = it.next();
			de.calculateEndPrice(de.getProduct());
			this.amount =HibernateUtils.roundfloat(  this.amount +de.getEndprice());
			if (this.deliverydate == null || this.deliverydate.equals("")){
				this.deliverydate = de.getDeliverydate();
			}else{
				Date draftdate = DataUtilConverter.convertDateFromString(this.deliverydate);
				Date dedate = DataUtilConverter.convertDateFromString(de.getDeliverydate());
				if (dedate.getTime() > draftdate.getTime()){
					this.deliverydate = DataUtilConverter.convertStringFromDate(dedate);
				}
			}
		}
		if (this.deliverycost != null){
			if (this.amount >= this.deliverycost.getBound()){
				this.delcost = 0;
			}else{
				this.delcost = this.deliverycost.getPrice();
			}
		}
		if (this.commission > 0){
			this.commissionamount = HibernateUtils.calculatePercentage(this.amount, this.commission);
		}
		this.total = HibernateUtils.roundfloat(this.amount + this.delcost+this.commissionamount);
	}
}
