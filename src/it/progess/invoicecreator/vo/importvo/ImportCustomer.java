package it.progess.invoicecreator.vo.importvo;

import it.progess.invoicecreator.vo.GECOObject;

public class ImportCustomer {
	private String colCode;
	private String colName;
	private String colAlternativecode1;
	private String colAlternativecode2;
	private String colTaxcode;
	private String colSerialnumber;
	private String colStreet;
	private String colCity;
	private String colZone;
	private String colZipcode;
	private String colList;
	private String colPromoter;
	private String colPhone;
	private String colMobile;
	private String colEmail;
	private String colCommission;
	private String filename;
	private int startIndex;
	private int endIndex;
	public String getColCode() {
		return colCode;
	}
	public void setColCode(String colCode) {
		this.colCode = colCode;
	}
	public String getColName() {
		return colName;
	}
	public void setColName(String colName) {
		this.colName = colName;
	}
	public String getColAlternativecode1() {
		return colAlternativecode1;
	}
	public void setColAlternativecode1(String colAlternativecode1) {
		this.colAlternativecode1 = colAlternativecode1;
	}
	public String getColAlternativecode2() {
		return colAlternativecode2;
	}
	public void setColAlternativecode2(String colAlternativecode2) {
		this.colAlternativecode2 = colAlternativecode2;
	}
	public String getColTaxcode() {
		return colTaxcode;
	}
	public void setColTaxcode(String colTaxcode) {
		this.colTaxcode = colTaxcode;
	}
	public String getColSerialnumber() {
		return colSerialnumber;
	}
	public void setColSerialnumber(String colSerialnumber) {
		this.colSerialnumber = colSerialnumber;
	}
	
	public String getColCity() {
		return colCity;
	}
	public void setColCity(String colCity) {
		this.colCity = colCity;
	}
	public String getColZone() {
		return colZone;
	}
	public void setColZone(String colZone) {
		this.colZone = colZone;
	}
	public String getColZipcode() {
		return colZipcode;
	}
	public void setColZipcode(String colZipcode) {
		this.colZipcode = colZipcode;
	}
	
	public String getColList() {
		return colList;
	}
	public void setColList(String colList) {
		this.colList = colList;
	}
	public String getColPromoter() {
		return colPromoter;
	}
	public void setColPromoter(String colPromoter) {
		this.colPromoter = colPromoter;
	}
	public String getColPhone() {
		return colPhone;
	}
	public void setColPhone(String colPhone) {
		this.colPhone = colPhone;
	}
	public String getColMobile() {
		return colMobile;
	}
	public void setColMobile(String colMobile) {
		this.colMobile = colMobile;
	}
	public String getColEmail() {
		return colEmail;
	}
	public void setColEmail(String colEmail) {
		this.colEmail = colEmail;
	}
	public String getColCommission() {
		return colCommission;
	}
	public void setColCommission(String colCommission) {
		this.colCommission = colCommission;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	public String getColStreet() {
		return colStreet;
	}
	public void setColStreet(String colStreet) {
		this.colStreet = colStreet;
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
	public GECOObject control(){
		return null;
	}
}
