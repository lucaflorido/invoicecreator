package it.progess.invoicecreator.pojo;

import it.progess.invoicecreator.vo.Ivo;
import it.progess.invoicecreator.vo.ListCustomer;

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
@Table(name="tbllist_customer")
public class TblListCustomer  implements Itbl{
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idListCustomer")
	private int idListCustomer;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idList")
	private TblList list;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idCustomer")
	private TblCustomer customer;
	public int getIdListCustomer() {
		return idListCustomer;
	}
	public void setIdListCustomer(int idListCustomer) {
		this.idListCustomer = idListCustomer;
	}
	public TblList getList() {
		return list;
	}
	public void setList(TblList list) {
		this.list = list;
	}
	public TblCustomer getCustomer() {
		return customer;
	}
	public void setCustomer(TblCustomer customer) {
		this.customer = customer;
	}
	public void convertToTable(Ivo obj){
		ListCustomer lc = (ListCustomer)obj;
		this.idListCustomer = lc.getIdListCustomer();
		this.list = new TblList();
		if (lc.getList() != null){
			this.list.convertToTable(lc.getList());
		}
	}
	public void convertToTableForSaving(Ivo obj,Itbl itbl){
		ListCustomer lc = (ListCustomer)obj;
		this.idListCustomer = lc.getIdListCustomer();
		this.list = new TblList();
		if (lc.getList() != null){
			this.list.convertToTable(lc.getList());
		}
		this.customer = (TblCustomer)itbl;
	}
}
