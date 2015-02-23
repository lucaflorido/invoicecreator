package it.progess.invoicecreator.pojo;

import it.progess.invoicecreator.hibernate.DataUtilConverter;
import it.progess.invoicecreator.vo.Ivo;
import it.progess.invoicecreator.vo.List;
import it.progess.invoicecreator.vo.ListProduct;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="tbllist")
public class TblList implements Itbl {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idList")
	private int idList;
	@Column(name="code")
	private String code;
	@Column(name="description")
	private String description;
	@Column(name="name")
	private String name;
	@OneToMany(fetch= FetchType.LAZY,mappedBy = "list",cascade = CascadeType.ALL)
	private Set<TblListProduct> listproduct;
	@Column(name="startdate")
	private Date startdate;
	@Column(name="active")
	private boolean active;
	@Column(name="isPercentage")
	private boolean isPercentage;
	@ManyToOne
	@JoinColumn(name = "idCompany")
	private TblCompany company;
	@Column(name="increment")
	private float increment;
	
	public float getIncrement() {
		return increment;
	}
	public void setIncrement(float increment) {
		this.increment = increment;
	}
	public boolean isPercentage() {
		return isPercentage;
	}
	public void setPercentage(boolean isPercentage) {
		this.isPercentage = isPercentage;
	}
	public TblCompany getCompany() {
		return company;
	}
	public void setCompany(TblCompany company) {
		this.company = company;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public int getIdList() {
		return idList;
	}
	public void setIdList(int idList) {
		this.idList = idList;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getStartdate() {
		return startdate;
	}
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	public Set<TblListProduct> getListproduct() {
		return listproduct;
	}
	public void setListproduct(Set<TblListProduct> listproduct) {
		this.listproduct = listproduct;
	}
	public void convertToTable(Ivo obj){
		List lt = (List)obj;
		this.code = lt.getCode();
		this.description = lt.getDescription();
		this.idList = lt.getIdList();
		this.name  = lt.getName();
		this.isPercentage = lt.isPercentage();
		this.increment = lt.getIncrement();
		this.startdate = DataUtilConverter.convertDateFromString(lt.getStartdate());
		if (lt.getCompany() != null){
			this.company = new TblCompany();
			this.company.convertToTable(lt.getCompany());
		}
	}
	public void convertToTableSingle(Ivo obj){
		List lt = (List)obj;
		this.code = lt.getCode();
		this.description = lt.getDescription();
		this.idList = lt.getIdList();
		this.name  = lt.getName();
		this.startdate = DataUtilConverter.convertDateFromString(lt.getStartdate());
		this.active = lt.isActive();
		this.isPercentage = lt.isPercentage();
		this.increment = lt.getIncrement();
		this.listproduct = new HashSet<TblListProduct>();
		if (lt.getCompany() != null){
			this.company = new TblCompany();
			this.company.convertToTable(lt.getCompany());
		}
		if (lt.getListproduct() != null){
			for (Iterator<ListProduct> iterator = lt.getListproduct().iterator(); iterator.hasNext();){
				ListProduct listproduct = iterator.next();
				TblListProduct listp = new TblListProduct();
				listp.convertToTable(listproduct);
				this.listproduct.add(listp);
			}
		}
	}
	public void convertToTableForSaving(Ivo obj){
		List lt = (List)obj;
		this.code = lt.getCode();
		this.description = lt.getDescription();
		this.idList = lt.getIdList();
		this.name  = lt.getName();
		this.active = lt.isActive();
		this.startdate = DataUtilConverter.convertDateFromString(lt.getStartdate());
		this.listproduct = new HashSet<TblListProduct>();
		this.isPercentage = lt.isPercentage();
		this.increment = lt.getIncrement();
		if (lt.getListproduct() != null){
			for (Iterator<ListProduct> iterator = lt.getListproduct().iterator(); iterator.hasNext();){
				ListProduct listproduct = iterator.next();
				TblListProduct listp = new TblListProduct();
				listp.convertToTableForSaving(listproduct,this);
				/*if (listp.getStartdate() == null){
					listp.setStartdate(this.startdate);
					listp.setActive(true);
				}*/
				this.listproduct.add(listp);
			}
		}
		if (lt.getCompany() != null){
			this.company = new TblCompany();
			this.company.convertToTable(lt.getCompany());
		}
	}
}
