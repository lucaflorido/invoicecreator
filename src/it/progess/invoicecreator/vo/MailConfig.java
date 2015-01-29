package it.progess.invoicecreator.vo;

import it.progess.invoicecreator.pojo.Itbl;
import it.progess.invoicecreator.pojo.TblMailConfig;

public class MailConfig implements Ivo {
	private int idMailConfig;
	private String host;
	private String port;
	private boolean auth;
	private boolean starttls;
	private String password;
	private String email;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public void convertFromTable(Itbl obj){
		TblMailConfig mc = (TblMailConfig)obj;
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
