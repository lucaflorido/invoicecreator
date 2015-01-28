package it.progess.invoicecreator.pojo;


import it.progess.invoicecreator.vo.CategorySupplier;
import it.progess.invoicecreator.vo.Ivo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="tblcategory_supplier")
public class TblCategorySupplier implements Itbl {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idCategorySupplier")
	private int idCategorySupplier;
	@Column(name="code")
	private String code;
	@Column(name="name")
	private String name;
	@Column(name="description")
	private String description;
	public int getIdCategorySupplier() {
		return idCategorySupplier;
	}
	public void setIdCategorySupplier(int idCategorySupplier) {
		this.idCategorySupplier = idCategorySupplier;
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
	public void convertToTable(Ivo obj){
		CategorySupplier cc = (CategorySupplier)obj;
		this.code = cc.getCode();
		this.description = cc.getDescription();
		this.idCategorySupplier = cc.getIdCategorySupplier();
		this.name = cc.getName();
	}
}
