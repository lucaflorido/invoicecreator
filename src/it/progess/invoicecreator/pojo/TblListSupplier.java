package it.progess.invoicecreator.pojo;

import it.progess.invoicecreator.vo.Ivo;
import it.progess.invoicecreator.vo.ListSupplier;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table(name="tbllist_supplier")
public class TblListSupplier implements Itbl {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idListSupplier")
	private int idListSupplier;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idList")
	private TblList list;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idSupplier")
	private TblSupplier supplier;
	public int getIdListSupplier() {
		return idListSupplier;
	}
	public void setIdListSupplier(int idListSupplier) {
		this.idListSupplier = idListSupplier;
	}
	public TblList getList() {
		return list;
	}
	public void setList(TblList list) {
		this.list = list;
	}
	public TblSupplier getSupplier() {
		return supplier;
	}
	public void setSupplier(TblSupplier supplier) {
		this.supplier = supplier;
	}
	public void convertToTable(Ivo obj){
		ListSupplier lc = (ListSupplier)obj;
		this.idListSupplier = lc.getIdListSupplier();
		this.list = new TblList();
		if (lc.getList() != null){
			this.list.convertToTable(lc.getList());
		}
	}
	public void convertToTableForSaving(Ivo obj,Itbl itbl){
		ListSupplier lc = (ListSupplier)obj;
		this.idListSupplier = lc.getIdListSupplier();
		this.list = new TblList();
		if (lc.getList() != null){
			this.list.convertToTable(lc.getList());
		}
		this.supplier = (TblSupplier)itbl;
	}
}
