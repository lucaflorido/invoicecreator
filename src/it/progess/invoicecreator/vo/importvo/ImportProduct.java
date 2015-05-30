package it.progess.invoicecreator.vo.importvo;

import java.util.Set;

import it.progess.invoicecreator.vo.GECOError;
import it.progess.invoicecreator.vo.GECOObject;
import it.progess.invoicecreator.vo.GECOSuccess;

public class ImportProduct {
	private String group;
	private String code;
	private String description;
	private String purchaseprice;
	private String umcode;
	private String taxrate;
	private String barcode;
	private String region;
	private String basicsellprice;
	private String filename;
	private int startIndex;
	private int endIndex;
	private Set<ImportProductList> lists;
	
	public Set<ImportProductList> getLists() {
		return lists;
	}
	public void setLists(Set<ImportProductList> lists) {
		this.lists = lists;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public int getStartIndex() {
		return startIndex;
	}
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}
	public int getEndIndex() {
		return endIndex;
	}
	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
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
	public String getPurchaseprice() {
		return purchaseprice;
	}
	public void setPurchaseprice(String purchaseprice) {
		this.purchaseprice = purchaseprice;
	}
	public String getUmcode() {
		return umcode;
	}
	public void setUmcode(String umcode) {
		this.umcode = umcode;
	}
	public String getTaxrate() {
		return taxrate;
	}
	public void setTaxrate(String taxrate) {
		this.taxrate = taxrate;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getBasicsellprice() {
		return basicsellprice;
	}
	public void setBasicsellprice(String basicsellprice) {
		this.basicsellprice = basicsellprice;
	}
	public GECOObject control(){
		if (this.filename == null || this.filename == ""){
			return new GECOError("", "nome del file non presente");
		}
		
		if (this.code == null || this.code == ""){
			return new GECOError("", "indicare la colonna per il codice prodotto");
		}
		if (this.description == null || this.description == ""){
			return new GECOError("", "indicare la colonna per la descrizione del prodotto");
		}
		if (this.group == null || this.group == ""){
			return new GECOError("", "indicare la colonna per il gruppo del prodotto");
		}
		if (this.taxrate == null || this.taxrate == ""){
			return new GECOError("", "indicare la colonna per l'aliquota");
		}
		if (this.umcode == null || this.umcode == ""){
			return new GECOError("", "indicare la colonna per l'unità di misura");
		}
		
		return null;
	}
}
