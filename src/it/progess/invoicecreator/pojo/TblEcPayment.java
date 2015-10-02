package it.progess.invoicecreator.pojo;

import it.progess.invoicecreator.vo.EcPayment;
import it.progess.invoicecreator.vo.Ivo;
import it.progess.invoicecreator.vo.PaymentSolution;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
/*TODO EcPayment vo
 * Company link
 * customer link
 * frontend */
@Entity
@Table(name="tblecpayment")
public class TblEcPayment implements Itbl {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idecpayment")
	private int idEcPayment;
	@Column(name="loggeduser")
	private boolean loggeduser;
	@Column(name="nologgeduser")
	private boolean nologgeduser;
	
	@Column(name="accountID")
	private String accountID;
	@Column(name="token")
	private String token;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="idpaymentsolution")
	private TblPaymentSolution paysolution;
	@Column(name="commission")
	private float commission;
	
	public float getCommission() {
		return commission;
	}
	public void setCommission(float commission) {
		this.commission = commission;
	}
	public TblPaymentSolution getPaysolution() {
		return paysolution;
	}
	public void setPaysolution(TblPaymentSolution paysolution) {
		this.paysolution = paysolution;
	}
	public int getIdEcPayment() {
		return idEcPayment;
	}
	public void setIdEcPayment(int idEcPayment) {
		this.idEcPayment = idEcPayment;
	}
	public boolean isLoggeduser() {
		return loggeduser;
	}
	public void setLoggeduser(boolean loggeduser) {
		this.loggeduser = loggeduser;
	}
	public boolean isNologgeduser() {
		return nologgeduser;
	}
	public void setNologgeduser(boolean nologgeduser) {
		this.nologgeduser = nologgeduser;
	}
	
	public String getAccountID() {
		return accountID;
	}
	public void setAccountID(String accountID) {
		this.accountID = accountID;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public void convertToTable(Ivo obj){
		EcPayment tep  =(EcPayment)obj;
		this.idEcPayment = tep.getIdEcPayment();
		this.accountID = tep.getAccountID();
		this.loggeduser = tep.isLoggeduser();
		this.nologgeduser = tep.isNologgeduser();
		this.token = tep.getToken();
		this.commission = tep.getCommission();
		if (tep.getPaysolution() != null){
			this.paysolution = new TblPaymentSolution();
			this.paysolution.convertToTable(tep.getPaysolution());
		}
	}
}
