package it.progess.invoicecreator.pojo;

import it.progess.invoicecreator.hibernate.DataUtilConverter;
import it.progess.invoicecreator.vo.Ivo;
import it.progess.invoicecreator.vo.ListProduct;
import it.progess.invoicecreator.vo.Product;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name="tbllist_product")
public class TblListProduct implements Itbl{
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idListProduct")
	private int idListProduct;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idList")
	@Fetch(FetchMode.SELECT)
	private TblList list;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idProduct")
	@Fetch(FetchMode.SELECT)
	private TblProduct product;
	@Column(name="price")
	private float price;
	@Column(name="startdate")
	private Date startdate;
	@Column(name="active")
	private boolean active;
	@Column(name="percentage")
	private float percentage;
	public float getPercentage() {
		return percentage;
	}
	public void setPercentage(float percentage) {
		this.percentage = percentage;
	}
	public Date getStartdate() {
		return startdate;
	}
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public int getIdListProduct() {
		return idListProduct;
	}
	public void setIdListProduct(int idListProduct) {
		this.idListProduct = idListProduct;
	}
	public TblList getList() {
		return list;
	}
	public void setList(TblList list) {
		this.list = list;
	}

	public TblProduct getProduct() {
		return product;
	}
	public void setProduct(TblProduct product) {
		this.product = product;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public void convertToTable(Ivo obj){
		ListProduct ltp = (ListProduct)obj;
		this.idListProduct = ltp.getIdListProduct();
		this.price = ltp.getPrice();
		this.product = new TblProduct();
		this.percentage = ltp.getPercentage();
		if (ltp.getProduct()!=null){
			this.product.convertToTable(ltp.getProduct());
		}
		if (ltp.getList() != null){
			this.list = new TblList();
			this.list.convertToTable(ltp.getList());
		}
	}
	public void convertToTableForSaving(Ivo obj,Itbl tbl){
		convertToTable(obj);
		
		this.startdate = DataUtilConverter.convertDateFromString(((ListProduct)obj).getStartdate());
		this.active = ((ListProduct)obj).isActive();
		if (this.startdate == null && this.idListProduct == 0){
			this.startdate = ((TblList)tbl).getStartdate();
			this.active = true;
			this.percentage = ((TblList)tbl).getIncrement();
		}
		this.list = (TblList)tbl;
	}
}
