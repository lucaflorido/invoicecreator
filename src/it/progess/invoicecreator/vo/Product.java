package it.progess.invoicecreator.vo;

import it.progess.invoicecreator.pojo.Itbl;
import it.progess.invoicecreator.pojo.TblBrand;
import it.progess.invoicecreator.pojo.TblProduct;
import it.progess.invoicecreator.pojo.TblUnitMeasureProduct;
import it.progess.invoicecreator.properties.GECOParameter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Product implements Ivo,Comparable<Product>{
	private int idProduct;
	private String code;
	private String description;
	private String barcode;
	private int weightbarcode;
	private float sellprice;
	private float purchaseprice;
	private boolean manageserialnumber;
	private Set<UnitMeasureProduct> ums;
	private CategoryProduct category;
	private SubCategoryProduct subcategory;
	private GroupProduct group;
	private TaxRate taxrate;
	private UnitMeasure umselected;
	private Supplier supplier;
	private float listprice;
	private float conversionrate;
	private Storage storage;
	private Brand brand;
	private double percentage;
	private Set<ProductDatePrice> pricehistory;
	private Company company;
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public Set<ProductDatePrice> getPricehistory() {
		return pricehistory;
	}
	public void setPricehistory(Set<ProductDatePrice> pricehistory) {
		this.pricehistory = pricehistory;
	}
	public double getPercentage() {
		return percentage;
	}
	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}
	public Brand getBrand() {
		return brand;
	}
	public void setBrand(Brand brand) {
		this.brand = brand;
	}
	public Storage getStorage() {
		return storage;
	}
	public void setStorage(Storage storage) {
		this.storage = storage;
	}
	public UnitMeasure getUmselected() {
		return umselected;
	}
	public void setUmselected(UnitMeasure umselected) {
		this.umselected = umselected;
	}
	public float getListprice() {
		return listprice;
	}
	public void setListprice(float listprice) {
		this.listprice = listprice;
	}
	public float getConversionrate() {
		return conversionrate;
	}
	public void setConversionrate(float conversionrate) {
		this.conversionrate = conversionrate;
	}
	public TaxRate getTaxrate() {
		return taxrate;
	}
	public void setTaxrate(TaxRate taxrate) {
		this.taxrate = taxrate;
	}
	public int getIdProduct() {
		return idProduct;
	}
	public void setIdProduct(int idProduct) {
		this.idProduct = idProduct;
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
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public int getWeightbarcode() {
		return weightbarcode;
	}
	public void setWeightbarcode(int weightbarcode) {
		this.weightbarcode = weightbarcode;
	}
	public float getSellprice() {
		return sellprice;
	}
	public void setSellprice(float sellprice) {
		this.sellprice = sellprice;
	}
	public float getPurchaseprice() {
		return purchaseprice;
	}
	public void setPurchaseprice(float purchaseprice) {
		this.purchaseprice = purchaseprice;
	}
	public boolean getManageserialnumber() {
		return manageserialnumber;
	}
	public void setManageserialnumber(boolean manageserialnumber) {
		this.manageserialnumber = manageserialnumber;
	}
	public Set<UnitMeasureProduct> getUms() {
		return ums;
	}
	public void setUms(Set<UnitMeasureProduct> ums) {
		this.ums = ums;
	}
	
	public CategoryProduct getCategory() {
		return category;
	}
	public void setCategory(CategoryProduct category) {
		this.category = category;
	}
	public SubCategoryProduct getSubcategory() {
		return subcategory;
	}
	public void setSubcategory(SubCategoryProduct subcategory) {
		this.subcategory = subcategory;
	}
	public GroupProduct getGroup() {
		return group;
	}
	public void setGroup(GroupProduct group) {
		this.group = group;
	}
	public Supplier getSupplier() {
		return supplier;
	}
	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	@Override
	public int compareTo(Product p){
		return this.code.compareTo(p.getCode());
	}
	private void calculatePercentage(){
		double inc = this.sellprice - this.purchaseprice;
		if (inc > 0){
			this.percentage =inc*100/this.purchaseprice;
			BigDecimal bd = new BigDecimal(this.percentage);
	        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
	        this.percentage= bd.doubleValue();
		}else{
			this.percentage = 0;
		}
	}
	public void convertFromTable(Itbl obj){
		TblProduct pd = (TblProduct)obj;
		this.barcode = pd.getBarcode();
		this.code = pd.getCode();
		this.description = pd.getDescription();
		this.idProduct = pd.getIdProduct();
		this.manageserialnumber = pd.getManageserialnumber();
		this.purchaseprice = pd.getPurchaseprice();
		this.sellprice = pd.getSellprice();
		this.weightbarcode = pd.getWeightbarcode();
		if (pd.getCompany() != null){
			this.company = new Company();
			this.company.convertFromTable(pd.getCompany());
		}
		calculatePercentage();
		if(pd.getGroup()!= null){
			this.group = new GroupProduct();
			this.group.convertFromTable(pd.getGroup());
		}
		if (pd.getTaxrate()!= null){
			this.taxrate = new TaxRate();
			this.taxrate.convertFromTable(pd.getTaxrate());
		}
		if (pd.getCategory() != null){
			this.category = new CategoryProduct();
			this.category.convertFromTable(pd.getCategory());
		}
		if (pd.getSubcategory() != null){
			this.subcategory = new SubCategoryProduct();
			this.subcategory.convertFromTable(pd.getSubcategory());
		}
		if (pd.getBrand() != null){
			this.brand = new Brand();
			this.brand.convertFromTable(pd.getBrand());
		}
		if (pd.getSupplier() != null){
			this.supplier = new Supplier();
			this.supplier.convertFromTable(pd.getSupplier());
		}
		this.ums = new HashSet<UnitMeasureProduct>();
		if (pd.getUms() != null){
			for (Iterator<TblUnitMeasureProduct> iterator = pd.getUms().iterator(); iterator.hasNext();){
				TblUnitMeasureProduct ump = iterator.next();
				UnitMeasureProduct umpt = new UnitMeasureProduct();
				umpt.convertFromTable(ump);
				if (umpt.isPreference() == true){
					this.umselected = umpt.getUm();
				}
				this.ums.add(umpt);
			}
		}
	}
	public void convertFromTableForSaving(Itbl obj,Ivo vo){
		TblProduct pd = (TblProduct)obj;
		this.barcode = pd.getBarcode();
		this.code = pd.getCode();
		this.description = pd.getDescription();
		this.idProduct = pd.getIdProduct();
		this.manageserialnumber = pd.getManageserialnumber();
		this.purchaseprice = pd.getPurchaseprice();
		this.sellprice = pd.getSellprice();
		this.weightbarcode = pd.getWeightbarcode();
		if(pd.getGroup()!= null){
			this.group = new GroupProduct();
			this.group.convertFromTable(pd.getGroup());
		}
		if (pd.getTaxrate()!= null){
			this.taxrate = new TaxRate();
			this.taxrate.convertFromTable(pd.getTaxrate());
		}
		if (pd.getCategory() != null){
			this.category = new CategoryProduct();
			this.category.convertFromTable(pd.getCategory());
		}
		if (pd.getSubcategory() != null){
			this.subcategory = new SubCategoryProduct();
			this.subcategory.convertFromTable(pd.getSubcategory());
		}
		if (pd.getUms() != null){
			this.ums = new HashSet<UnitMeasureProduct>();
			for (Iterator<TblUnitMeasureProduct> iterator = pd.getUms().iterator(); iterator.hasNext();){
				TblUnitMeasureProduct ump = iterator.next();
				UnitMeasureProduct umpt = new UnitMeasureProduct();
				umpt.convertFromTable(ump);
				this.ums.add(umpt);
			}
		}
	}
	public GECOError control(){
		GECOError er = null;
		clean();
		if (this.supplier != null && this.supplier.getIdSupplier() == 0){
			this.supplier = null;
		}
		if (this.code == "" || this.code == null){
			er = new GECOError(GECOParameter.ERROR_VALUE_MISSING,"Codice Manacante");
		}
		if (this.description == "" || this.description == null){
			er = new GECOError(GECOParameter.ERROR_VALUE_MISSING,"Descrizione Manacante");
		}
		if (this.purchaseprice  == 0){
			er = new GECOError(GECOParameter.ERROR_VALUE_MISSING,"Prezzo di Acquisto mancante");
		}
		if (this.sellprice  == 0){
			er = new GECOError(GECOParameter.ERROR_VALUE_MISSING,"Prezzo di Vendita mancante");
		}
		if (this.group  == null || this.group.getIdGroupProduct() == 0){
			er = new GECOError(GECOParameter.ERROR_VALUE_MISSING,"Gruppo mancante");
		}
		if (this.category  == null || this.category.getIdCategoryProduct() == 0){
			er = new GECOError(GECOParameter.ERROR_VALUE_MISSING,"Categoria mancante");
		}
		if (this.taxrate  == null || this.taxrate.getIdtaxrate() == 0){
			er = new GECOError(GECOParameter.ERROR_VALUE_MISSING,"Aliquota mancante");
		}
		if (this.ums  == null || this.ums.size() == 0){
			er = new GECOError(GECOParameter.ERROR_VALUE_MISSING,"Unità di misura non definite");
		}else{
			boolean hasConv = true;
			boolean hasPreference = false;
			boolean hasBasicConv = false;
			boolean hasUm = true;
			boolean hasCode = true;
			boolean hasNoDuplicate = true;
			Map<String, String> codes = new HashMap<String, String>();
			for (Iterator<UnitMeasureProduct> it = ums.iterator();it.hasNext();){
				UnitMeasureProduct um = it.next();
				if (um.getConversion() == 0){
					hasConv = false;
				}
				if (um.isPreference() == true){
					hasPreference = true;
				}
				if (um.getConversion() == 1){
					hasBasicConv = true;
				}
				if (um.getUm() == null || um.getUm().getIdUnitMeasure() ==0){
					hasUm = false;
				}
				if (um.getCode() == null || um.getCode() ==  "" ){
					hasCode = false;
				}
				if (codes.containsKey(um.getCode()) == true){
					hasNoDuplicate = false;
				}else{
					codes.put(um.getCode(), um.getCode());
				}
			}
			if (hasConv == false){
				er = new GECOError(GECOParameter.ERROR_VALUE_MISSING,"Fattore di conversione mancante");
			}
			if (hasPreference == false){
				er = new GECOError(GECOParameter.ERROR_VALUE_MISSING,"Unità di misura preferenziale mancante");
			}
			if (hasBasicConv == false){
				er = new GECOError(GECOParameter.ERROR_VALUE_MISSING,"Fattore di conversione di base mancante");
			}
			if (hasUm == false){
				er = new GECOError(GECOParameter.ERROR_VALUE_MISSING,"Unità di misura mancante");
			}
			if (hasCode == false){
				if (hasPreference == true && hasBasicConv == true && ums.size() == 1){
					
				}else{
					er = new GECOError(GECOParameter.ERROR_VALUE_MISSING,"Codice mancante");
				}
			}
			if (hasNoDuplicate == false){
				er = new GECOError(GECOParameter.ERROR_VALUE_MISSING,"Codice duplicato");
			}
			
		}
		
		return er;
	}
	public void updateCode(){
		if (ums != null){
			for (Iterator<UnitMeasureProduct> it = ums.iterator();it.hasNext();){
				UnitMeasureProduct um = it.next();
				
				if (um.isPreference() == true){
					um.setCode(code);
					break;
				}
				
			}
		}
	}
	public UnitMeasureProduct getPreferenceUMP(){
		for(Iterator<UnitMeasureProduct> i = ums.iterator();i.hasNext();){
			UnitMeasureProduct u = i.next();
			if (u.isPreference() == true){
				return u;
			}
		}
		return null;
	}
	public double getConversion(UnitMeasure ump){
		for(Iterator<UnitMeasureProduct> i = ums.iterator();i.hasNext();){
			UnitMeasureProduct u = i.next();
			if (u.getUm().getIdUnitMeasure() == ump.getIdUnitMeasure()){
				return u.getConversion();
			}
		}
		return 0;
	}
	private void clean(){
		if (ums != null && ums.size() >0){
			for (Iterator<UnitMeasureProduct> it = ums.iterator();it.hasNext();){
				UnitMeasureProduct r = it.next();
				if (r.getUm() == null){
					ums.remove(r);
				}
			}
		}
	}
}
