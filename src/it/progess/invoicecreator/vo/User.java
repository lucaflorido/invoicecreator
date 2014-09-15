package it.progess.invoicecreator.vo;

import it.progess.invoicecreator.pojo.TblUser;

public class User {
	private int iduser;
	private String username;
	private String password;
	private String name;
	private String surname;
	private String phone;
	private String mobile;
	private String email;
	private Role role;
	private String newpassword;
	//private  role;
	private Boolean active;
	private Company company;
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public int getIduser() {
		return iduser;
	}
	public void setIduser(int iduser) {
		this.iduser = iduser;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int get_iduser() {
		return iduser;
	}
	public void set_iduser(int iduser) {
		this.iduser = iduser;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public String getNewpassword() {
		return newpassword;
	}
	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}
	public void convertFromTable(TblUser tbluser){
		this.set_iduser(tbluser.getIduser());
		this.setUsername(tbluser.getUsername());
		this.setPassword(tbluser.getPassword());
		this.setName(tbluser.getName());
		this.setSurname(tbluser.getSurname());
		this.setPhone(tbluser.getPhone());
		this.setMobile(tbluser.getMobile());
		this.setEmail(tbluser.getEmail());
		Role roleFrom = new Role();
		roleFrom.convertFromTable(tbluser.getRole());
		this.setRole(roleFrom);
		this.setActive(tbluser.getActive());
		if (tbluser.getCompany() != null){
			this.company = new Company();
			this.company.convertFromTable(tbluser.getCompany());
		}
	}
}
