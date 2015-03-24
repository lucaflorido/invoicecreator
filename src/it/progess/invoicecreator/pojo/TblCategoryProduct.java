package it.progess.invoicecreator.pojo;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import it.progess.invoicecreator.vo.CategoryProduct;
import it.progess.invoicecreator.vo.Ivo;
import it.progess.invoicecreator.vo.SubCategoryProduct;

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
@Table(name="tblcategory_product")
public class TblCategoryProduct implements Itbl {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idCategoryProduct")
	private int idCategoryProduct;
	@Column(name="code")
	private String code;
	@Column(name="name")
	private String name;
	@Column(name="description")
	private String description;
	@Column(name="note")
	private String note;
	@OneToMany(fetch= FetchType.LAZY,mappedBy = "categoryproduct",cascade = CascadeType.ALL)
	private Set<TblSubCategoryProduct> subcategories;
	@ManyToOne
	@JoinColumn(name = "idCompany")
	private TblCompany company;
	public TblCompany getCompany() {
		return company;
	}
	public void setCompany(TblCompany company) {
		this.company = company;
	}
	public Set<TblSubCategoryProduct> getSubcategories() {
		return subcategories;
	}
	public void setSubcategories(Set<TblSubCategoryProduct> subcategories) {
		this.subcategories = subcategories;
	}
	public int getIdCategoryProduct() {
		return idCategoryProduct;
	}
	public void setIdCategoryProduct(int idCategoryProduct) {
		this.idCategoryProduct = idCategoryProduct;
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
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public void convertToTable(Ivo obj){
		CategoryProduct cp = (CategoryProduct)obj;
		this.code = cp.getCode();
		this.description = cp.getDescription();
		this.idCategoryProduct = cp.getIdCategoryProduct();
		this.name = cp.getName();
		this.note = cp.getNote();
		if (cp.getSubcategories() != null){
			this.subcategories = new HashSet<TblSubCategoryProduct>();
			for (Iterator<SubCategoryProduct> iterator = cp.getSubcategories().iterator(); iterator.hasNext();){
				SubCategoryProduct subcategoryproduct = iterator.next();
				TblSubCategoryProduct subc = new TblSubCategoryProduct();
				subc.convertToTable(subcategoryproduct);
				this.subcategories.add(subc);
			}
		}
	}
	public void convertToTableForSaving(Ivo obj){
		CategoryProduct cp = (CategoryProduct)obj;
		this.code = cp.getCode();
		this.description = cp.getDescription();
		this.idCategoryProduct = cp.getIdCategoryProduct();
		this.name = cp.getName();
		this.note = cp.getNote();
		if (cp.getSubcategories() != null){
			this.subcategories = new HashSet<TblSubCategoryProduct>();
			for (Iterator<SubCategoryProduct> iterator = cp.getSubcategories().iterator(); iterator.hasNext();){
				SubCategoryProduct subcategoryproduct = iterator.next();
				TblSubCategoryProduct subc = new TblSubCategoryProduct();
				subc.convertToTableForSaving(subcategoryproduct,this);
				this.subcategories.add(subc);
			}
		}
	}
}
