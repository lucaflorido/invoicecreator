package it.progess.invoicecreator.pojo;

import java.util.Date;
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

import it.progess.invoicecreator.hibernate.DataUtilConverter;
import it.progess.invoicecreator.vo.Head;
import it.progess.invoicecreator.vo.Ivo;
import it.progess.invoicecreator.vo.Row;
import it.progess.invoicecreator.vo.TaxRate;
@Entity
@Table(name="tblhead")
public class TblHead implements Itbl {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idHead")
	private int idHead;
	@Column(name="number")
	private int number;
	@Column(name="date")
	private Date date;
	@Column(name="amount4")
	private float amount4;
	@Column(name="taxamount4")
	private float taxamount4;
	@Column(name="total4")
	private float total4;
	@Column(name="amount10")
	private float amount10;
	@Column(name="taxamount10")
	private float taxamount10;
	@Column(name="total10")
	private float total10;
	@Column(name="amount20")
	private float amount20;
	@Column(name="taxamount20")
	private float taxamount20;
	@Column(name="total20")
	private float total20;
	@Column(name="amount")
	private float amount;
	@Column(name="taxamount")
	private float taxamount;
	@Column(name="total")
	private float total;
	@Column(name="note")
	private String note;
	@Column(name="withholdingtax")
	private float withholdingtax;
	@ManyToOne
	@JoinColumn(name = "idCustomer")
	private TblCustomer customer;
	@ManyToOne
	@JoinColumn(name = "idSupplier")
	private TblSupplier supplier;
	@ManyToOne
	@JoinColumn(name = "idDestination")
	private TblDestination destination;
	@ManyToOne
	@JoinColumn(name = "idDocument")
	private TblDocument document;
	@ManyToOne
	@JoinColumn(name = "idTransporter")
	private TblTransporter transporter;
	@ManyToOne
	@JoinColumn(name = "idPayment")
	private TblPayment payment;
	@ManyToOne
	@JoinColumn(name = "idList")
	private TblList list;
	@ManyToOne
	@JoinColumn(name = "idTaxRate")
	private TblTaxrate taxrate;
	@OneToMany(fetch= FetchType.LAZY,mappedBy = "head",cascade = CascadeType.ALL)
	private Set<TblRow> rows;
	@Column(name="disable")
	private boolean disable;
	@OneToMany(fetch= FetchType.LAZY,mappedBy = "headGenerate",cascade = CascadeType.ALL)
	private Set<TblGenerateHeadRow> generatedFromHeads;
	@OneToMany(fetch= FetchType.LAZY,mappedBy = "headSource",cascade = CascadeType.ALL)
	private Set<TblGenerateHeadRow> generetedHead;
	@ManyToOne
	@JoinColumn(name = "idCompany")
	private TblCompany company;
	
	public float getWithholdingtax() {
		return withholdingtax;
	}
	public void setWithholdingtax(float withholdingtax) {
		this.withholdingtax = withholdingtax;
	}
	public TblTaxrate getTaxrate() {
		return taxrate;
	}
	public void setTaxrate(TblTaxrate taxrate) {
		this.taxrate = taxrate;
	}
	public TblCompany getCompany() {
		return company;
	}
	public void setCompany(TblCompany company) {
		this.company = company;
	}
	public Set<TblGenerateHeadRow> getGeneratedFromHeads() {
		return generatedFromHeads;
	}
	public void setGeneratedFromHeads(Set<TblGenerateHeadRow> generatedFromHeads) {
		this.generatedFromHeads = generatedFromHeads;
	}
	
