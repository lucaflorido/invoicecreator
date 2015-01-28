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

import it.progess.invoicecreator.vo.Ivo;

import it.progess.invoicecreator.vo.Storage;
import it.progess.invoicecreator.vo.StorageSerialCode;

@Entity
@Table(name="tblstorage")
public class TblStorage implements Itbl {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idStorage")
	private int idStorage;
	@ManyToOne
	@JoinColumn(name = "idUnitMeasure")
	private TblUnitMeasure um;
	@ManyToOne
	@JoinColumn(name = "idProduct")
	private TblProduct product;
	@Column(name="loaded")
	private double load;
	@Column(name="unloaded")
	private double unload;
	@Column(name="reserved")
	private double reserved;
	@Column(name="stock")
	private double stock;
	@OneToMany(fetch= FetchType.LAZY,mappedBy = "storage",cascade = CascadeType.ALL)
	private Set<TblStorageSerialCode> storagesc;
	public Set<TblStorageSerialCode> getStoragesc() {
		return storagesc;
	}
	public void setStoragesc(Set<TblStorageSerialCode> storagesc) {
		this.storagesc = storagesc;
	}
	public int getIdStorage() {
		return idStorage;
	}
	public void setIdStorage(int idStorage) {
		this.idStorage = idStorage;
	}
	public TblUnitMeasure getUm() {
		return um;
	}
	public void setUm(TblUnitMeasure um) {
		this.um = um;
	}
	public TblProduct getProduct() {
		return product;
	}
	public void setProduct(TblProduct product) {
		this.product = product;
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
		Storage st = (Storage)obj;
		this.idStorage = st.getIdStorage();
		this.load = st.getLoad();
		this.reserved = st.getReserved();
		this.stock = st.getStock();
		this.unload = st.getUnload();
		if (st.getProduct() != null){
			this.product = new TblProduct();
			this.product.convertToTable(st.getProduct());
		}
		if (st.getUm() != null){
			this.um = new TblUnitMeasure();
			this.um.convertToTable(st.getUm());
		}
		if (st.getStoragesc() != null && st.getStoragesc().size() >0){
			this.storagesc = new HashSet<TblStorageSerialCode>();
			for(Iterator<StorageSerialCode> it = st.getStoragesc().iterator();it.hasNext();){
				StorageSerialCode sc = it.next();
				TblStorageSerialCode scNew = new TblStorageSerialCode();
				scNew.convertToTable(sc);
				scNew.setStorage(this);
				this.storagesc.add(scNew);
			}
		}
	}
}
