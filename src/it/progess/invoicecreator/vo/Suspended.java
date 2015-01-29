package it.progess.invoicecreator.vo;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import it.progess.invoicecreator.hibernate.DataUtilConverter;
import it.progess.invoicecreator.hibernate.HibernateUtils;
import it.progess.invoicecreator.pojo.Itbl;
import it.progess.invoicecreator.pojo.TblDeadline;
import it.progess.invoicecreator.pojo.TblSuspended;

import it.progess.invoicecreator.pojo.TblHead;
import it.progess.invoicecreator.properties.GECOParameter;

public class Suspended implements Ivo,Comparable<Suspended> {
	private int idSuspended;
	private boolean paid;
	private boolean customer;
	private boolean supplier;
	private double amount;
	private double openamount;
	private Head head;
	private Set<Deadline> deadlines;
	public Set<Deadline> getDeadlines() {
		return deadlines;
	}
	public void setDeadlines(Set<Deadline> deadlines) {
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
	public Head getHead() {
		return head;
	}
	public void setHead(Head head) {
		this.head = head;
	}
	public double getOpenamount() {
		return openamount;
	}
	public void setOpenamount(double openamount) {
		this.openamount = openamount;
	}
	public void convertFromTable(Itbl obj){
		TblSuspended s = (TblSuspended)obj;
		this.amount = s.getAmount();
		this.customer = s.isCustomer();
		this.idSuspended = s.getIdSuspended();
		this.paid = s.isPaid();
		this.supplier = s.isSupplier();
		
		if (s.getHead() != null){
			this.head = new Head();
			this.head.convertFromTable(s.getHead());
		}
		this.openamount = HibernateUtils.rounddouble(head.getTotal() - amount);
		if (s.getDeadlines() != null){
			this.deadlines = new TreeSet<Deadline>();
			for (Iterator<TblDeadline> it = s.getDeadlines().iterator();it.hasNext();){
				TblDeadline d = it.next();
				Deadline dt = new Deadline();
				dt.convertFromTable(d);
				this.deadlines.add(dt);
			}
		}
	}
	@Override
	public int compareTo(Suspended s){
		if (DataUtilConverter.convertDateFromString(s.getHead().getDate()).before(DataUtilConverter.convertDateFromString(head.getDate())))
            return 1;
		else
			return -1;
	}
	public GECOObject control(){
		
		return null;
	}
}
