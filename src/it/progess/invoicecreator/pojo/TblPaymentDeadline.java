package it.progess.invoicecreator.pojo;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import it.progess.invoicecreator.vo.Ivo;
import it.progess.invoicecreator.vo.PaymentDeadline;

@Entity
@Table(name="tblpaymentdeadline")
public class TblPaymentDeadline implements Itbl,Comparable<TblPaymentDeadline> {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idPaymentDeadline")
	private int idPaymentDeadline;
	@Column(name="days")
	private int days;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idPayment", nullable = false)
	private TblPayment payment;
	public TblPayment getPayment() {
		return payment;
	}
	public void setPayment(TblPayment payment) {
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
	public void convertToTable(Ivo obj){
		PaymentDeadline pd = (PaymentDeadline)obj;
		this.idPaymentDeadline = pd.getIdPaymentDeadline();
		this.days = pd.getDays();
		if (pd.getPayment() != null){
			this.payment = new TblPayment();
			payment.convertToTable(pd.getPayment());
		}
	}
	public void convertToTableForSaving(Ivo obj,Itbl objToLink){
		PaymentDeadline pd = (PaymentDeadline)obj;
		this.idPaymentDeadline = pd.getIdPaymentDeadline();
		this.days = pd.getDays();
		this.payment = (TblPayment)objToLink;
	}
	@Override
	public int compareTo(TblPaymentDeadline p){
		return days - p.getDays();
	}
}
