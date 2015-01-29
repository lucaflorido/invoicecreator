package it.progess.invoicecreator.pojo;

import it.progess.invoicecreator.vo.GroupCustomer;
import it.progess.invoicecreator.vo.Ivo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tblgroup_customer")
public class TblGroupCustomer  implements Itbl{
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idGroupCustomer")
	private int idGroupCustomer;
	@Column(name="code")
	private String code;
	@Column(name="name")
	private String name;
	@Column(name="description")
	private String description;
	public int getIdGroupCustomer() {
		return idGroupCustomer;
	}
	public void setIdGroupCustomer(int idGroupCustomer) {
		this.idGroupCustomer = idGroupCustomer;
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
		GroupCustomer gc = (GroupCustomer) obj;
		this.code = gc.getCode();
		this.description = gc.getDescription();
		this.idGroupCustomer = gc.getIdGroupCustomer();
		this.name = gc.getName();
	}
}
