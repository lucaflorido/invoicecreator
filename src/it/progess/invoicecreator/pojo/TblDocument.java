package it.progess.invoicecreator.pojo;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;







import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import it.progess.invoicecreator.vo.Document;
import it.progess.invoicecreator.vo.DocumentFlow;
import it.progess.invoicecreator.vo.Ivo;
import it.progess.invoicecreator.vo.ListProduct;


@Entity
@Table(name="tbldocument")
public class TblDocument implements Itbl{
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idDocument")
	private int idDocument;
	@Column(name="code")
	private String code;
	@Column(name="description")
	private String description;
	@Column(name="customer")
	private boolean customer;
	@Column(name="supplier")
	private boolean supplier;
	@Column(name="internal")
	private boolean internal;
	@ManyToOne
	@JoinColumn(name = "idStoreMovement")
	private TblStoreMovement storemovement;
	@ManyToOne
	@JoinColumn(name = "idCounter")
	private TblCounter counter;
	@Column(name="credit")
	private boolean credit;
	@Column(name="debit")
	private boolean debit;
	@Column(name="orderdoc")
	private boolean order;
	@Column(name="invoice")
	private boolean invoice;
	@Column(name="transport")
	private boolean transport;
	@ManyToOne
	@JoinColumn(name = "idCompany")
	private TblCompany company;
	@Column(name="expireday")
	private int expireday;
	@Column(name="online")
	private boolean online;
	@OneToMany(mappedBy = "documentSource")
	@Fetch(FetchMode.SELECT)
	private Set<TblDocumentFlow> flows;
	public boolean isOnline() {
		return online;
	}
	public void setOnline(boolean online) {
		this.online = online;
	}
	public int getExpireday() {
		return expireday;
	}
	public void setExpireday(int expireday) {
		this.expireday = expireday;
	}
	public TblCompany getCompany() {
		return company;
	}
	public void setCompany(TblCompany company) {
		this.company = company;
	}
	public boolean isOrder() {
		return order;
	}
	public void setOrder(boolean order) {
		this.order = order;
	}
	public boolean isInvoice() {
		return invoice;
	}
	public void setInvoice(boolean invoice) {
		this.invoice = invoice;
	}
	public boolean isTransport() {
		return transport;
	}
	public void setTransport(boolean transport) {
		this.transport = transport;
	}
	public void setDebit(boolean debit) {
		this.debit = debit;
	}
	public boolean isCredit() {
		return credit;
	}
	public void setCredit(boolean credit) {
		this.credit = credit;
	}
	public boolean isDebit() {
		return debit;
	}
	public TblCounter getCounter() {
		return counter;
	}
	public void setCounter(TblCounter counter) {
		this.counter = counter;
	}
	public TblStoreMovement getStoremovement() {
		return storemovement;
	}
	public void setStoremovement(TblStoreMovement storemovement) {
		this.storemovement = storemovement;
	}
	public int getIdDocument() {
		return idDocument;
	}
	public void setIdDocument(int idDocument) {
		this.idDocument = idDocument;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean getCustomer() {
		return customer;
	}
	public void setCustomer(boolean customer) {
		this.customer = customer;
	}
	public boolean getSupplier() {
		return supplier;
	}
	public void setSupplier(boolean supplier) {
		this.supplier = supplier;
	}
	public boolean getInternal() {
		return internal;
	}
	public void setInternal(boolean internal) {
		this.internal = internal;
	}
	
	public Set<TblDocumentFlow> getFlows() {
		return flows;
	}
	public void setFlows(Set<TblDocumentFlow> flows) {
		this.flows = flows;
	}
	public void convertToTableWithSources(Ivo obj){
		Document dc = (Document)obj;
		this.convertToTable(obj);
		if (dc.getFlows() != null){
			this.flows = new HashSet<TblDocumentFlow>();
			for (Iterator<DocumentFlow> iterator = dc.getFlows().iterator(); iterator.hasNext();){
				DocumentFlow df = iterator.next();
				TblDocumentFlow tdf = new TblDocumentFlow();
				tdf.convertToTable(df);
				this.flows.add(tdf);
			}
		}
	}
	public void convertToTable(Ivo obj){
		Document dc = (Document)obj;
		this.code = dc.getCode();
		this.customer = dc.getCustomer();
		this.description = dc.getDescription();
		this.idDocument = dc.getIdDocument();
		this.internal = dc.getInternal();
		this.supplier = dc.getSupplier();
		this.credit = dc.isCredit();
		this.debit = dc.isDebit();
		this.order = dc.isOrder();
		this.invoice = dc.isInvoice();
		this.transport = dc.isTransport();
		this.expireday = dc.getExpireday();
		this.online = dc.isOnline();
		if (dc.getStoremovement() != null){
			this.storemovement = new TblStoreMovement();
			this.storemovement.convertToTable(dc.getStoremovement());
		}
		if (dc.getCounter() != null){
			this.counter = new TblCounter();
			this.counter.convertToTable(dc.getCounter());
		}
		if(dc.getCompany() != null){
			this.company = new TblCompany();
			this.company.convertToTable(dc.getCompany());
		}
	}
}