	public Set<TblGenerateHeadRow> getGeneretedHead() {
		return generetedHead;
	}
	public void setGeneretedHead(Set<TblGenerateHeadRow> generetedHead) {
		this.generetedHead = generetedHead;
	}
	public boolean isDisable() {
		return disable;
	}
	public void setDisable(boolean disable) {
		this.disable = disable;
	}
	public int getIdHead() {
		return idHead;
	}
	public void setIdHead(int idHead) {
		this.idHead = idHead;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public float getAmount4() {
		return amount4;
	}
	public void setAmount4(float amount4) {
		this.amount4 = amount4;
	}
	public float getTaxamount4() {
		return taxamount4;
	}
	public void setTaxamount4(float taxamount4) {
		this.taxamount4 = taxamount4;
	}
	public float getTotal4() {
		return total4;
	}
	public void setTotal4(float total4) {
		this.total4 = total4;
	}
	public float getAmount10() {
		return amount10;
	}
	public void setAmount10(float amount10) {
		this.amount10 = amount10;
	}
	public float getTaxamount10() {
		return taxamount10;
	}
	public void setTaxamount10(float taxamount10) {
		this.taxamount10 = taxamount10;
	}
	public float getTotal10() {
		return total10;
	}
	public void setTotal10(float total10) {
		this.total10 = total10;
	}
	public float getAmount20() {
		return amount20;
	}
	public void setAmount20(float amount20) {
		this.amount20 = amount20;
	}
	public float getTaxamount20() {
		return taxamount20;
	}
	public void setTaxamount20(float taxamount20) {
		this.taxamount20 = taxamount20;
	}
	public float getTotal20() {
		return total20;
	}
	public void setTotal20(float total20) {
		this.total20 = total20;
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
	public float getTotal() {
		return total;
	}
	public void setTotal(float total) {
		this.total = total;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public TblCustomer getCustomer() {
		return customer;
	}
	public void setCustomer(TblCustomer customer) {
		this.customer = customer;
	}
	public TblSupplier getSupplier() {
		return supplier;
	}
	public void setSupplier(TblSupplier supplier) {
		this.supplier = supplier;
	}
	public TblDestination getDestination() {
		return destination;
	}
	public void setDestination(TblDestination destination) {
		this.destination = destination;
	}
	public TblDocument getDocument() {
		return document;
	}
	public void setDocument(TblDocument document) {
		this.document = document;
	}
	public TblTransporter getTransporter() {
		return transporter;
	}
	public void setTransporter(TblTransporter transporter) {
		this.transporter = transporter;
	}
	public TblPayment getPayment() {
		return payment;
	}
	public void setPayment(TblPayment payment) {
		this.payment = payment;
	}
	public TblList getList() {
		return list;
	}
	public void setList(TblList list) {
		this.list = list;
	}
	public Set<TblRow> getRows() {
		return rows;
	}
	public void setRows(Set<TblRow> rows) {
		this.rows = rows;
	}
	public void convertToTable(Ivo obj){
		Head h = (Head)obj;
		this.amount = h.getAmount();
		this.amount10 = h.getAmount10();
		this.amount20 = h.getAmount20();
		this.amount4 = h.getAmount4();
		this.date =DataUtilConverter.convertDateFromString(h.getDate());
		this.idHead = h.getIdHead();
		this.note = h.getNote();
		this.number = h.getNumber();
		this.taxamount = h.getTaxamount();
		this.taxamount10 = h.getTaxamount10();
		this.taxamount20 = h.getTaxamount20();
		this.taxamount4 = h.getTaxamount4();
		this.total = h.getTotal();
		this.total10 = h.getTotal10();
		this.total20 = h.getTotal20();
		this.total4 = h.getTotal4();
		this.disable = h.isDisable();
		if (h.getCustomer() != null){
			this.customer = new TblCustomer();
			this.customer.convertToTable(h.getCustomer());
		}
		if (h.getDestination() != null){
			this.destination = new TblDestination();
			this.destination.convertToTable(h.getDestination());
		}
		if (h.getDocument() != null){
			this.document = new TblDocument();
			this.document.convertToTable(h.getDocument());
		}
		if (h.getList() != null){
			this.list = new TblList();
			this.list.convertToTable(h.getList());
		}
		if (h.getPayment()!=null){
			this.payment = new TblPayment();
			this.payment.convertToTable(h.getPayment());
		}
		if (h.getSupplier() != null){
			this.supplier = new TblSupplier();
			this.supplier.convertToTable(h.getSupplier());
		}
		if (h.getTransporter() != null){
			this.transporter = new TblTransporter();
			this.transporter.convertToTable(h.getTransporter());
		}
		if (h.getCompany() != null){
			this.company = new TblCompany();
			this.company.convertToTable(h.getCompany());
		}
		if (h.getTaxrate() != null){
			this.taxrate = new TblTaxrate();
			this.taxrate.convertToTable(h.getTaxrate());
		}
		this.withholdingtax = h.getWithholdingtax();
	}
	public void convertToTableSingle(Ivo obj){
		this.convertToTable(obj);
		Head h = (Head)obj;
		if (h.getRows() != null){
			for (Iterator<Row> iterator = h.getRows().iterator(); iterator.hasNext();){
				Row listrow = iterator.next();
				TblRow rowtbl = new TblRow();
				rowtbl.convertToTable(listrow);
				this.rows.add(rowtbl);
			}
		}
	
	}
	public void convertToTableSingleToSave(Ivo obj){
		this.convertToTable(obj);
		Head h = (Head)obj;
		if (h.getRows() != null){
			this.rows = new HashSet<TblRow>();
			for (Iterator<Row> iterator = h.getRows().iterator(); iterator.hasNext();){
				
				Row listrow = iterator.next();
				TblRow rowtbl = new TblRow();
				rowtbl.convertToTable(listrow);
				rowtbl.setHead(this);
				this.rows.add(rowtbl);
			}
		}
	
	}
	
}
