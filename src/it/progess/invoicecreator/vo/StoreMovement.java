package it.progess.invoicecreator.vo;

import it.progess.invoicecreator.pojo.Itbl;
import it.progess.invoicecreator.pojo.TblStoreMovement;
import it.progess.invoicecreator.properties.GECOParameter;

public class StoreMovement implements Ivo {
	private int idstoremovement;
	private String name;
	private boolean genericLoad;
	private boolean supplierLoad;
	private boolean comebackLoad;
	private boolean internalLoad;
	private boolean expiredUnload;
	private boolean customerUnload;
	private boolean genericUnload;
	private boolean internalUnload;
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
	public void convertFromTable(Itbl itbl){
		TblStoreMovement sm = (TblStoreMovement)itbl;
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
	public GECOObject control(){
		if (this.name == null || this.name.equals("") == true){
			return new GECOError(GECOParameter.ERROR_VALUE_MISSING,"Nome Mancante");
		}
		return null;
	}
	public boolean isLoad(){
		if (this.genericLoad == true || this.internalLoad == true || this.supplierLoad == true || this.comebackLoad == true)
			return true;
		else
			return false;
	}
	public boolean isUnload(){
		if (this.genericUnload == true || this.internalUnload == true || this.customerUnload == true || this.expiredUnload == true)
			return true;
		else
			return false;
	}
	public boolean isStoreMovementActive(){
		return  (isLoad() == true || isUnload() == true);
	}
}
