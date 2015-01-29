package it.progess.invoicecreator.vo;

import it.progess.invoicecreator.hibernate.DataUtilConverter;
import it.progess.invoicecreator.hibernate.HibernateUtils;

public class AccountingElement implements Comparable<AccountingElement> {
	private String date;
	private boolean customer;
	private boolean supplier;
	private boolean credit;
	private boolean debit;
	private double amount;
	private String name;
	private String description;
	private String note;
	private double total;
	private double openamount;
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public double getOpenamount() {
		return openamount;
	}
	public void setOpenamount(double openamount) {
		this.openamount = openamount;
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
	public AccountingElement(){
		
	}
	public AccountingElement(Suspended susp){
		this.setFromSuspended(susp);
	}
	public AccountingElement(Paid p){
		this.setFromPaid(p);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
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
	public boolean isCredit() {
		return credit;
	}
	public void setCredit(boolean credit) {
		this.credit = credit;
	}
	public boolean isDebit() {
		return debit;
	}
	public void setDebit(boolean debit) {
		this.debit = debit;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public void setFromSuspended(Suspended susp){
		this.customer = susp.isCustomer();
		this.supplier = susp.isSupplier();
		this.amount = susp.getAmount();
		this.credit = susp.getHead().getDocument().isCredit();
		this.debit = susp.getHead().getDocument().isDebit();
		this.date = susp.getHead().getDate();
		this.description = susp.getHead().getDocument().getDescription()+" "+susp.getHead().getNumber();
		this.note = susp.getHead().getNote();
		this.amount = HibernateUtils.rounddouble(susp.getAmount());
		this.total = HibernateUtils.rounddouble(susp.getHead().getTotal());
		this.openamount = HibernateUtils.rounddouble(total-amount);
		
		if (susp.isCustomer() == true){
			this.name = susp.getHead().getCustomer().getCustomername();
		}else{
			this.name = susp.getHead().getSupplier().getSuppliername();
		}
	}
	public void setFromPaid(Paid paid){
		this.customer = paid.isCustomer();
		this.supplier = paid.isSupplier();
		this.amount = paid.getAmount();
		this.credit = paid.isCustomer();;
		this.debit = paid.isSupplier();
		this.date = paid.getDate();
		this.description = paid.getDescription();
		this.note = paid.getNote();
		if (paid.getCustomer_paid() != null){
			this.name = paid.getCustomer_paid().getCustomername();
		}else{
			if (paid.getSupplier_paid() != null){
				this.name = paid.getSupplier_paid().getSuppliername();
			}
			
		}
	}
	@Override
	public int compareTo(AccountingElement s){
		if (DataUtilConverter.convertDateFromString(s.getDate()).before(DataUtilConverter.convertDateFromString(this.date)))
            return 1;
		else
			return -1;
	}
}
