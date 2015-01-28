package it.progess.invoicecreator.vo;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;



import it.progess.invoicecreator.pojo.Itbl;
import it.progess.invoicecreator.pojo.TblStorage;
import it.progess.invoicecreator.pojo.TblStorageSerialCode;
import it.progess.invoicecreator.properties.GECOParameter;


public class Storage implements Ivo {
	private int idStorage;
	private UnitMeasure um;
	private Product product;
	private double load;
	private double unload;
	private double reserved;
	private double stock;
	private Set<StorageSerialCode> storagesc;
	public Set<StorageSerialCode> getStoragesc() {
		return storagesc;
	}
	public void setStoragesc(Set<StorageSerialCode> storagesc) {
		this.storagesc = storagesc;
	}
	public int getIdStorage() {
		return idStorage;
	}
	public void setIdStorage(int idStorage) {
		this.idStorage = idStorage;
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
	public UnitMeasure getUm() {
		return um;
	}
	public void setUm(UnitMeasure um) {
		this.um = um;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public void convertFromTable(Itbl obj){
		TblStorage st = (TblStorage)obj;
		this.idStorage = st.getIdStorage();
		this.load = st.getLoad();
		this.reserved = st.getReserved();
		this.stock = st.getStock();
		this.unload = st.getUnload();
		if (st.getProduct() != null){
			this.product = new Product();
			this.product.convertFromTable(st.getProduct());
		}
		if (st.getUm() != null){
			this.um = new UnitMeasure();
			this.um.convertFromTable(st.getUm());
		}
		if (st.getStoragesc().size() >0){
			this.storagesc = new HashSet<StorageSerialCode>();
			for(Iterator<TblStorageSerialCode> it = st.getStoragesc().iterator();it.hasNext();){
				TblStorageSerialCode sc = it.next();
				StorageSerialCode scNew = new StorageSerialCode();
				scNew.convertFromTable(sc);
				this.storagesc.add(scNew);
			}
		}
	}
	public void loadRow(Row row){
		this.product = row.getProduct();
		float conv = this.conversionRate(row.getProduct(), row.getUm());
		this.load = this.load + (row.getQuantity() * conv);
		this.stock = this.stock + (row.getQuantity() * conv);
		this.um = getBaseUM(product);
		if (row.getSerialnumber() != null && row.getSerialnumber().equals("") != true){
			StorageSerialCode sc = getStorageSerialCode(row.getSerialnumber());
			if (sc == null){
				sc = new StorageSerialCode();
				this.storagesc = new HashSet<StorageSerialCode>();
				this.storagesc.add(sc);
			}
			sc.loadSerial(this, row.getQuantity()*conv, row.getSerialnumber(), row.getExpiredate());
		}
	}
	public void removeloadRow(Row row){
		this.product = row.getProduct();
		float conv = this.conversionRate(row.getProduct(), row.getUm());
		this.load = this.load - (row.getQuantity() * conv);
		this.stock = this.stock - (row.getQuantity() * conv);
		this.um = getBaseUM(product);
		if (row.getSerialnumber() != null && row.getSerialnumber().equals("") != true){
			StorageSerialCode sc = getStorageSerialCode(row.getSerialnumber());
			if (sc == null){
				sc = new StorageSerialCode();
				this.storagesc = new HashSet<StorageSerialCode>();
				this.storagesc.add(sc);
			}
			sc.removeloadSerial(this, row.getQuantity()*conv, row.getSerialnumber(), row.getExpiredate());
		}
	}
	public void unloadRow(Row row){
		this.product = row.getProduct();
		float conv = this.conversionRate(row.getProduct(), row.getUm());
		this.unload = this.unload + (row.getQuantity() * conv);
		this.stock = this.stock - (row.getQuantity() * conv);
		this.um = getBaseUM(product);
		if (row.getSerialnumber() != null && row.getSerialnumber().equals("") != true){
			StorageSerialCode sc = getStorageSerialCode(row.getSerialnumber());
			if (sc == null){
				sc = new StorageSerialCode();
				this.storagesc = new HashSet<StorageSerialCode>();
				this.storagesc.add(sc);
			}
			sc.unloadSerial(this, row.getQuantity(), row.getSerialnumber(), row.getExpiredate());
		}
	}
	public void removeunloadRow(Row row){
		this.product = row.getProduct();
		float conv = this.conversionRate(row.getProduct(), row.getUm());
		this.unload = this.unload - (row.getQuantity() * conv);
		this.stock = this.stock + (row.getQuantity() * conv);
		this.um = getBaseUM(product);
		if (row.getSerialnumber() != null && row.getSerialnumber().equals("") != true){
			StorageSerialCode sc = getStorageSerialCode(row.getSerialnumber());
			if (sc == null){
				sc = new StorageSerialCode();
				this.storagesc = new HashSet<StorageSerialCode>();
				this.storagesc.add(sc);
			}
			sc.removeunloadSerial(this, row.getQuantity(), row.getSerialnumber(), row.getExpiredate());
		}
	}
	private float conversionRate(Product prod,UnitMeasure um){
		float conv = 1;
		if (prod.getUms() != null && prod.getUms().size() > 0){
			for(Iterator<UnitMeasureProduct> it = prod.getUms().iterator();it.hasNext();){
				UnitMeasureProduct ump = it.next();
				if (ump.getUm().getIdUnitMeasure() == um.getIdUnitMeasure()){
					conv = ump.getConversion();
				}
			}
		}
		return conv;
	}
	private UnitMeasure getBaseUM(Product prod){
		UnitMeasure um = new UnitMeasure();
		if (prod.getUms() != null && prod.getUms().size() > 0){
			for(Iterator<UnitMeasureProduct> it = prod.getUms().iterator();it.hasNext();){
				UnitMeasureProduct ump = it.next();
				if (ump.getConversion() == 1){
					um = ump.getUm();
				}
			}
		}
		return um;
	}
	private StorageSerialCode getStorageSerialCode(String serial){
		if (this.getStoragesc() != null){
			for (Iterator<StorageSerialCode> it = this.getStoragesc().iterator(); it.hasNext();){
				StorageSerialCode sc = it.next();
				if (sc.getSerialcode().equals(serial) == true){
					return sc;
				}
			}
		}
		return null;
	}
	public GECOObject control(){
		return null;
	}
}
