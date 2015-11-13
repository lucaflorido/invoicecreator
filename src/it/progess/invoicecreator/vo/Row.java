package it.progess.invoicecreator.vo;

import java.util.Date;

import javax.persistence.Column;

import it.progess.invoicecreator.hibernate.DataUtilConverter;
import it.progess.invoicecreator.pojo.Itbl;
import it.progess.invoicecreator.pojo.TblProduct;
import it.progess.invoicecreator.pojo.TblRow;
import it.progess.invoicecreator.pojo.TblTaxrate;
import it.progess.invoicecreator.pojo.TblUnitMeasure;
import it.progess.invoicecreator.properties.GECOParameter;


public class Row implements Ivo{
	private int idRow;
	private String productcode;
	private String productdescription;
	private String productum;
	private double quantity;
	private float total;
	private float amount;
	private float taxamount;
	private float price;
	private String type;
	private Product product;
	private TaxRate taxrate;
	private Head head;
	private UnitMeasure um;
	private String serialnumber;
	private String expiredate;
	private boolean generate;
	private double necks;
	private float conversion;
	private String usercreate;
	private String userupdate;
	private String dateupdate;
	private String dateinsert;
	
	

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
	public String getDateupdate() {
		return dateupdate;
	}
	public void setDateupdate(String dateupdate) {
		this.dateupdate = dateupdate;
	}
	public String getDateinsert() {
		return dateinsert;
	}
	public void setDateinsert(String dateinsert) {
		this.dateinsert = dateinsert;
	}
	public double getNecks() {
		return necks;
	}
	public void setNecks(double necks) {
		this.necks = necks;
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
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public TaxRate getTaxrate() {
		return taxrate;
	}
	public void setTaxrate(TaxRate taxrate) {
		this.taxrate = taxrate;
	}
	public Head getHead() {
		return head;
	}
	public void setHead(Head head) {
		this.head = head;
	}
	public String getSerialnumber() {
		return serialnumber;
	}
	public void setSerialnumber(String serialcode) {
		this.serialnumber = serialcode;
	}
	public String getExpiredate() {
		return expiredate;
	}
	public void setExpiredate(String expiredate) {
		this.expiredate = expiredate;
	}
	public boolean isGenerate() {
		return generate;
	}
	public void setGenerate(boolean generate) {
		this.generate = generate;
	}
	public UnitMeasure getUm() {
		return um;
	}
	public void setUm(UnitMeasure um) {
		this.um = um;
	}
	
	public float getConversion() {
		return conversion;
	}
	public void setConversion(float conversion) {
		this.conversion = conversion;
	}
	public void convertFromTable(Itbl obj){
		TblRow h = (TblRow)obj;
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
		this.expiredate = DataUtilConverter.convertStringFromDate(h.getExpiredate());
		this.necks = h.getNecks();
		this.conversion = h.getConversion();
		if (h.getProduct()!= null){
			this.product = new Product();
			this.product.convertFromTable(h.getProduct());
		}
		if (h.getTaxrate()!= null){
			this.taxrate = new TaxRate();
			this.taxrate.convertFromTable(h.getTaxrate());
		}
		if (h.getUm() != null){
			this.um = new UnitMeasure();
			this.um.convertFromTable(h.getUm());
		}
		this.usercreate = h.getUsercreate();
		this.userupdate = h.getUserupdate();
		this.dateinsert = DataUtilConverter.convertStringFromDate(h.getDateinsert());
		this.dateupdate = DataUtilConverter.convertStringFromDate(h.getDateupdate());
	}
	public void copy(Row h){
		this.idRow = h.getIdRow();
		this.productcode = h.getProductcode();
		this.productdescription = h.getProductdescription();
		this.productum = h.getProductum();
		this.quantity = h.getQuantity();
		this.taxamount = h.getTaxamount();
		this.total = h.getTotal();
		this.type = h.getType();
		this.serialnumber = h.getSerialnumber();
		this.expiredate = h.getExpiredate();
		this.necks = h.getNecks();
		if (h.getProduct()!= null){
			this.product = h.getProduct();
		}
		if (h.getTaxrate()!= null){
			this.taxrate = h.getTaxrate();
		}
		if (h.getUm() != null){
			this.um = h.getUm();
		}
	}
	public void addProduct(UnitMeasureProduct um){
		Product p = um.getProduct();
		this.setProduct(p);
		this.setProductcode(um.getCode());
		this.setProductdescription(p.getDescription());
		this.setUm(um.getUm());
		this.setProductum(um.getUm().getCode());
		this.setQuantity(um.getQuantity());
		this.setPrice(p.getListprice()* um.getConversion());
		this.setType("V");
		this.setTaxrate(p.getTaxrate());
	}

	public GECOObject control(){
		return null;
	}
}
