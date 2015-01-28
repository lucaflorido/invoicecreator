package it.progess.invoicecreator.pojo;

import it.progess.invoicecreator.vo.Supplier;

import it.progess.invoicecreator.vo.Ivo;
import it.progess.invoicecreator.vo.ListSupplier;

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
@Table(name="tblsupplier")
public class TblSupplier implements Itbl{
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idSupplier")
	private int idSupplier;
	@Column(name="suppliername")
	private String suppliername;
	@Column(name="suppliercode")
	private String suppliercode;
	@Column(name="active")
	private boolean active;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idContact")
	private TblContact contact;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idAddress")
	private TblAddress address;
	@Column(name="taxcode")
	private String taxcode;
	@Column(name="serialnumber")
	private String serialnumber;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idGroup")
	private TblGroupSupplier group;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idCategory")
	private TblCategorySupplier category;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idBankContact")
	private TblBankContact bankcontact;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idPayment")
	private TblPayment payment;
	@OneToMany(fetch= FetchType.LAZY,mappedBy = "supplier",cascade = CascadeType.ALL)
	private Set<TblListSupplier> lists;
	public TblPayment getPayment() {
		return payment;
	}
	public void setPayment(TblPayment payment) {
		this.payment = payment;
	}
	public int getIdSupplier() {
		return idSupplier;
	}
	public void setIdSupplier(int idSupplier) {
		this.idSupplier = idSupplier;
	}
	public String getSuppliername() {
		return suppliername;
	}
	public void setSuppliername(String suppliername) {
		this.suppliername = suppliername;
	}
	public String getSuppliercode() {
		return suppliercode;
	}
	public void setSuppliercode(String suppliercode) {
		this.suppliercode = suppliercode;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public TblContact getContact() {
		return contact;
	}
	public void setContact(TblContact contact) {
		this.contact = contact;
	}
	public TblAddress getAddress() {
		return address;
	}
	public void setAddress(TblAddress address) {
		this.address = address;
	}
	public String getTaxcode() {
		return taxcode;
	}
	public void setTaxcode(String taxcode) {
		this.taxcode = taxcode;
	}
	public String getSerialnumber() {
		return serialnumber;
	}
	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}
	public TblGroupSupplier getGroup() {
		return group;
	}
	public void setGroup(TblGroupSupplier group) {
		this.group = group;
	}
	public TblCategorySupplier getCategory() {
		return category;
	}
	public void setCategory(TblCategorySupplier category) {
		this.category = category;
	}
	public TblBankContact getBankcontact() {
		return bankcontact;
	}
	public void setBankcontact(TblBankContact bankcontact) {
		this.bankcontact = bankcontact;
	}
	public Set<TblListSupplier> getLists() {
		return lists;
	}
	public void setLists(Set<TblListSupplier> lists) {
		this.lists = lists;
	}
	public void convertToTable(Ivo obj){
		Supplier c = (Supplier)obj;
		this.active = c.isActive();
		if (c.getAddress() != null){
			this.address = new TblAddress();
			this.address.convertToTable(c.getAddress());
		}
		if (c.getBankcontact()!= null){
			this.bankcontact = new TblBankContact();
			this.bankcontact.convertToTable(c.getBankcontact());
		}
		if(c.getCategory() != null){
			this.category = new TblCategorySupplier();
			this.category.convertToTable(c.getCategory());
		}
		if(c.getContact() != null){
			this.contact = new TblContact();
			this.contact.convertToTable(c.getContact());
		}
		if (c.getPayment() != null){
			this.payment = new TblPayment();
			this.payment.convertToTable(c.getPayment());
		}
		this.suppliercode = c.getSuppliercode();
		this.suppliername = c.getSuppliername();
		if (c.getGroup() != null){
			this.group = new TblGroupSupplier();
			this.group.convertToTable(c.getGroup());
		}
		this.idSupplier = c.getIdSupplier();
		this.serialnumber = c.getSerialnumber();
		this.taxcode = c.getTaxcode();
	}
	public void convertToTableSingle(Ivo obj){
		Supplier c = (Supplier)obj;
		this.convertToTable(obj);
		if (c.getLists() != null){
			this.lists = new HashSet<TblListSupplier>();
			for (Iterator<ListSupplier> iterator = c.getLists().iterator(); iterator.hasNext();){
				ListSupplier listproduct = iterator.next();
				TblListSupplier listp = new TblListSupplier();
				listp.convertToTable(listproduct);
				this.lists.add(listp);
			}
		}
		
	}
	public void convertToTableSingle(Ivo obj,Itbl tbl){
		Supplier c = (Supplier)obj;
		this.convertToTable(obj);
		
		if (c.getLists() != null){
			this.lists = new HashSet<TblListSupplier>();
			for (Iterator<ListSupplier> iterator = c.getLists().iterator(); iterator.hasNext();){
				ListSupplier listproduct = iterator.next();
				TblListSupplier listp = new TblListSupplier();
				listp.convertToTableForSaving(listproduct,tbl);
				this.lists.add(listp);
			}
		}
		
	}
}
