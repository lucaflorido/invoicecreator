package it.progess.invoicecreator.vo;

import it.progess.invoicecreator.pojo.Itbl;
import it.progess.invoicecreator.pojo.TblPaymentDeadline;
import it.progess.invoicecreator.properties.GECOParameter;

public class PaymentDeadline implements Ivo,Comparable<PaymentDeadline>{
	private int idPaymentDeadline;
	private int days;
	private Payment payment;
	public Payment getPayment() {
		return payment;
	}
	public void setPayment(Payment payment) {
		this.payment = payment;
	}
	public int getIdPaymentDeadline() {
		return idPaymentDeadline;
	}
	public void setIdPaymentDeadline(int idPaymentDeadline) {
		this.idPaymentDeadline = idPaymentDeadline;
	}
	public int getDays() {
		return days;
	}
	public void setDays(int days) {
		this.days = days;
	}
	public void convertFromTable(Itbl obj){
		TblPaymentDeadline pd = (TblPaymentDeadline)obj;
		this.idPaymentDeadline = pd.getIdPaymentDeadline();
		this.days = pd.getDays();
	}
	@Override
	public int compareTo(PaymentDeadline p){
		return days - p.getDays();
	}
	public GECOObject control(){
		if (this.days == 0){
			return new GECOError(GECOParameter.ERROR_VALUE_MISSING,"Giorni di Scadenza Mancanti");
		}
		
		return null;
	}
}
