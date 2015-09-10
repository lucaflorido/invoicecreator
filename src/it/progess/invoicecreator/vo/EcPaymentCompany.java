package it.progess.invoicecreator.vo;

import it.progess.invoicecreator.pojo.Itbl;
import it.progess.invoicecreator.pojo.TblEcPaymentCompany;

public class EcPaymentCompany implements Ivo{
	private int idEcPaymentCompany;
	private Company company;
	private EcPayment ecpayment;
	public int getIdEcPaymentCompany() {
		return idEcPaymentCompany;
	}
	public void setIdEcPaymentCompany(int idEcPaymentCompany) {
		this.idEcPaymentCompany = idEcPaymentCompany;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public EcPayment getEcpayment() {
		return ecpayment;
	}
	public void setEcpayment(EcPayment ecpayment) {
		this.ecpayment = ecpayment;
	}
	public void convertFromTable(Itbl t){
		TblEcPaymentCompany ecp = (TblEcPaymentCompany)t;
		this.idEcPaymentCompany = ecp.getIdEcPaymentCompany();
		if (ecp.getEcpayment() != null){
			this.ecpayment = new EcPayment();
			this.ecpayment.convertFromTable(ecp.getEcpayment());
		}
	}
	public GECOObject control(){
		return null;
	}
}
