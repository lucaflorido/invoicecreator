package it.progess.invoicecreator.vo;

import it.progess.invoicecreator.pojo.Itbl;
import it.progess.invoicecreator.pojo.TblCurrency;

public class Currency implements Ivo {
	private int idcurrency;
	private String code;
	private String name;
	public int getIdcurrency() {
		return idcurrency;
	}
	public void setIdcurrency(int idcurrency) {
		this.idcurrency = idcurrency;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void convertFromTable(Itbl tbl){
		TblCurrency c = (TblCurrency)tbl;
		this.idcurrency = c.getIdcurrency();
		this.code = c.getCode();
		this.name = c.getName();
	}
	public GECOObject control(){
		return null;
	}
}
