package it.progess.invoicecreator.vo;

import it.progess.invoicecreator.pojo.Itbl;
import it.progess.invoicecreator.pojo.TblPaymentSolution;

public class PaymentSolution implements Ivo{
	private int idPaymentSolution;
	private String name;
	private boolean online;
	private String code;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public boolean isOnline() {
		return online;
	}
	public void setOnline(boolean online) {
		this.online = online;
	}
	public int getIdPaymentSolution() {
		return idPaymentSolution;
	}
	public void setIdPaymentSolution(int idPaymentSolution) {
		this.idPaymentSolution = idPaymentSolution;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void convertFromTable(Itbl t){
		TblPaymentSolution ps =  (TblPaymentSolution)t;
		this.idPaymentSolution = ps.getIdPaymentSolution();
		this.name = ps.getName();
		this.online = ps.isOnline();
		this.code = ps.getCode();
	}
	public GECOObject control(){
		return null;
	}
}
