package it.progess.invoicecreator.pojo;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import it.progess.invoicecreator.vo.Deadline;
import it.progess.invoicecreator.vo.Ivo;
import it.progess.invoicecreator.vo.Suspended;
@Entity
@Table(name="tblsuspended")
public class TblSuspended implements Itbl{
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idSuspended")
	private int idSuspended;
	@Column(name="paid")
	private boolean paid;
	@Column(name="customer")
	private boolean customer;
	@Column(name="supplier")
	private boolean supplier;
	@Column(name="amount")
	private double amount;
	@ManyToOne
	@JoinColumn(name = "idHead")
	private TblHead head;
	@OneToMany(fetch= FetchType.LAZY,mappedBy = "suspended",cascade = CascadeType.ALL)
	private Set<TblDeadline> deadlines;
	public Set<TblDeadline> getDeadlines() {
		return deadlines;
	}
	public void setDeadlines(Set<TblDeadline> deadlines) {
		this.deadlines = deadlines;
	}
	public int getIdSuspended() {
		return idSuspended;
	}
	public void setIdSuspended(int idSuspended) {
		this.idSuspended = idSuspended;
	}
	public boolean isPaid() {
		return paid;
	}
	public void setPaid(boolean paid) {
		this.paid = paid;
	}
	public boolean isCustomer() {
		return customer;
	}
	public void setCustomer(boolean customer) {
		this.customer = customer;
	}
	public boolean isSupplier() {
		return supplier;
	}
	public void setSupplier(boolean supplier) {
		this.supplier = supplier;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public TblHead getHead() {
		return head;
	}
	public void setHead(TblHead head) {
		this.head = head;
	}
	public void convertToTable(Ivo obj){
		Suspended s = (Suspended)obj;
		this.amount = s.getAmount();
		this.customer = s.isCustomer();
		this.idSuspended = s.getIdSuspended();
		this.paid = s.isPaid();
		this.supplier = s.isSupplier();
		if (s.getHead() != null){
			this.head = new TblHead();
			this.head.convertToTable(s.getHead());
		}
		if (s.getDeadlines() != null){
			this.deadlines = new HashSet<TblDeadline>();
			for (Iterator<Deadline> it = s.getDeadlines().iterator();it.hasNext();){
				Deadline d = it.next();
				TblDeadline dt = new TblDeadline();
				dt.convertToTable(d);
				dt.setSuspended(this);
				this.deadlines.add(dt);
			}
		}
	}
}
