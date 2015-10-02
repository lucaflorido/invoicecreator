package it.progess.invoicecreator.pojo;
import it.progess.invoicecreator.vo.Customer;
import it.progess.invoicecreator.vo.Destination;
import it.progess.invoicecreator.vo.Ivo;
import it.progess.invoicecreator.vo.ListCustomer;




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
@Entity
@Table(name="tblcustomer")
public class TblCustomer extends it.progess.model.pojo.Customer implements Itbl{
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idCustomer")
	private int idCustomer;
	/*@Column(name="customername")
	private String customername;*/
	@Column(name="customercode")
	private String customercode;
	@Column(name="active")
	private boolean active;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idContact")
	private TblContact contact;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idAddress")
	private TblAddress address;
	@Column(name="taxcode")
	private String taxcode;
	@Column(name="serialnumber")
	private String serialnumber;
	@ManyToOne(fetch= FetchType.LAZY)
	@JoinColumn(name = "idGroup")
	private TblGroupCustomer group;
	@ManyToOne
	@JoinColumn(name = "idCategory")
	private TblCategoryCustomer category;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idBankContact")
	private TblBankContact bankcontact;
	@ManyToOne
	@JoinColumn(name = "idPayment")
	private TblPayment payment;
	@OneToMany(fetch= FetchType.LAZY,mappedBy = "customer",cascade = CascadeType.ALL)
	private Set<TblListCustomer> lists;
	@OneToMany(fetch= FetchType.LAZY,mappedBy = "customer",cascade = CascadeType.ALL)
	private Set<TblDestination> destinations;
	@Column(name="suspended")
	private double suspended;
	@ManyToOne
	@JoinColumn(name = "idCompany")
	private TblCompany company;
	@ManyToOne
	@JoinColumn(name = "idTaxRate")
	private TblTaxrate taxrate;
	@Column(name="commission")
	private Double commission;
	@ManyToOne
	@JoinColumn(name = "idPromoter")
	private TblPromoter promoter;
	@Column(name="alternativecode1")
	private String alternativecode1;
	@Column(name="alternativecode2")
	private String alternativecode2;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="idecpayment")
	private TblEcPayment ecpayment;
	@Column(name="isprivate")
	private boolean isprivate;
	public boolean isIsprivate() {
		return isprivate;
	}
	public void setIsprivate(boolean isprivate) {
		this.isprivate = isprivate;
	}
	public TblEcPayment getEcpayment() {
		return ecpayment;
	}
	public void setEcpayment(TblEcPayment ecpayment) {
		this.ecpayment = ecpayment;
	}
	public String getAlternativecode1() {
		return alternativecode1;
	}
	public void setAlternativecode1(String alternativecode1) {
		this.alternativecode1 = alternativecode1;
	}
	public String getAlternativecode2() {
		return alternativecode2;
	}
	public void setAlternativecode2(String alternativecode2) {
		this.alternativecode2 = alternativecode2;
	}
	public TblPromoter getPromoter() {
		return promoter;
	}
	public void setPromoter(TblPromoter promoter) {
		this.promoter = promoter;
	}
	public Double getCommission() {
		return commission;
	}
	public void setCommission(Double commission) {
		this.commission = commission;
	}
	public TblCustomer(){
		super();
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
	public double getSuspended() {
		return suspended;
	}
	public void setSuspended(double suspended) {
		this.suspended = suspended;
	}
	public Set<TblDestination> getDestinations() {
		return destinations;
	}
	public void setDestinations(Set<TblDestination> destinations) {
		this.destinations = destinations;
	}
	public int getIdCustomer() {
		return idCustomer;
	}
	public void setIdCustomer(int idCustomer) {
		this.idCustomer = idCustomer;
	}
	/*public String getCustomername() {
		return customername;
	}
	public void setCustomername(String customername) {
		this.customername = customername;
	}*/
	public String getCustomercode() {
		return customercode;
	}
	public void setCustomercode(String customercode) {
		this.customercode = customercode;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public TblContact getContact() {
		return contact;
	}
	public void setContact(TblContact contact) {
		this.contact = contact;
	}
	public TblAddress getAddress() {
		return address;
	}
	public void setAddress(TblAddress address) {
		this.address = address;
	}
	public String getTaxcode() {
		return taxcode;
	}
	public void setTaxcode(String taxcode) {
		this.taxcode = taxcode;
	}
	public String getSerialnumber() {
		return serialnumber;
	}
	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}
	public TblGroupCustomer getGroup() {
		return group;
	}
	public void setGroup(TblGroupCustomer group) {
		this.group = group;
	}
	public TblCategoryCustomer getCategory() {
		return category;
	}
	public void setCategory(TblCategoryCustomer category) {
		this.category = category;
	}
	public TblBankContact getBankcontact() {
		return bankcontact;
	}
	public void setBankcontact(TblBankContact bankcontact) {
		this.bankcontact = bankcontact;
	}
	public Set<TblListCustomer> getLists() {
		return lists;
	}
	public void setLists(Set<TblListCustomer> lists) {
		this.lists = lists;
	}
	public TblPayment getPayment() {
		return payment;
	}
	public void setPayment(TblPayment payment) {
		this.payment = payment;
	}
	public void convertToTable(Ivo obj){
		Customer c = (Customer)obj;
		this.active = c.isActive();
		this.suspended = c.getSuspended();
		this.alternativecode1 = c.getAlternativecode1();
		this.alternativecode2 = c.getAlternativecode2();
		this.isprivate = c.isIsprivate();
		if (c.getAddress() != null){
			this.address = new TblAddress();
			this.address.convertToTable(c.getAddress());
		}
		if (c.getBankcontact()!= null){
			this.bankcontact = new TblBankContact();
			this.bankcontact.convertToTable(c.getBankcontact());
		}
		if(c.getCategory() != null){
			this.category = new TblCategoryCustomer();
			this.category.convertToTable(c.getCategory());
		}
		if(c.getContact() != null){
			this.contact = new TblContact();
			this.contact.convertToTableCustomer(c.getContact());
		}
		this.customercode = c.getCustomercode();
		this.setCustomername(c.getCustomername());
		if (c.getGroup() != null){
			this.group = new TblGroupCustomer();
			this.group.convertToTable(c.getGroup());
		}
		if (c.getPayment() != null){
			this.payment = new TblPayment();
			this.payment.convertToTable(c.getPayment());
		}
		if (c.getPromoter() !=null){
			this.promoter = new TblPromoter();
			this.promoter.convertToTable(c.getPromoter());
		}
		this.idCustomer = c.getIdCustomer();
		this.serialnumber = c.getSerialnumber();
		this.taxcode = c.getTaxcode();
		if (c.getCompany() != null){
			this.company = new TblCompany();
			this.company.convertToTable(c.getCompany());
		}
		if (c.getTaxrate() != null){
			this.taxrate = new TblTaxrate();
			this.taxrate.convertToTable(c.getTaxrate());
			
		}
		this.commission = c.getCommission();
	}
	public void convertToTableSingle(Ivo obj){
		Customer c = (Customer)obj;
		this.convertToTable(obj);
		if (c.getLists() != null){
			this.lists = new HashSet<TblListCustomer>();
			for (Iterator<ListCustomer> iterator = c.getLists().iterator(); iterator.hasNext();){
				ListCustomer listproduct = iterator.next();
				TblListCustomer listp = new TblListCustomer();
				listp.convertToTable(listproduct);
				this.lists.add(listp);
			}
		}
		if (c.getDestinations() != null){
			this.destinations = new HashSet<TblDestination>();
			for (Iterator<Destination> iterator = c.getDestinations().iterator(); iterator.hasNext();){
				Destination destination = iterator.next();
				TblDestination dest = new TblDestination();
				dest.convertToTable(destination);
				this.destinations.add(dest);
			}
		}
	}
	public void convertToTableSingle(Ivo obj,Itbl tbl){
		Customer c = (Customer)obj;
		this.convertToTable(obj);
		if (c.getEcpayment() != null){
			this.ecpayment = new TblEcPayment();
			this.ecpayment.convertToTable(c.getEcpayment());
		}
		if (c.getLists() != null){
			this.lists = new HashSet<TblListCustomer>();
			for (Iterator<ListCustomer> iterator = c.getLists().iterator(); iterator.hasNext();){
				ListCustomer listproduct = iterator.next();
				TblListCustomer listp = new TblListCustomer();
				listp.convertToTableForSaving(listproduct,tbl);
				this.lists.add(listp);
			}
		}
		if (c.getDestinations() != null){
			this.destinations = new HashSet<TblDestination>();
			for (Iterator<Destination> iterator = c.getDestinations().iterator(); iterator.hasNext();){
				Destination destination = iterator.next();
				TblDestination dest = new TblDestination();
				dest.convertToTable(destination);
				dest.setCustomer((TblCustomer)tbl);
				this.destinations.add(dest);
			}
		}
	}
}
