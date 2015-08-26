package it.progess.invoicecreator.pojo;

import it.progess.invoicecreator.vo.Ivo;
import it.progess.invoicecreator.vo.ProductEcConfig;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name="tblproduct_ec_config")
public class TblProductEcConfig  implements Itbl {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idproduct_ec_config")
	private int idProductEcConfig;
	@Column(name="deliverydays")
	private int deliverydays;
	@Column(name="orderdays")
	private int orderdays;
	@Column(name="quantitymin")
	private int quantitymin;
	@Column(name="quantitymax")
	private int quantitymax;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idUnitMisureProduct")
	@Fetch(FetchMode.SELECT)
	private TblUnitMeasureProduct umproduct;
	public int getIdProductEcConfig() {
		return idProductEcConfig;
	}
	public void setIdProductEcConfig(int idProductEcConfig) {
		this.idProductEcConfig = idProductEcConfig;
	}
	public int getDeliverydays() {
		return deliverydays;
	}
	public void setDeliverydays(int deliverydays) {
		this.deliverydays = deliverydays;
	}
	public int getOrderdays() {
		return orderdays;
	}
	public void setOrderdays(int orderdays) {
		this.orderdays = orderdays;
	}
	public int getQuantitymin() {
		return quantitymin;
	}
	public void setQuantitymin(int quantitymin) {
		this.quantitymin = quantitymin;
	}
	public int getQuantitymax() {
		return quantitymax;
	}
	public void setQuantitymax(int quantitymax) {
		this.quantitymax = quantitymax;
	}
	public TblUnitMeasureProduct getUmproduct() {
		return umproduct;
	}
	public void setUmproduct(TblUnitMeasureProduct umproduct) {
		this.umproduct = umproduct;
	}
	public void convertToTable(Ivo obj){
		ProductEcConfig pec = (ProductEcConfig)obj;
		this.deliverydays = pec.getDeliverydays();
		this.idProductEcConfig = pec.getIdProductEcConfig();
		this.orderdays = pec.getOrderdays();
		this.quantitymax = pec.getQuantitymax();
		this.quantitymin = pec.getQuantitymin();
		if (pec.getUmproduct() != null){
			this.umproduct = new TblUnitMeasureProduct();
			this.umproduct.convertToTable(pec.getUmproduct());
		}
	}
}
