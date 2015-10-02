package it.progess.invoicecreator.vo;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import it.progess.invoicecreator.pojo.Itbl;
import it.progess.invoicecreator.pojo.TblDeliveryDeliveryCountry;
import it.progess.invoicecreator.pojo.TblEcDelivery;



public class EcDelivery implements Ivo {
	private int idEcDelivery;
	private float default_country;
	private float bound_country;
	private Set<DeliveryDeliveryCountry> deliverycountry;
	
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

	
	public Set<DeliveryDeliveryCountry> getDeliverycountry() {
		return deliverycountry;
	}

	public void setDeliverycountry(Set<DeliveryDeliveryCountry> deliverycountry) {
		this.deliverycountry = deliverycountry;
	}

	public void convertFromTable(Itbl tbl){
		TblEcDelivery ed = (TblEcDelivery)tbl;
		this.bound_country = ed.getBound_country();
		this.default_country = ed.getDefault_country();
		this.idEcDelivery = ed.getIdEcDelivery();
		if (ed.getDeliverycountry() != null){
			this.deliverycountry = new HashSet<DeliveryDeliveryCountry>();
			for (Iterator<TblDeliveryDeliveryCountry> it = ed.getDeliverycountry().iterator(); it.hasNext();){
				TblDeliveryDeliveryCountry tddc = it.next();
				DeliveryDeliveryCountry ddc = new DeliveryDeliveryCountry();
				ddc.convertFromTable(tddc);
				this.deliverycountry.add(ddc);
			}
		}
	}
	
	public GECOObject control(){
		return null;
	}
}
