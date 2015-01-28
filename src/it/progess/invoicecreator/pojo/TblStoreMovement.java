package it.progess.invoicecreator.pojo;

import it.progess.invoicecreator.vo.Ivo;
import it.progess.invoicecreator.vo.StoreMovement;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tblstoremovement")
public class TblStoreMovement implements Itbl{
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idStoreMovement")
	private int idstoremovement;
	@Column(name="name")
	private String name;
	@Column(name="genericload")
	private boolean genericLoad;
	@Column(name="supplierload")
	private boolean supplierLoad;
	@Column(name="comebackload")
	private boolean comebackLoad;
	@Column(name="internalload")
	private boolean internalLoad;
	@Column(name="expiredunload")
	private boolean expiredUnload;
	@Column(name="customerunload")
	private boolean customerUnload;
	@Column(name="genericunload")
	private boolean genericUnload;
	@Column(name="internalunload")
	private boolean internalUnload;
	@Column(name="genericreserved")
	private boolean genericreserved;
	public boolean getGenericreserved() {
		return genericreserved;
	}
	public void setGenericreserved(boolean genericreserved) {
		this.genericreserved = genericreserved;
	}
	public int getIdstoremovement() {
		return idstoremovement;
	}
	public void setIdstoremovement(int idstoremovement) {
		this.idstoremovement = idstoremovement;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean getGenericLoad() {
		return genericLoad;
	}
	public void setGenericLoad(boolean genericLoad) {
		this.genericLoad = genericLoad;
	}
	public boolean getSupplierLoad() {
		return supplierLoad;
	}
	public void setSupplierLoad(boolean supplierLoad) {
		this.supplierLoad = supplierLoad;
	}
	public boolean getComebackLoad() {
		return comebackLoad;
	}
	public void setComebackLoad(boolean comebackLoad) {
		this.comebackLoad = comebackLoad;
	}
	public boolean getInternalLoad() {
		return internalLoad;
	}
	public void setInternalLoad(boolean internalLoad) {
		this.internalLoad = internalLoad;
	}
	public boolean getExpiredUnload() {
		return expiredUnload;
	}
	public void setExpiredUnload(boolean expiredUnload) {
		this.expiredUnload = expiredUnload;
	}
	public boolean getCustomerUnload() {
		return customerUnload;
	}
	public void setCustomerUnload(boolean customerUnload) {
		this.customerUnload = customerUnload;
	}
	public boolean getGenericUnload() {
		return genericUnload;
	}
	public void setGenericUnload(boolean genericUnload) {
		this.genericUnload = genericUnload;
	}
	public boolean getInternalUnload() {
		return internalUnload;
	}
	public void setInternalUnload(boolean internalUnload) {
		this.internalUnload = internalUnload;
	}
	public void convertToTable(Ivo vo){
		StoreMovement sm = (StoreMovement)vo;
		this.name = sm.getName();
		this.comebackLoad = sm.getComebackLoad();
		this.customerUnload = sm.getCustomerUnload();
		this.expiredUnload = sm.getExpiredUnload();
		this.genericLoad = sm.getGenericLoad();
		this.genericUnload = sm.getGenericUnload();
		this.idstoremovement = sm.getIdstoremovement();
		this.internalLoad = sm.getInternalLoad();
		this.internalUnload = sm.getInternalUnload();
		this.supplierLoad = sm.getSupplierLoad();
		this.genericreserved = sm.getGenericreserved();
	}
}
