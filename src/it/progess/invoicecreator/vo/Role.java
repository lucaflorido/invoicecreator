package it.progess.invoicecreator.vo;

import it.progess.invoicecreator.pojo.Itbl;
import it.progess.invoicecreator.pojo.TblRole;
import it.progess.invoicecreator.properties.GECOParameter;

public class Role implements Ivo {
	private int idrole;
	private String name;
	private Boolean create;
	private Boolean update;
	private Boolean delete;
	private Boolean read;
	private Boolean admin;
	private boolean ec;
	public boolean isEc() {
		return ec;
	}
	public void setEc(boolean ec) {
		this.ec = ec;
	}
	public int getIdrole() {
		return idrole;
	}
	public void setIdrole(int idrole) {
		this.idrole = idrole;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getCreate() {
		return create;
	}
	public void setCreate(Boolean create) {
		this.create = create;
	}
	public Boolean getUpdate() {
		return update;
	}
	public void setUpdate(Boolean update) {
		this.update = update;
	}
	public Boolean getDelete() {
		return delete;
	}
	public void setDelete(Boolean delete) {
		this.delete = delete;
	}
	public Boolean getRead() {
		return read;
	}
	public void setRead(Boolean read) {
		this.read = read;
	}
	public Boolean getAdmin() {
		return admin;
	}
	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}
	public void convertFromTable(Itbl itbl){
		if (itbl != null){
			TblRole roletbl = (TblRole)itbl;
			this.setIdrole(roletbl.getIdrole());
			this.setName(roletbl.getName());
			this.setCreate(roletbl.getCreate());
			this.setUpdate(roletbl.getUpdate());
			this.setDelete(roletbl.getDelete());
			this.setRead(roletbl.getRead());
			this.setAdmin(roletbl.getAdmin());
			this.ec = roletbl.isEc();
		}
	}
	public GECOObject control(){
		if (this.name == null || this.name.equals("") == true){
			return new GECOError(GECOParameter.ERROR_VALUE_MISSING,"Nome Mancante");
		}
		return null;
	}
}
