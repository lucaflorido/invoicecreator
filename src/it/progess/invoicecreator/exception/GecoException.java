package it.progess.invoicecreator.exception;

public class GecoException extends Exception {
	
	public String GECOtype;
	public String GECOmessage;
	public GecoException(){
		super();
	}
	public GecoException(String message){
		super();
		this.GECOmessage = message;
		this.GECOtype = "GECOERROR";
	}
}
