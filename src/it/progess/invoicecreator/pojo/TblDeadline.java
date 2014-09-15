package it.progess.invoicecreator.pojo;

import it.progess.invoicecreator.hibernate.DataUtilConverter;
import it.progess.invoicecreator.vo.Deadline;
import it.progess.invoicecreator.vo.Ivo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table(name="tbldeadline")
public class TblDeadline implements Itbl{
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idDeadline")
	private int idDeadline;
	@Column(name="amount")
	private double amount;
	@Column(name="expiredDate")
	private Date expiredDate;
	@Column(name="paid")
	private boolean paid;
	@ManyToOne
	@JoinColumn(name = "idSuspended")
	private TblSuspended suspended;
	@Column(name="amountpaid")
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
	public Date getExpiredDate() {
		return expiredDate;
	}
	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}
	public boolean isPaid() {
		return paid;
	}
	public void setPaid(boolean paid) {
		this.paid = paid;
	}
	public TblSuspended getSuspended() {
		return suspended;
	}
	public void setSuspended(TblSuspended suspended) {
		this.suspended = suspended;
	}
	public void convertToTable(Ivo obj){
		Deadline d = (Deadline)obj;
		this.amount = d.getAmount();
		this.expiredDate = DataUtilConverter.convertDateFromString(d.getExpiredDate());
		this.idDeadline = d.getIdDeadline();
		this.paid = d.isPaid();
		this.amountpaid = d.getAmountpaid();
		
	}
}
