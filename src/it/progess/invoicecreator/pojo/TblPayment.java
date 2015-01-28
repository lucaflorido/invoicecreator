package it.progess.invoicecreator.pojo;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.OrderBy;


import it.progess.invoicecreator.vo.Ivo;
import it.progess.invoicecreator.vo.Payment;
import it.progess.invoicecreator.vo.PaymentDeadline;

@Entity
@Table(name="tblpayment")
public class TblPayment implements Itbl {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idPayment")
	private int idPayment;
	@Column(name="code")
	private String code;
	@Column(name="description")
	private String description;
	@Column(name="endofmonth")
	private boolean endofmonth;
	@OneToMany(fetch= FetchType.LAZY,mappedBy = "payment",cascade = CascadeType.ALL)
	@OrderBy(clause = "days asc")
	private Set<TblPaymentDeadline> deadlines;
	public Set<TblPaymentDeadline> getDeadlines() {
		return deadlines;
	}
	public void setDeadlines(Set<TblPaymentDeadline> deadlines) {
		this.deadlines = deadlines;
	}
	public boolean getEndofmonth() {
		return endofmonth;
	}
	public void setEndofmonth(boolean endofmonth) {
		this.endofmonth = endofmonth;
	}
	public int getIdPayment() {
		return idPayment;
	}
	public void setIdPayment(int idPayment) {
		this.idPayment = idPayment;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void convertToTable(Ivo obj){
		Payment py = (Payment)obj;
		this.code = py.getCode();
		this.description = py.getDescription();
		this.endofmonth = py.getEndofmonth();
		this.idPayment = py.getIdPayment();
		if (py.getDeadlines() != null){
			this.deadlines = new TreeSet<TblPaymentDeadline>();
			for (Iterator<PaymentDeadline> iterator = py.getDeadlines().iterator(); iterator.hasNext();){
				PaymentDeadline paymentdeadline = iterator.next();
				TblPaymentDeadline payd = new TblPaymentDeadline();
				payd.convertToTable(paymentdeadline);
				payd.setPayment(this);
				this.deadlines.add(payd);
			}
		}
	}
	public void convertToTableForSaving(Ivo obj){
		Payment py = (Payment)obj;
		this.code = py.getCode();
		this.description = py.getDescription();
		this.endofmonth = py.getEndofmonth();
		this.idPayment = py.getIdPayment();
		if (py.getDeadlines() != null){
			this.deadlines = new HashSet<TblPaymentDeadline>();
			for (Iterator<PaymentDeadline> iterator = py.getDeadlines().iterator(); iterator.hasNext();){
				PaymentDeadline cy = iterator.next();
				TblPaymentDeadline tcy = new TblPaymentDeadline();
				tcy.convertToTableForSaving(cy,this);
				this.deadlines.add(tcy);
			}
		}
	}
	
}
