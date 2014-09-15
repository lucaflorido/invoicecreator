package it.progess.invoicecreator.pojo;

import it.progess.invoicecreator.vo.Ivo;
import it.progess.invoicecreator.vo.UnitMeasureProduct;

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
@Table(name="tblunitmeasure_product")
public class TblUnitMeasureProduct implements Itbl {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idUnitMeasure_Product")
	private int idUnitMeasureProduct;
	@Column(name="preference")
	private boolean preference;
	@Column(name="conversion")
	private float conversion;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idProduct")
	private TblProduct product;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idUnitMeasure")
	private TblUnitMeasure um;
	@Column(name="code")
	private String code;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public TblProduct getProduct() {
		return product;
	}
	public void setProduct(TblProduct product) {
		this.product = product;
	}
	public int getIdUnitMeasureProduct() {
		return idUnitMeasureProduct;
	}
	public void setIdUnitMeasureProduct(int idUnitMeasureProduct) {
		this.idUnitMeasureProduct = idUnitMeasureProduct;
	}
	public boolean isPreference() {
		return preference;
	}
	public void setPreference(boolean preference) {
		this.preference = preference;
	}
	public float getConversion() {
		return conversion;
	}
	public void setConversion(float conversion) {
		this.conversion = conversion;
	}
	public TblUnitMeasure getUm() {
		return um;
	}
	public void setUm(TblUnitMeasure um) {
		this.um = um;
	}
	public void convertToTable(Ivo obj){
		UnitMeasureProduct um = (UnitMeasureProduct)obj;
		this.conversion = um.getConversion();
		this.idUnitMeasureProduct = um.getIdUnitMeasureProduct();
		this.preference = um.isPreference();
		this.code = um.getCode();
		if (um.getUm() != null){
			this.um = new TblUnitMeasure();
			this.um.convertToTable(um.getUm());
		}
	}
	public void convertToTableForSaving(Ivo obj,Itbl itbl){
		UnitMeasureProduct um = (UnitMeasureProduct)obj;
		this.conversion = um.getConversion();
		this.idUnitMeasureProduct = um.getIdUnitMeasureProduct();
		this.preference = um.isPreference();
		this.code = um.getCode();
		if (um.getUm() != null){
			this.um = new TblUnitMeasure();
			this.um.convertToTable(um.getUm());
		}
		this.product = (TblProduct)itbl;
	}
}
