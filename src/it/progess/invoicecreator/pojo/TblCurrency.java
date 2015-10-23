package it.progess.invoicecreator.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import it.progess.invoicecreator.vo.Currency;
import it.progess.invoicecreator.vo.Ivo;
@Entity
@Table(name="tblcurrency")
public class TblCurrency implements Itbl {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idcurrency")
	private int idcurrency;
	@Column(name="code")
	private String code;
	@Column(name="name")
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
	public void convertToTable(Ivo vo){
		Currency c = (Currency)vo;
		this.idcurrency = c.getIdcurrency();
		this.code = c.getCode();
		this.name = c.getName();
	}
}
