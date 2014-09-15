package it.progess.invoicecreator.pojo;

import it.progess.invoicecreator.hibernate.DataUtilConverter;

import it.progess.invoicecreator.vo.Ivo;
import it.progess.invoicecreator.vo.Paid;
import it.progess.invoicecreator.vo.PaidSuspended;


import java.util.Date;
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
@Entity
@Table(name="tblpaid")
public class TblPaid implements Itbl{
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idPaid")
	private int idPaid;
	@Column(name="date")
	private Date date;
	@Column(name="description")
	private String description;
	@Column(name="note")
	private String note;
	@Column(name="customer")
	private boolean customer;
	@Column(name="supplier")
	private boolean supplier;
	@Column(name="amount")
	private double amount;
	@ManyToOne
	@JoinColumn(name = "idCustomer")
	private TblCustomer customer_paid;
	@ManyToOne
	@JoinColumn(name = "idSupplier")
	private TblSupplier supplier_paid;
	public TblSupplier getSupplier_paid() {
		return supplier_paid;
	}
	public void setSupplier_paid(TblSupplier supplier_paid) {
		this.supplier_paid = supplier_paid;
	}
	@OneToMany(fetch= FetchType.LAZY,mappedBy = "paid",cascade = CascadeType.ALL)
	private Set<TblPaidSuspended> paids;
	public Set<TblPaidSuspended> getPaids() {
		return paids;
	}
	public void setPaids(Set<TblPaidSuspended> paids) {
		this.paids = paids;
	}
	public TblCustomer getCustomer_paid() {
		return customer_paid;
	}
	public void setCustomer_paid(TblCustomer customer_paid) {
		this.customer_paid = customer_paid;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public int getIdPaid() {
		return idPaid;
	}
	public void setIdPaid(int idPaid) {
		this.idPaid = idPaid;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
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
	public void convertToTable(Ivo obj){
		Paid p = (Paid)obj;
		this.customer = p.isCustomer();
		this.date = DataUtilConverter.convertDateFromString(p.getDate());
		this.description = p.getDescription();
		this.idPaid = p.getIdPaid();
		this.note = p.getNote();
		this.supplier = p.isSupplier();
		this.amount = p.getAmount();
		if (p.getCustomer_paid() != null){
			this.customer_paid = new TblCustomer();
			this.customer_paid.convertToTable(p.getCustomer_paid());
		}
		if (p.getSupplier_paid() != null){
			this.supplier_paid = new TblSupplier();
			this.supplier_paid.convertToTable(p.getSupplier_paid());
		}
		if (p.getPaids() != null){
			this.paids = new HashSet<TblPaidSuspended>();
			for (Iterator<PaidSuspended> iterator = p.getPaids().iterator(); iterator.hasNext();){
				PaidSuspended paidsusp = iterator.next();
				TblPaidSuspended pstbl = new TblPaidSuspended();
				pstbl.convertToTable(paidsusp);
				pstbl.setPaid(this);
				this.paids.add(pstbl);
			}
		}
	}
}
