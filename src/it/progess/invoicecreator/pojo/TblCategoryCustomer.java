package it.progess.invoicecreator.pojo;

import it.progess.invoicecreator.vo.CategoryCustomer;
import it.progess.invoicecreator.vo.Ivo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tblcategory_customer")
public class TblCategoryCustomer implements Itbl {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idCategoryCustomer")
	private int idCategoryCustomer;
	@Column(name="code")
	private String code;
	@Column(name="name")
	private String name;
	@Column(name="description")
	private String description;
	public int getIdCategoryCustomer() {
		return idCategoryCustomer;
	}
	public void setIdCategoryCustomer(int idCategoryCustomer) {
		this.idCategoryCustomer = idCategoryCustomer;
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
		CategoryCustomer cc = (CategoryCustomer)obj;
		this.code = cc.getCode();
		this.description = cc.getDescription();
		this.idCategoryCustomer = cc.getIdCategoryCustomer();
		this.name = cc.getName();
	}
}
