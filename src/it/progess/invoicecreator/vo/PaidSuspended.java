package it.progess.invoicecreator.vo;

import it.progess.invoicecreator.pojo.Itbl;
import it.progess.invoicecreator.pojo.TblPaid;
import it.progess.invoicecreator.pojo.TblPaidSuspended;
import it.progess.invoicecreator.pojo.TblSuspended;
import it.progess.invoicecreator.properties.GECOParameter;

public class PaidSuspended implements Ivo {
	private int idPaidSuspended;
	private Paid paid;
	private Suspended suspended;
	private double amount;
	public int getIdPaidSuspended() {
		return idPaidSuspended;
	}
	public void setIdPaidSuspended(int idPaidSuspended) {
		this.idPaidSuspended = idPaidSuspended;
	}
	public Paid getPaid() {
		return paid;
	}
	public void setPaid(Paid paid) {
		this.paid = paid;
	}
	public Suspended getSuspended() {
		return suspended;
	}
	public void setSuspended(Suspended suspended) {
		this.suspended = suspended;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public void convertFromTable(Itbl obj){
		TblPaidSuspended ps = (TblPaidSuspended)obj;
		this.amount = ps.getAmount();
		this.idPaidSuspended = ps.getIdPaidSuspended();
		if (ps.getPaid() != null){
			this.paid = new Paid();
			this.paid.convertFromTable(ps.getPaid());
		}
		if (ps.getSuspended() != null){
			this.suspended = new Suspended();
			this.suspended.convertFromTable(ps.getSuspended());
		}
	}
	public GECOObject control(){
		return null;
	}
}
