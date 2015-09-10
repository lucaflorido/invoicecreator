package it.progess.invoicecreator.pojo;

import it.progess.invoicecreator.vo.Ivo;
import it.progess.invoicecreator.vo.PaymentSolution;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tblpaymentsolution")
public class TblPaymentSolution implements Itbl{
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idpaymentsolution")
	private int idPaymentSolution;
	@Column(name="name")
	private String name;
	@Column(name="online")
	private boolean online;
	@Column(name="code")
	private String code;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public boolean isOnline() {
		return online;
	}
	public void setOnline(boolean online) {
		this.online = online;
	}
	public int getIdPaymentSolution() {
		return idPaymentSolution;
	}
	public void setIdPaymentSolution(int idPaymentSolution) {
		this.idPaymentSolution = idPaymentSolution;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void convertToTable(Ivo obj){
		PaymentSolution ps =  (PaymentSolution)obj;
		this.idPaymentSolution = ps.getIdPaymentSolution();
		this.name = ps.getName();
		this.online = ps.isOnline();
		this.code = ps.getCode();
	}
}
