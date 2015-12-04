package it.progess.invoicecreator.vo;

import it.progess.invoicecreator.pojo.Itbl;
import it.progess.invoicecreator.pojo.TblCompositionProduct;

public class CompositionProduct implements Ivo {
	private int idCompositionProduct;
	private Product product;
	private Composition composition;
	public int getIdCompositionProduct() {
		return idCompositionProduct;
	}
	public void setIdCompositionProduct(int idCompositionProduct) {
		this.idCompositionProduct = idCompositionProduct;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Composition getComposition() {
		return composition;
	}
	public void setComposition(Composition composition) {
		this.composition = composition;
	}
	public void convertFromTable(Itbl tbl){
		TblCompositionProduct cp = (TblCompositionProduct)tbl;
		this.idCompositionProduct = cp.getIdCompositionProduct();
		if (cp.getComposition() != null){
			this.composition = new Composition();
			this.composition.convertFromTable(cp.getComposition());
		}
		
	}
	public GECOObject control(){
		return null;
	}
}
