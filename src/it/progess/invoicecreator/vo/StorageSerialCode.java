package it.progess.invoicecreator.vo;



import it.progess.invoicecreator.hibernate.DataUtilConverter;
import it.progess.invoicecreator.pojo.Itbl;

import it.progess.invoicecreator.pojo.TblStorageSerialCode;
import it.progess.invoicecreator.properties.GECOParameter;


public class StorageSerialCode implements Ivo {
	private int idStorageSerialCode;
	private Storage storage;
	private String serialcode;
	private String expiredate;
	private double load;
	private double unload;
	private double reserved;
	private double stock;
	public int getIdStorageSerialCode() {
		return idStorageSerialCode;
	}
	public void setIdStorageSerialCode(int idStorageSerialCode) {
		this.idStorageSerialCode = idStorageSerialCode;
	}
	public Storage getStorage() {
		return storage;
	}
	public void setStorage(Storage storage) {
		this.storage = storage;
	}
	public String getSerialcode() {
		return serialcode;
	}
	public void setSerialcode(String serialcode) {
		this.serialcode = serialcode;
	}
	public String getExpiredate() {
		return expiredate;
	}
	public void setExpiredate(String expiredate) {
		this.expiredate = expiredate;
	}
	public double getLoad() {
		return load;
	}
	public void setLoad(double load) {
		this.load = load;
	}
	public double getUnload() {
		return unload;
	}
	public void setUnload(double unload) {
		this.unload = unload;
	}
	public double getReserved() {
		return reserved;
	}
	public void setReserved(double reserved) {
		this.reserved = reserved;
	}
	public double getStock() {
		return stock;
	}
	public void setStock(double stock) {
		this.stock = stock;
	}
	public void convertFromTable(Itbl obj){
		TblStorageSerialCode ssc = (TblStorageSerialCode)obj;
		this.expiredate = DataUtilConverter.convertStringFromDate(ssc.getExpiredate());
		this.idStorageSerialCode = ssc.getIdStorageSerialCode();
		this.load = ssc.getLoad();
		this.reserved = ssc.getReserved();
		this.serialcode = ssc.getSerialcode();
		this.stock = ssc.getStock();
		this.unload = ssc.getUnload();
		/*if (ssc.getStorage() !=null){
			this.storage = new Storage();
			this.storage.convertFromTable(ssc.getStorage());
		}*/
	}
	public void loadSerial(Storage st,double load,String serialcode,String expireDate){
		this.load = this.load + load;
		this.stock = this.stock + load;
		this.expiredate = expireDate;
		this.serialcode = serialcode;
	}
	public void removeloadSerial(Storage st,double load,String serialcode,String expireDate){
		this.storage = st;
		this.load = this.load - load;
		this.stock = this.stock - load;
		this.expiredate = expireDate;
		this.serialcode = serialcode;
	}
	public void unloadSerial(Storage st,double unload,String serialcode,String expireDate){
		this.unload = this.unload + unload;
		this.stock = this.stock - unload;
		this.expiredate = expireDate;
		this.serialcode = serialcode;
	}
	public void removeunloadSerial(Storage st,double unload,String serialcode,String expireDate){
		this.storage = st;
		this.unload = this.unload - unload;
		this.stock = this.stock + unload;
		this.expiredate = expireDate;
		this.serialcode = serialcode;
	}
	public GECOObject control(){
		return null;
	}
}
