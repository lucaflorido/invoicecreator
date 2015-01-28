package it.progess.invoicecreator.pojo;

import it.progess.invoicecreator.hibernate.DataUtilConverter;
import it.progess.invoicecreator.vo.Ivo;
import it.progess.invoicecreator.vo.StorageSerialCode;

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
@Table(name="tblstorageserialcode")
public class TblStorageSerialCode implements Itbl {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idStorageSerialCode")
	private int idStorageSerialCode;
	@ManyToOne
	@JoinColumn(name = "idStorage")
	private TblStorage storage;
	@Column(name="serialcode")
	private String serialcode;
	@Column(name="expiredate")
	private Date expiredate;
	@Column(name="loaded")
	private double load;
	@Column(name="unloaded")
	private double unload;
	@Column(name="reserved")
	private double reserved;
	@Column(name="stock")
	private double stock;
	public int getIdStorageSerialCode() {
		return idStorageSerialCode;
	}
	public void setIdStorageSerialCode(int idStorageSerialCode) {
		this.idStorageSerialCode = idStorageSerialCode;
	}
	public TblStorage getStorage() {
		return storage;
	}
	public void setStorage(TblStorage storage) {
		this.storage = storage;
	}
	public String getSerialcode() {
		return serialcode;
	}
	public void setSerialcode(String serialcode) {
		this.serialcode = serialcode;
	}
	public Date getExpiredate() {
		return expiredate;
	}
	public void setExpiredate(Date expiredate) {
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
	public void convertToTable(Ivo obj){
		StorageSerialCode ssc = (StorageSerialCode)obj;
		this.expiredate = DataUtilConverter.convertDateFromString(ssc.getExpiredate());
		this.idStorageSerialCode = ssc.getIdStorageSerialCode();
		this.load = ssc.getLoad();
		this.reserved = ssc.getReserved();
		this.serialcode = ssc.getSerialcode();
		this.stock = ssc.getStock();
		this.unload = ssc.getUnload();
		/*if (ssc.getStorage() !=null){
			this.storage = new TblStorage();
			this.storage.convertToTable(ssc.getStorage());
		}*/
	}
}
