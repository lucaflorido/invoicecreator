package it.progess.invoicecreator.vo;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import it.progess.invoicecreator.hibernate.DataUtilConverter;
import it.progess.invoicecreator.pojo.Itbl;
import it.progess.invoicecreator.pojo.TblCustomer;
import it.progess.invoicecreator.pojo.TblHead;
import it.progess.invoicecreator.pojo.TblRow;
import it.progess.invoicecreator.properties.GECOParameter;

public class Head implements Ivo {
	private int idHead;
	private int number;
	private String date;
	private float amount4;
	private float taxamount4;
	private float total4;
	private float amount10;
	private float taxamount10;
	private float total10;
	private float amount20;
	private float taxamount20;
	private float total20;
	private float amount;
	private float taxamount;
	private float total;
	private String note;
	private Customer customer;
	private Supplier supplier;
	private Destination destination;
	private Document document;
	private Transporter transporter;
	private Payment payment;
	private List list;
	private Set<Row> rows;
	private boolean generate = false;
	private boolean disable;
	private boolean generateBy;
	private boolean generateTo;
	private boolean incomplete;
	private Company company;
	private float withholdingtax;
	private TaxRate taxrate;
	private String userinsert;
	private String dateinsert;
	
	public String getUserinsert() {
		return userinsert;
	}
	public void setUserinsert(String userinsert) {
		this.userinsert = userinsert;
	}
	public String getDateinsert() {
		return dateinsert;
	}
	public void setDateinsert(String dateinsert) {
		this.dateinsert = dateinsert;
	}
	public float getWithholdingtax() {
		return withholdingtax;
	}
	public void setWithholdingtax(float withholdingtax) {
		this.withholdingtax = withholdingtax;
	}
	public TaxRate getTaxrate() {
		return taxrate;
	}
	public void setTaxrate(TaxRate taxrate) {
		this.taxrate = taxrate;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public boolean isIncomplete() {
		return incomplete;
	}
	public void setIncomplete(boolean incomplete) {
		this.incomplete = incomplete;
	}
	public boolean isGenerateBy() {
		return generateBy;
	}
	public void setGenerateBy(boolean generateBy) {
		this.generateBy = generateBy;
	}
	public boolean isGenerateTo() {
		return generateTo;
	}
	public void setGenerateTo(boolean generateTo) {
		this.generateTo = generateTo;
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
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
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
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Supplier getSupplier() {
		return supplier;
	}
	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	public Destination getDestination() {
		return destination;
	}
	public void setDestination(Destination destination) {
		this.destination = destination;
	}
	public Document getDocument() {
		return document;
	}
	public void setDocument(Document document) {
		this.document = document;
	}
	public Transporter getTransporter() {
		return transporter;
	}
	public void setTransporter(Transporter transporter) {
		this.transporter = transporter;
	}
	public Payment getPayment() {
		return payment;
	}
	public void setPayment(Payment payment) {
		this.payment = payment;
	}
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
	public Set<Row> getRows() {
		return rows;
	}
	public void setRows(Set<Row> rows) {
		this.rows = rows;
	}
	public boolean isGenerate() {
		return generate;
	}
	public void setGenerate(boolean generate) {
		this.generate = generate;
	}
	public void convertFromTable(Itbl obj){
		TblHead h = (TblHead)obj;
		this.convertFromTableCommon(h);
		if (h.getCustomer() != null){
			this.customer = new Customer();
			this.customer.convertFromTable((TblCustomer)h.getCustomer());
		}
		checkIncomplete(h.getRows());
	} 
	private void checkIncomplete(Set<TblRow> tbrows){
		if (tbrows != null){
			for (Iterator<TblRow> it = tbrows.iterator(); it.hasNext();){
				TblRow row = it.next();
					if ((row.getProduct().getManageserialnumber() == true && (row.getSerialnumber() == "" || row.getExpiredate() == null || row.getSerialnumber() == null || row.getExpiredate() == null)) && this.getDocument().getStoremovement().isStoreMovementActive()){
						
							this.setIncomplete(true);
						
						break;
					}
				
			}
		}
	}
	public void convertFromTableCommon(TblHead h){
		this.amount = h.getAmount();
		this.amount10 = h.getAmount10();
		this.amount20 = h.getAmount20();
		this.amount4 = h.getAmount4();
		this.date =DataUtilConverter.convertStringFromDate(h.getDate());
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
		if (h.getDestination() != null){
			this.destination = new Destination();
			this.destination.convertFromTable(h.getDestination());
		}
		if (h.getDocument() != null){
			this.document = new Document();
			this.document.convertFromTable(h.getDocument());
		}
		if (h.getList() != null){
			this.list = new List();
			this.list.convertFromTable(h.getList());
		}
		if (h.getPayment()!=null){
			this.payment = new Payment();
			this.payment.convertFromTable(h.getPayment());
		}
		if (h.getSupplier() != null){
			this.supplier = new Supplier();
			this.supplier.convertFromTable(h.getSupplier());
		}
		if (h.getTransporter() != null){
			this.transporter = new Transporter();
			this.transporter.convertFromTable(h.getTransporter());
		}
		if (h.getGeneratedFromHeads() != null && h.getGeneratedFromHeads().size() > 0){
			this.generateBy = true;
		}else{
			this.generateBy = false;
		}
		if (h.getGeneretedHead() != null && h.getGeneretedHead().size() > 0){
			this.generateTo = true;
		}else{
			this.generateTo = false;
		}
		if (h.getCompany() != null){
			this.company = new Company();
			this.company.convertFromTable(h.getCompany());
		}
		if (h.getTaxrate() != null){
			this.taxrate = new TaxRate();
			this.taxrate.convertFromTable(h.getTaxrate());
		}
		this.withholdingtax = h.getWithholdingtax();
		this.userinsert = h.getUsercreate();
		this.dateinsert = DataUtilConverter.convertStringFromDate(h.getDateinsert());
	}
	public void convertFromTableSingle(Itbl obj){
		this.convertFromTable(obj);
		TblHead h = (TblHead)obj;
		this.convertFromTableCommon(h);
		if (h.getCustomer() != null){
			this.customer = new Customer();
			this.customer.convertFromTableSingle((TblCustomer)h.getCustomer());
		}
		if (h.getRows() != null){
			this.rows = new HashSet<Row>();
			for (Iterator<TblRow> iterator = h.getRows().iterator(); iterator.hasNext();){
				TblRow rowtbl = iterator.next();
				Row row = new Row();
				row.convertFromTable(rowtbl);
				this.rows.add(row);
			}
		}
	}
	public void convertFromTableSingle(Itbl obj,Supplier sup){
		this.convertFromTable(obj);
		TblHead h = (TblHead)obj;
		this.convertFromTableCommon(h);
		if (h.getCustomer() != null){
			this.customer = new Customer();
			this.customer.convertFromTableSingle((TblCustomer)h.getCustomer());
		}
		if (h.getRows() != null){
			this.rows = new HashSet<Row>();
			for (Iterator<TblRow> iterator = h.getRows().iterator(); iterator.hasNext();){
				TblRow rowtbl = iterator.next();
				Row row = new Row();
				if (rowtbl.getProduct().getSupplier().getIdSupplier() == sup.getIdSupplier()){
					row.convertFromTable(rowtbl);
					this.rows.add(row);
				}
			}
		}
	}
	public void calculateNumber(){
	    if (date.length() > 0){
	    	String[] dateArray = this.date.split("/");
	    	if (dateArray.length > 2){
		    	int year = Integer.parseInt(dateArray[2]);
				for (Iterator<CounterYear> iterator = this.document.getCounter().getYearsvalue().iterator();iterator.hasNext();){
					CounterYear cy = iterator.next();
					if (cy.getYear() == year){
						if (this.number >= cy.getValue()){
							cy.setValue(this.number + 1);
						}else if(this.number < cy.getValue() && this.number == 0 && this.idHead == 0){
							this.number = cy.getValue();
							//cy.setValue(this.number + 1);
						}
						
					}
				}
	    	}
	    }
		
	}
	public void calculateNumber(int index){
	    if (date.length() > 0){
	    	String[] dateArray = this.date.split("/");
	    	if (dateArray.length > 2){
		    	int year = Integer.parseInt(dateArray[2]);
				for (Iterator<CounterYear> iterator = this.document.getCounter().getYearsvalue().iterator();iterator.hasNext();){
					CounterYear cy = iterator.next();
					if (cy.getYear() == year){
						if (this.number >= cy.getValue()){
							cy.setValue(this.number +1);
						}else if(this.number < cy.getValue() && this.number == 0 && this.idHead == 0){
							this.number = cy.getValue();
							cy.setValue(this.number +1);
						}
						
					}
				}
	    	}
	    }
		
	}
	private GECOError genericControl(){
		GECOError er = null;
		if(this.number == 0){
			er = new GECOError(GECOParameter.ERROR_VALUE_MISSING,"Numero Documento mancante");
		}
		if (this.date == "" || this.date == null){
			er = new GECOError(GECOParameter.ERROR_VALUE_MISSING,"Data mancante");
		}
		if (this.document == null || this.document.getIdDocument() == 0){
			er = new GECOError(GECOParameter.ERROR_VALUE_MISSING,"Tipo documento mancante");
		}else{
			if (this.document.getCustomer() == true && (this.customer == null || this.customer.getIdCustomer() == 0)){
				if ((this.transporter == null || this.transporter.getIdTransporter() == 0) && this.getDocument().getInternal() == false){
					er = new GECOError(GECOParameter.ERROR_VALUE_MISSING,"Cliente o Vettore mancante");
				}
			}
			if (this.document.getSupplier() == true && ((this.supplier == null || this.supplier.getIdSupplier() == 0)) && this.getDocument().getInternal() == false)
				er = new GECOError(GECOParameter.ERROR_VALUE_MISSING,"fornitore mancante");
			if ((this.document.isCredit() == true || this.document.isDebit() == true)  && (this.payment == null || this.payment.getIdPayment() == 0))
				er = new GECOError(GECOParameter.ERROR_VALUE_MISSING,"Pagamento mancante");
		}
		return er;
	}
	public GECOError control(boolean forceSerialNumber){
		GECOError er = null;
		er = genericControl();
		if (er==null){
			if (this.rows != null){
				for (Iterator<Row> it = rows.iterator(); it.hasNext();){
					Row row = it.next();
					if (row.getProductcode() == "" && row.getProductdescription() == "" && row.getProductum() == ""){
						rows.remove(row);
					}else{
						if (row.getProductcode() == "" ){
							er = new GECOError(GECOParameter.ERROR_VALUE_MISSING,"Codice Prodotto  mancante");
							break;
						}
						if (row.getProductdescription() == "" ){
							er = new GECOError(GECOParameter.ERROR_VALUE_MISSING,"Descrizione Prodotto  mancante");
							break;
						}
						if (row.getProductum() == "" ){
							er = new GECOError(GECOParameter.ERROR_VALUE_MISSING,"Unità misura Prodotto  mancante");
							break;
						}
						if ((row.getProduct().getManageserialnumber() == true && (row.getSerialnumber() == "" || row.getExpiredate() == "" || row.getSerialnumber() == null || row.getExpiredate() == null)) && this.getDocument().getStoremovement().isStoreMovementActive()){
							if (forceSerialNumber != true){
								er = new GECOError(GECOParameter.ERROR_VALUE_MISSING,"Lotto  mancante per il prodotto "+row.getProduct().getCode());
							}else{
								this.setIncomplete(true);
							}
							break;
						}
					}
				}
			}
		}
		return er;
	}
	public GECOError control(){
		GECOError er = null;
		clean();
		er = genericControl();
		if (er==null){
			if (this.rows != null){
				for (Iterator<Row> it = rows.iterator(); it.hasNext();){
					Row row = it.next();
					if (row.getProductcode() == "" && row.getProductdescription() == "" && row.getProductum() == ""){
						rows.remove(row);
					}else{
						if (row.getProductcode() == "" ){
							er = new GECOError(GECOParameter.ERROR_VALUE_MISSING,"Codice Prodotto  mancante");
							break;
						}
						if (row.getProductdescription() == "" ){
							er = new GECOError(GECOParameter.ERROR_VALUE_MISSING,"Descrizione Prodotto  mancante");
							break;
						}
						if (row.getProductum() == "" ){
							er = new GECOError(GECOParameter.ERROR_VALUE_MISSING,"Unità misura Prodotto  mancante");
							break;
						}
						if ((row.getProduct().getManageserialnumber() == true && (row.getSerialnumber() == "" || row.getExpiredate() == "" || row.getSerialnumber() == null || row.getExpiredate() == null)) && this.getDocument().getStoremovement().isStoreMovementActive()){
							er = new GECOError(GECOParameter.ERROR_VALUE_MISSING,"Lotto  mancante per il prodotto "+row.getProduct().getCode());
							break;
						}
					}
				}
			}
		}
		return er;
	}
	private void clean(){
		if (rows != null && rows.size() >0){
			for (Iterator<Row> it = rows.iterator();it.hasNext();){
				Row r = it.next();
				if (r.getProduct() == null){
					rows.remove(r);
				}
			}
		}
	}
}
