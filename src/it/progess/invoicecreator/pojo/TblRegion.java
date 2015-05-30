package it.progess.invoicecreator.pojo;

import it.progess.invoicecreator.vo.Ivo;
import it.progess.invoicecreator.vo.Region;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONE)
@Table(name="tblregion")
public class TblRegion implements Itbl {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idRegion")
	private int idRegion;
	@Column(name="description")
	private String description;
	@Column(name="name")
	private String name;
	@Column(name="code")
	private String code;
	@ManyToOne
	@JoinColumn(name = "idCompany")
	private TblCompany company;
	public TblCompany getCompany() {
		return company;
	}
	public void setCompany(TblCompany company) {
		this.company = company;
	}
	
	public int getIdRegion() {
		return idRegion;
	}
	public void setIdRegion(int idRegion) {
		this.idRegion = idRegion;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public void convertToTable(Ivo obj) {
		Region re = (Region)obj;
		this.idRegion = re.getIdRegion();
		this.description = re.getDescription();
		this.code = re.getCode();
		this.name = re.getName();
		if (re.getCompany() != null){
			this.company = new TblCompany();
			this.company.convertToTable(re.getCompany());
		}
	}
}
