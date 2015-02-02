package it.progess.invoicecreator.vo;

import it.progess.invoicecreator.hibernate.DataUtilConverter;
import it.progess.invoicecreator.pojo.Itbl;
import it.progess.invoicecreator.pojo.TblDeadline;

public class Deadline implements Ivo,Comparable<Deadline> {
	private int idDeadline;
	private double amount;
	private String expiredDate;
	private boolean paid;
	private Suspended suspended;
	private double amountpaid;
	public double getAmountpaid() {
		return amountpaid;
	}
	public void setAmountpaid(double amountpaid) {
		this.amountpaid = amountpaid;
	}
	public int getIdDeadline() {
		return idDeadline;
	}
	public void setIdDeadline(int idDeadline) {
		this.idDeadline = idDeadline;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getExpiredDate() {
		return expiredDate;
	}
	public void setExpiredDate(String expiredDate) {
		this.expiredDate = expiredDate;
	}
	public boolean isPaid() {
		return paid;
	}
	public void setPaid(boolean paid) {
		this.paid = paid;
	}
	public Suspended getSuspended() {
		return suspended;
	}
	public void setSuspended(Suspended suspended) {
		this.suspended = suspended;
	}
	public void convertFromTable(Itbl obj){
		TblDeadline d = (TblDeadline)obj;
		this.amount = d.getAmount();
		this.expiredDate = DataUtilConverter.convertStringFromDate(d.getExpiredDate());
		this.idDeadline = d.getIdDeadline();
		this.paid = d.isPaid();
		this.amountpaid = d.getAmountpaid();
	}
	public GECOObject control(){
		return null;
	}
	@Override
	public int compareTo(Deadline p){
		if (DataUtilConverter.convertDateFromString(p.getExpiredDate()).before(DataUtilConverter.convertDateFromString(expiredDate)))
            return 1;
		else
			return -1;
		
	}
}
