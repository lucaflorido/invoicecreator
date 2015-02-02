package it.progess.invoicecreator.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import it.progess.invoicecreator.vo.GECOObject;
import it.progess.invoicecreator.vo.Ivo;
import it.progess.invoicecreator.vo.MailConfig;
@Entity
@Table(name="tblmailconfig")
public class TblMailConfig implements Itbl {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idMailConfig")
	private int idMailConfig;
	@Column(name="host")
	private String host;
	@Column(name="port")
	private String port;
	@Column(name="auth")
	private boolean auth;
	@Column(name="starttls")
	private boolean starttls;
	@Column(name="email")
	private String email;
	@Column(name="pec")
	private boolean pec;
	public boolean isPec() {
		return pec;
	}
	public void setPec(boolean pec) {
		this.pec = pec;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getIdMailConfig() {
		return idMailConfig;
	}
	public void setIdMailConfig(int idMailConfig) {
		this.idMailConfig = idMailConfig;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public boolean isAuth() {
		return auth;
	}
	public void setAuth(boolean auth) {
		this.auth = auth;
	}
	public boolean isStarttls() {
		return starttls;
	}
	public void setStarttls(boolean starttls) {
		this.starttls = starttls;
	}
	public void convertToTable(Ivo obj){
		MailConfig mc = (MailConfig)obj;
		this.auth = mc.isAuth();
		this.host = mc.getHost();
		this.idMailConfig = mc.getIdMailConfig();
		this.port = mc.getPort();
		this.starttls = mc.isStarttls();
		this.email = mc.getEmail();
		this.pec = mc.isPec();
	}
	public GECOObject control(){
		return null;
	}
}
