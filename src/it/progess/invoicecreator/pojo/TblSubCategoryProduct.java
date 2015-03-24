package it.progess.invoicecreator.pojo;

import it.progess.invoicecreator.vo.Ivo;
import it.progess.invoicecreator.vo.SubCategoryProduct;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="tblsubcategory_product")
public class TblSubCategoryProduct implements Itbl {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idSubCategory_Product")
	private int idSubCategoryProduct;
	@Column(name="code")
	private String code;
	@Column(name="name")
	private String name;
	@Column(name="description")
	private String description;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idCategoryProduct")
	private TblCategoryProduct categoryproduct;
	public TblCategoryProduct getCategoryproduct() {
		return categoryproduct;
	}
	public void setCategoryproduct(TblCategoryProduct categoryproduct) {
		this.categoryproduct = categoryproduct;
	}
	public int getIdSubCategoryProduct() {
		return idSubCategoryProduct;
	}
	public void setIdSubCategoryProduct(int idSubCategoryProduct) {
		this.idSubCategoryProduct = idSubCategoryProduct;
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
		SubCategoryProduct scp = (SubCategoryProduct)obj;
		this.code = scp.getCode();
		this.description = scp.getDescription();
		this.idSubCategoryProduct = scp.getIdSubCategoryProduct();
		this.name = scp.getName();
	}
	public void convertToTableForSaving(Ivo obj,Itbl tbl){
		SubCategoryProduct scp = (SubCategoryProduct)obj;
		this.code = scp.getCode();
		this.description = scp.getDescription();
		this.idSubCategoryProduct = scp.getIdSubCategoryProduct();
		this.categoryproduct = (TblCategoryProduct)tbl;
		this.name = scp.getName();
	}
}
