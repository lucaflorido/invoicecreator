package it.progess.invoicecreator.pojo;


import it.progess.invoicecreator.vo.CompositionProduct;
import it.progess.invoicecreator.vo.Ivo;



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
@Table(name="tblcomposition_product")
public class TblCompositionProduct implements Itbl {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idcomposition_product")
	private int idCompositionProduct;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idproduct")
	private TblProduct product;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idcomposition")
	private TblComposition composition;
	public int getIdCompositionProduct() {
		return idCompositionProduct;
	}
	public void setIdCompositionProduct(int idCompositionProduct) {
		this.idCompositionProduct = idCompositionProduct;
	}
	public TblProduct getProduct() {
		return product;
	}
	public void setProduct(TblProduct product) {
		this.product = product;
	}
	public TblComposition getComposition() {
		return composition;
	}
	public void setComposition(TblComposition composition) {
		this.composition = composition;
	}
	public void convertToTable(Ivo vo){
		CompositionProduct cp = (CompositionProduct)vo;
		this.idCompositionProduct = cp.getIdCompositionProduct();
		if (cp.getComposition() != null){
			this.composition = new TblComposition();
			this.composition.convertToTable(cp.getComposition());
		}
		/*if (cp.getProduct() != null){
			this.product = new TblProduct();
			this.product.convertToTable(cp.getProduct());
		}*/
	}
}
