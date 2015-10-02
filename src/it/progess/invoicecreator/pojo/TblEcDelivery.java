package it.progess.invoicecreator.pojo;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import it.progess.invoicecreator.vo.DeliveryDeliveryCountry;
import it.progess.invoicecreator.vo.EcDelivery;
import it.progess.invoicecreator.vo.Ivo;
@Entity
@Table(name="tblecdelivery")
public class TblEcDelivery implements Itbl {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idecdelivery")
	private int idEcDelivery;
	@Column(name="default_country")
	private float default_country;
	@Column(name="bound_country")
	private float bound_country;
	@OneToMany(fetch = FetchType.EAGER,mappedBy = "ecdelivery",cascade = CascadeType.ALL)
	@Fetch(FetchMode.SELECT)
	private Set<TblDeliveryDeliveryCountry> deliverycountry;
	public int getIdEcDelivery() {
		return idEcDelivery;
	}
	public void setIdEcDelivery(int idEcDelivery) {
		this.idEcDelivery = idEcDelivery;
	}
	public float getDefault_country() {
		return default_country;
	}

	public void setDefault_country(float default_country) {
		this.default_country = default_country;
	}

	
	public float getBound_country() {
		return bound_country;
	}

	public void setBound_country(float bound_country) {
		this.bound_country = bound_country;
	}

	

	public Set<TblDeliveryDeliveryCountry> getDeliverycountry() {
		return deliverycountry;
	}

	public void setDeliverycountry(Set<TblDeliveryDeliveryCountry> deliverycountry) {
		this.deliverycountry = deliverycountry;
	}

	public void convertToTable(Ivo vo){
		EcDelivery ed = (EcDelivery)vo;
		this.bound_country = ed.getBound_country();
		this.default_country = ed.getDefault_country();
		this.idEcDelivery = ed.getIdEcDelivery();
		if (ed.getDeliverycountry() != null){
			this.deliverycountry = new HashSet<TblDeliveryDeliveryCountry>();
			for (Iterator<DeliveryDeliveryCountry> it = ed.getDeliverycountry().iterator(); it.hasNext();){
				DeliveryDeliveryCountry ddc = it.next();
				TblDeliveryDeliveryCountry tddc = new TblDeliveryDeliveryCountry();
				tddc.convertToTable(ddc);
				this.deliverycountry.add(tddc);
			}
		}
	}
	public void convertToTableSingle(Ivo vo){
		this.convertToTable(vo);
	}
	public void convertToTableToSave(Ivo vo){
		EcDelivery ed = (EcDelivery)vo;
		this.bound_country = ed.getBound_country();
		this.default_country = ed.getDefault_country();
		if (ed.getDeliverycountry() != null){
			this.deliverycountry = new HashSet<TblDeliveryDeliveryCountry>();
			for (Iterator<DeliveryDeliveryCountry> it = ed.getDeliverycountry().iterator(); it.hasNext();){
				DeliveryDeliveryCountry ddc = it.next();
				TblDeliveryDeliveryCountry tddc = new TblDeliveryDeliveryCountry();
				tddc.convertToTableForSaving(ddc);
				tddc.setEcdelivery(this);
				this.deliverycountry.add(tddc);
			}
		}
		this.idEcDelivery = ed.getIdEcDelivery();
		/*if (this.deliverycountry != null){
			for (Iterator<TblDeliveryDeliveryCountry> it = this.deliverycountry.iterator(); it.hasNext();){
				TblDeliveryDeliveryCountry ddc = it.next();
				ddc.setEcdelivery(this);
			}
		}*/
	}
}
