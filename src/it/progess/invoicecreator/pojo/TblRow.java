package it.progess.invoicecreator.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import it.progess.invoicecreator.hibernate.DataUtilConverter;
import it.progess.invoicecreator.vo.Ivo;
import it.progess.invoicecreator.vo.Row;
@Entity
@Table(name="tblrow")
public class TblRow implements Itbl {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idRow")
	private int idRow;
	@Column(name="productcode")
	private String productcode;
	@Column(name="productdescription")
	private String productdescription;
	@Column(name="productum")
	private String productum;
	@Column(name="quantity")
	private double quantity;
	@Column(name="total")
	private float total;
	@Column(name="amount")
	private float amount;
	@Column(name="taxamount")
	private float taxamount;
	@Column(name="price")
	private float price;
	@Column(name="type")
	private String type;
	
	@Column(name="serialcode")
	private String serialnumber;
	@Column(name="expiredate")
	private Date expiredate;
	@ManyToOne
	@JoinColumn(name = "idProduct")
	private TblProduct product;
	@ManyToOne
	@JoinColumn(name = "idTaxrate")
	private TblTaxrate taxrate;
	@ManyToOne
	@JoinColumn(name = "idHead")
	private TblHead head;
	@ManyToOne
	@JoinColumn(name = "idUnitMeasure")
	private TblUnitMeasure um;
	@Column(name="necks")
	private double necks;
	@Column(name="conversion")
	private float conversion;
	
	@Column(name="usercreate")
	private String usercreate;
	@Column(name="userupdate")
	private String userupdate;
	@Column(name="dateupdate")
	private Date dateupdate;
	@Column(name="dateinsert")
	private Date dateinsert;
	
	public String getUsercreate() {
		return usercreate;
	}
	public void setUsercreate(String usercreate) {
		this.usercreate = usercreate;
	}
	public String getUserupdate() {
		return userupdate;
	}
	public void setUserupdate(String userupdate) {
		this.userupdate = userupdate;
	}
	public Date getDateupdate() {
		return dateupdate;
	}
	public void setDateupdate(Date dateupdate) {
		this.dateupdate = dateupdate;
	}
	public Date getDateinsert() {
		return dateinsert;
	}
	public void setDateinsert(Date dateinsert) {
		this.dateinsert = dateinsert;
	}
	public double getNecks() {
		return necks;
	}
	public void setNecks(double necks) {
		this.necks = necks;
	}
	public TblUnitMeasure getUm() {
		return um;
	}
	public void setUm(TblUnitMeasure um) {
		this.um = um;
	}
	public int getIdRow() {
		return idRow;
	}
	public void setIdRow(int idRow) {
		this.idRow = idRow;
	}
	public String getProductcode() {
		return productcode;
	}
	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}
	public String getProductdescription() {
		return productdescription;
	}
	public void setProductdescription(String productdescription) {
		this.productdescription = productdescription;
	}
	public String getProductum() {
		return productum;
	}
	public void setProductum(String productum) {
		this.productum = productum;
	}
	public double getQuantity() {
		return quantity;
	}
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	public float getTotal() {
		return total;
	}
	public void setTotal(float total) {
		this.total = total;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public float getTaxamount() {
		return taxamount;
	}
	public void setTaxamount(float taxamount) {
		this.taxamount = taxamount;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public TblProduct getProduct() {
		return product;
	}
	public void setProduct(TblProduct product) {
		this.product = product;
	}
	public TblTaxrate getTaxrate() {
		return taxrate;
	}
	public void setTaxrate(TblTaxrate taxrate) {
		this.taxrate = taxrate;
	}
	public TblHead getHead() {
		return head;
	}
	public void setHead(TblHead head) {
		this.head = head;
	}
	public String getSerialnumber() {
		return serialnumber;
	}
	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}
	public Date getExpiredate() {
		return expiredate;
	}
	public void setExpiredate(Date expiredate) {
		this.expiredate = expiredate;
	}
	
	public float getConversion() {
		return conversion;
	}
	public void setConversion(float conversion) {
		this.conversion = conversion;
	}
	public void convertToTable(Ivo obj){
		Row h = (Row)obj;
		this.amount = h.getAmount();
		this.idRow = h.getIdRow();
		this.price = h.getPrice();
		this.productcode = h.getProductcode();
		this.productdescription = h.getProductdescription();
		this.productum = h.getProductum();
		this.quantity = h.getQuantity();
		this.taxamount = h.getTaxamount();
		this.total = h.getTotal();
		this.type = h.getType();
		this.serialnumber = h.getSerialnumber();
		this.necks = h.getNecks();
		this.conversion = h.getConversion();
		if (this.conversion == 0)
			this.conversion = 1;
		this.expiredate = DataUtilConverter.convertDateFromString(h.getExpiredate());
		if (h.getProduct()!= null){
			this.product = new TblProduct();
			this.product.convertToTable(h.getProduct());
		}
		if (h.getTaxrate()!= null){
			this.taxrate = new TblTaxrate();
			this.taxrate.convertToTable(h.getTaxrate());
		}
		if (h.getUm() != null){
			this.um = new TblUnitMeasure();
			this.um.convertToTable(h.getUm());
		}
		this.usercreate = h.getUsercreate();
		this.userupdate = h.getUserupdate();
		this.dateinsert = DataUtilConverter.convertDateFromString(h.getDateinsert());
		this.dateupdate = DataUtilConverter.convertDateFromString(h.getDateupdate());
	}
}
