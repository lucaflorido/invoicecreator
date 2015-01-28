package it.progess.invoicecreator.vo;

import it.progess.invoicecreator.properties.GECOParameter;

public class GECOSuccess extends GECOObject  {
	public GECOSuccess(){
		this.type = GECOParameter.SUCCESS_TYPE;
	}
	public GECOSuccess(Object obj){
		this.success = obj;
		this.type = GECOParameter.SUCCESS_TYPE;
	}
	public Object success;
}
