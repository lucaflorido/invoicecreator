package it.progess.invoicecreator.vo;

import it.progess.invoicecreator.properties.GECOParameter;

public class GECOError  extends GECOObject {
	private String errorName;
	private String errorMessage;
	public GECOError(){
		this.type = GECOParameter.ERROR_TYPE;
	}
	public GECOError(String name,String message){
		this.errorMessage = message;
		this.errorName = name;
		this.type = GECOParameter.ERROR_TYPE;
	}
	public String getErrorName() {
		return errorName;
	}
	public void setErrorName(String errorName) {
		this.errorName = errorName;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
