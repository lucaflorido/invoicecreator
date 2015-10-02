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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;








import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import it.progess.invoicecreator.vo.DeliveryCity;
import it.progess.invoicecreator.vo.DeliveryCountry;
import it.progess.invoicecreator.vo.DeliveryDeliveryCountry;
import it.progess.invoicecreator.vo.DeliveryZone;
import it.progess.invoicecreator.vo.EcDelivery;
import it.progess.invoicecreator.vo.Ivo;
@Entity
@Table(name="tbldelivery_deliverycountry")
public class TblDeliveryDeliveryCountry implements Itbl {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="iddeliverydeliverycountry")
	private int idDeliveryDeliveryCountry;
	@ManyToOne
	@JoinColumn(name="idEcDelivery")
	private TblEcDelivery ecdelivery;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="iddeliverycountry")
	private TblDeliveryCountry deliverycountry;
	@OneToMany(fetch = FetchType.EAGER,mappedBy = "deliverycountry",cascade = CascadeType.ALL)
	@Fetch(FetchMode.SELECT)
	private Set<TblDeliveryZone> deliveryzones;
	@OneToMany(fetch = FetchType.EAGER,mappedBy = "deliverycountry",cascade = CascadeType.ALL)
	@Fetch(FetchMode.SELECT)
	private Set<TblDeliveryCity> deliverycities;
	public int getIdDeliveryDeliveryCountry() {
		return idDeliveryDeliveryCountry;
	}

	public void setIdDeliveryDeliveryCountry(int idDeliveryDeliveryCountry) {
		this.idDeliveryDeliveryCountry = idDeliveryDeliveryCountry;
	}

	public TblEcDelivery getEcdelivery() {
		return ecdelivery;
	}

	public void setEcdelivery(TblEcDelivery ecdelivery) {
		this.ecdelivery = ecdelivery;
	}

	public TblDeliveryCountry getDeliverycountry() {
		return deliverycountry;
	}

	public void setDeliverycountry(TblDeliveryCountry deliverycountry) {
		this.deliverycountry = deliverycountry;
	}
	
	public Set<TblDeliveryZone> getDeliveryzones() {
		return deliveryzones;
	}

	public void setDeliveryzones(Set<TblDeliveryZone> deliveryzones) {
		this.deliveryzones = deliveryzones;
	}
	
	public Set<TblDeliveryCity> getDeliverycities() {
		return deliverycities;
	}

	public void setDeliverycities(Set<TblDeliveryCity> deliverycities) {
		this.deliverycities = deliverycities;
	}

	public void convertToTable(Ivo vo){
		DeliveryDeliveryCountry ddc = (DeliveryDeliveryCountry) vo;
		this.idDeliveryDeliveryCountry = ddc.getIdDeliveryDeliveryCountry();
		if (ddc.getDeliverycountry() != null){
			this.deliverycountry = new TblDeliveryCountry();
			this.deliverycountry.convertToTable(ddc.getDeliverycountry());
		}
		if (ddc.getEcdelivery() != null){
			this.ecdelivery = new TblEcDelivery();
			this.ecdelivery.convertToTable(ddc.getEcdelivery());
		}
		if (ddc.getDeliveryzones() != null){
			this.deliveryzones = new HashSet<TblDeliveryZone>();
			for(Iterator<DeliveryZone> it = ddc.getDeliveryzones().iterator();it.hasNext();){
				DeliveryZone dz = it.next();
				TblDeliveryZone tdz = new TblDeliveryZone();
				tdz.convertToTable(dz);
				this.deliveryzones.add(tdz);
			}
		}
		if (ddc.getDeliverycities() != null){
			this.deliverycities = new HashSet<TblDeliveryCity>();
			for(Iterator<DeliveryCity> itc = ddc.getDeliverycities().iterator();itc.hasNext();){
				DeliveryCity dz = itc.next();
				TblDeliveryCity tdz = new TblDeliveryCity();
				tdz.convertToTable(dz);
				this.deliverycities.add(tdz);
			}
		}
	}
	public void convertToTableForSaving(Ivo vo){
		convertToTable(vo);
		if (this.deliveryzones != null){
			for(Iterator<TblDeliveryZone> it = this.deliveryzones.iterator();it.hasNext();){
				TblDeliveryZone tdz = it.next();
				tdz.setDeliverycountry(this);
			}
		}
		if (this.deliverycities != null){
			for(Iterator<TblDeliveryCity> itc = this.deliverycities.iterator();itc.hasNext();){
				TblDeliveryCity tdz = itc.next();
				tdz.setDeliverycountry(this);
			}
		}
		
	}
}
