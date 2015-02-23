package it.progess.invoicecreator.vo;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;



import it.progess.invoicecreator.hibernate.DataUtilConverter;
import it.progess.invoicecreator.pojo.Itbl;
import it.progess.invoicecreator.pojo.TblCompany;
import it.progess.invoicecreator.pojo.TblList;
import it.progess.invoicecreator.pojo.TblListProduct;
import it.progess.invoicecreator.properties.GECOParameter;

public class List implements Ivo {
	private int idList;
	private String code;
	private String description;
	private String name;
	private String startdate;
	private Set<ListProduct> listproduct;
	private boolean active;
	private boolean fill;
	private float increment;
	private boolean isPercentage;
	
	public float getIncrement() {
		return increment;
	}
	public void setIncrement(float increment) {
		this.increment = increment;
	}
	public boolean isPercentage() {
		return isPercentage;
	}
	public void setPercentage(boolean isPercentage) {
		this.isPercentage = isPercentage;
	}
	public boolean isFill() {
		return fill;
	}
	public void setFill(boolean fill) {
		this.fill = fill;
	}
	private Company company;
	
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public int getIdList() {
		return idList;
	}
	public void setIdList(int idList) {
		this.idList = idList;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public Set<ListProduct> getListproduct() {
		return listproduct;
	}
	public void setListproduct(Set<ListProduct> listproduct) {
		this.listproduct = listproduct;
	}
	public void convertFromTable(Itbl obj){
		TblList lt = (TblList)obj;
		this.code = lt.getCode();
		this.description = lt.getDescription();
		this.idList = lt.getIdList();
		this.name  = lt.getName();
		this.active = lt.isActive();
		this.isPercentage = lt.isPercentage();
		this.increment = lt.getIncrement();
		this.startdate =  DataUtilConverter.convertStringFromDate( lt.getStartdate());
		if (lt.getCompany() != null){
			this.company = new Company();
			this.company.convertFromTable(lt.getCompany());
		}
	}
	public void copy(List lt){
		this.code = lt.getCode();
		this.description = lt.getDescription();
		this.name  = lt.getName();
	}
	public void convertFromTableSingle(Itbl obj){
		TblList lt = (TblList)obj;
		this.code = lt.getCode();
		this.description = lt.getDescription();
		this.idList = lt.getIdList();
		this.name  = lt.getName();
		this.active = lt.isActive();
		this.isPercentage = lt.isPercentage();
		this.increment = lt.getIncrement();
		this.startdate = DataUtilConverter.convertStringFromDate( lt.getStartdate());
		this.listproduct = new HashSet<ListProduct>();
		if (lt.getCompany() != null){
			this.company = new Company();
			this.company.convertFromTable(lt.getCompany());
		}
		if (lt.getListproduct() != null){
			for (Iterator<TblListProduct> iterator = lt.getListproduct().iterator(); iterator.hasNext();){
				TblListProduct listproduct = iterator.next();
				if (listproduct.isActive() == true){
					ListProduct listp = new ListProduct();
					listp.convertFromTable(listproduct);
					this.listproduct.add(listp);
				}
			}
		}
		
	}
	public void copyProducts(List listToCopy,boolean newVersion,boolean isPercentage,float increment){
		if (newVersion == true){
			if (this.getListproduct() != null){
				HashSet<ListProduct> listNewProd = new HashSet<ListProduct>();
				for (Iterator<ListProduct> iteratorNew = this.getListproduct().iterator(); iteratorNew.hasNext();){
					ListProduct listproduct = iteratorNew.next();
					listproduct.setActive(false);
					ListProduct newListProduct = new ListProduct();
					newListProduct.setActive(true);
					newListProduct.setProduct(listproduct.getProduct());
					newListProduct.setStartdate(listToCopy.getStartdate());
					if (increment >=0){
						if (isPercentage == false){
							newListProduct.setPrice(listproduct.getPrice() + increment);
						}else{
							float incrementPrice = 0;
							incrementPrice = listproduct.getPrice()/100*increment;
							newListProduct.setPrice(listproduct.getPrice() + incrementPrice);
						}
					}
					listNewProd.add(newListProduct);
					
				}
				this.listproduct.addAll(listNewProd);
			}
		
		}else{
			if (this.getListproduct() != null){
				for (Iterator<ListProduct> iterator = this.getListproduct().iterator(); iterator.hasNext();){
					ListProduct listproduct = iterator.next();
					if (increment >=0){
						if (isPercentage == false){
							listproduct.setPrice(listproduct.getPrice() + increment);
						}else{
							float incrementPrice = 0;
							incrementPrice = listproduct.getPrice()/100*increment;
							listproduct.setPrice(listproduct.getPrice() + incrementPrice);
						}
					}
					
				}
			}
		}
	}
	public GECOObject control(){
		if (this.code == null || this.code.equals("") == true){
			return new GECOError(GECOParameter.ERROR_VALUE_MISSING,"Codice Mancante");
		}
		if (this.name == null || this.name.equals("") == true){
			return new GECOError(GECOParameter.ERROR_VALUE_MISSING,"Nome Mancante");
		}
		if ((this.startdate == null || this.startdate.equals("") == true) && this.idList != 0 ){
			return new GECOError(GECOParameter.ERROR_VALUE_MISSING,"Data validità Mancante");
		}
		return null;
	}
}
