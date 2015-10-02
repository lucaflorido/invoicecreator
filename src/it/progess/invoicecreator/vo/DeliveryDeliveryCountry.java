package it.progess.invoicecreator.vo;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import it.progess.invoicecreator.pojo.Itbl;
import it.progess.invoicecreator.pojo.TblDeliveryCity;
import it.progess.invoicecreator.pojo.TblDeliveryDeliveryCountry;
import it.progess.invoicecreator.pojo.TblDeliveryZone;



public class DeliveryDeliveryCountry implements Ivo {
	private int idDeliveryDeliveryCountry;
	
	private EcDelivery ecdelivery;
	
	private DeliveryCountry deliverycountry;
	
	private Set<DeliveryZone> deliveryzones;
	
	private Set<DeliveryCity> deliverycities;
	
	public Set<DeliveryZone> getDeliveryzones() {
		return deliveryzones;
	}

	public void setDeliveryzones(Set<DeliveryZone> deliveryzones) {
		this.deliveryzones = deliveryzones;
	}

	public int getIdDeliveryDeliveryCountry() {
		return idDeliveryDeliveryCountry;
	}

	public void setIdDeliveryDeliveryCountry(int idDeliveryDeliveryCountry) {
		this.idDeliveryDeliveryCountry = idDeliveryDeliveryCountry;
	}

	public EcDelivery getEcdelivery() {
		return ecdelivery;
	}

	public void setEcdelivery(EcDelivery ecdelivery) {
		this.ecdelivery = ecdelivery;
	}

	public DeliveryCountry getDeliverycountry() {
		return deliverycountry;
	}

	public void setDeliverycountry(DeliveryCountry deliverycountry) {
		this.deliverycountry = deliverycountry;
	}

	
	public Set<DeliveryCity> getDeliverycities() {
		return deliverycities;
	}

	public void setDeliverycities(Set<DeliveryCity> deliverycities) {
		this.deliverycities = deliverycities;
	}

	public void convertFromTable(Itbl tb){
		TblDeliveryDeliveryCountry ddc = (TblDeliveryDeliveryCountry) tb;
		this.idDeliveryDeliveryCountry = ddc.getIdDeliveryDeliveryCountry();
		if (ddc.getDeliverycountry() != null){
			this.deliverycountry = new DeliveryCountry();
			this.deliverycountry.convertFromTable(ddc.getDeliverycountry());
		}
		/*if (ddc.getEcdelivery() != null){
			this.ecdelivery = new EcDelivery();
			this.ecdelivery.convertFromTable(ddc.getEcdelivery());
		}*/
		if (ddc.getDeliveryzones() != null){
			this.deliveryzones = new HashSet<DeliveryZone>();
			for(Iterator<TblDeliveryZone> it = ddc.getDeliveryzones().iterator();it.hasNext();){
				TblDeliveryZone tdz = it.next();
				DeliveryZone dz = new DeliveryZone();
				dz.convertFromTable(tdz);
				this.deliveryzones.add(dz);
			}
		}
		if (ddc.getDeliverycities() != null){
			this.deliverycities = new HashSet<DeliveryCity>();
			for(Iterator<TblDeliveryCity> itc = ddc.getDeliverycities().iterator();itc.hasNext();){
				TblDeliveryCity tdz = itc.next();
				DeliveryCity dz = new DeliveryCity();
				dz.convertFromTable(tdz);
				this.deliverycities.add(dz);
			}
		}
	}
	public GECOObject control(){
		return null;
	}
}
