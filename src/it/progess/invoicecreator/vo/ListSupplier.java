package it.progess.invoicecreator.vo;

import it.progess.invoicecreator.pojo.Itbl;
import it.progess.invoicecreator.pojo.TblListSupplier;
import it.progess.invoicecreator.properties.GECOParameter;

public class ListSupplier implements Ivo {
	private int idListSupplier;
	private List list;
	private Supplier supplier;
	public int getIdListSupplier() {
		return idListSupplier;
	}
	public void setIdListSupplier(int idListSupplier) {
		this.idListSupplier = idListSupplier;
	}
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
	public Supplier getSupplier() {
		return supplier;
	}
	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	public void convertFromTable(Itbl obj){
		TblListSupplier lc = (TblListSupplier)obj;
		this.idListSupplier = lc.getIdListSupplier();
		if (lc.getSupplier() != null){
			this.supplier = new Supplier();
			this.supplier.convertFromTable(lc.getSupplier());
		}
		if (lc.getList() != null){
			this.list = new List();
			this.list.convertFromTable(lc.getList());
		}
	}
	public GECOObject control(){
		return null;
	}
}
