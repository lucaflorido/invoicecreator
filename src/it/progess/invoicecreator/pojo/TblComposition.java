package it.progess.invoicecreator.pojo;

import it.progess.invoicecreator.vo.Composition;
import it.progess.invoicecreator.vo.Ivo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="tblcomposition")
public class TblComposition implements Itbl {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idcomposition")
	private int idComposition;
	@Column(name="code")
	private String code;
	@Column(name="name")
	private String name;
	@Column(name="description")
	private String description;
	@ManyToOne
	@JoinColumn(name = "idcompany")
	private TblCompany company;
	
	public TblCompany getCompany() {
		return company;
	}
	public void setCompany(TblCompany company) {
		this.company = company;
	}
	public int getIdComposition() {
		return idComposition;
	}
	public void setIdComposition(int idComposition) {
		this.idComposition = idComposition;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void convertToTable(Ivo vo){
		Composition c = (Composition)vo;
		this.idComposition = c.getIdComposition();
		this.code = c.getCode();
		this.name = c.getName();
		this.description = c.getDescription();
		if (c.getCompany() != null){
			this.company = new TblCompany();
			this.company.convertToTable(c.getCompany());
		}
	}
}
