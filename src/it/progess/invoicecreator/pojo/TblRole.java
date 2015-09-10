package it.progess.invoicecreator.pojo;

import it.progess.invoicecreator.vo.Ivo;
import it.progess.invoicecreator.vo.Role;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tblrole")
public class TblRole implements Itbl {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idRole")
	private int idrole;
	@Column(name="name")
	private String name;
	@Column(name="CREATE_GRANT")
	private Boolean create;
	@Column(name="UPDATE_GRANT")
	private Boolean update;
	@Column(name="DELETE_GRANT")
	private Boolean delete;
	@Column(name="READ_GRANT")
	private Boolean read;
	@Column(name="ADMIN_GRANT")
	private Boolean admin;
	@Column(name="ec")
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
	public void convertToTable(Ivo ivo){
		Role role = (Role)ivo;
		this.setIdrole(role.getIdrole());
		this.setName(role.getName());
		this.setCreate(role.getCreate());
		this.setUpdate(role.getUpdate());
		this.setDelete(role.getDelete());
		this.setRead(role.getRead());
		this.setAdmin(role.getAdmin());
		this.ec = role.isEc();
	}
}
