package it.progess.invoicecreator.pojo;

import it.progess.invoicecreator.vo.GroupSupplier;
import it.progess.invoicecreator.vo.Ivo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="tblgroup_supplier")
public class TblGroupSupplier implements Itbl {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idGroupSupplier")
	private int idGroupSupplier;
	@Column(name="code")
	private String code;
	@Column(name="name")
	private String name;
	@Column(name="description")
	private String description;
	public int getIdGroupSupplier() {
		return idGroupSupplier;
	}
	public void setIdGroupSupplier(int idGroupSupplier) {
		this.idGroupSupplier = idGroupSupplier;
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
		GroupSupplier gc = (GroupSupplier) obj;
		this.code = gc.getCode();
		this.description = gc.getDescription();
		this.idGroupSupplier = gc.getIdGroupSupplier();
		this.name = gc.getName();
	}

}
