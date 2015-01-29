package it.progess.invoicecreator.vo;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import it.progess.invoicecreator.pojo.Itbl;
import it.progess.invoicecreator.pojo.TblPayment;
import it.progess.invoicecreator.pojo.TblPaymentDeadline;
import it.progess.invoicecreator.properties.GECOParameter;

public class Payment implements Ivo{
	private int idPayment;
	private String code;
	private String description;
	private boolean endofmonth;
	private Set<PaymentDeadline> deadlines;
	public Set<PaymentDeadline> getDeadlines() {
		return deadlines;
	}
	public void setDeadlines(Set<PaymentDeadline> deadlines) {
		this.deadlines = deadlines;
	}
	public boolean getEndofmonth() {
		return endofmonth;
	}
	public void setEndofmonth(boolean endofmonth) {
		this.endofmonth = endofmonth;
	}
	public int getIdPayment() {
		return idPayment;
	}
	public void setIdPayment(int idPayment) {
		this.idPayment = idPayment;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void convertFromTable(Itbl obj){
		TblPayment py = (TblPayment)obj;
		this.code = py.getCode();
		this.description = py.getDescription();
		this.endofmonth = py.getEndofmonth();
		this.idPayment = py.getIdPayment();
		if (py.getDeadlines() != null){
			this.deadlines = new TreeSet<PaymentDeadline>();
			for (Iterator<TblPaymentDeadline> iterator = py.getDeadlines().iterator(); iterator.hasNext();){
				TblPaymentDeadline paymentdeadline = iterator.next();
				PaymentDeadline payd = new PaymentDeadline();
				payd.convertFromTable(paymentdeadline);
				this.deadlines.add(payd);
			}
		}
	}
	public GECOObject control(){
		if (this.code == null || this.code.equals("") == true){
			return new GECOError(GECOParameter.ERROR_VALUE_MISSING,"Codice Mancante");
		}
		if (this.description == null || this.description.equals("") == true){
			return new GECOError(GECOParameter.ERROR_VALUE_MISSING,"Decrizione Mancante");
		}
		return null;
	}
}
