package it.progess.invoicecreator.vo;

import it.progess.invoicecreator.hibernate.DataUtilConverter;
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
	private String path;
	private RegistryVO entity;
	private Contact contact;
	private String code;
	private String birthday;
	private Address address;
	private String taxcode;
	private String oldpassword;
	private String confirmpassword;
	private Address deliveryaddress;
	public String getOldpassword() {
		return oldpassword;
	}
	public void setOldpassword(String oldpassword) {
		this.oldpassword = oldpassword;
	}
	public String getConfirmpassword() {
		return confirmpassword;
	}
	public void setConfirmpassword(String confirmpassword) {
		this.confirmpassword = confirmpassword;
	}
	public String getTaxcode() {
		return taxcode;
	}
	public void setTaxcode(String taxcode) {
		this.taxcode = taxcode;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public Contact getContact() {
		return contact;
	}
	public void setContact(Contact contact) {
		this.contact = contact;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public RegistryVO getEntity() {
		return entity;
	}
	public void setEntity(RegistryVO entity) {
		this.entity = entity;
	}
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
	
	public Address getDeliveryaddress() {
		return deliveryaddress;
	}
	public void setDeliveryaddress(Address deliveryaddress) {
		this.deliveryaddress = deliveryaddress;
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
		this.setCode(tbluser.getCode());
		if (tbluser.getBirthday() != null){
			this.birthday = DataUtilConverter.convertStringFromDate(tbluser.getBirthday());
		}else{
			this.birthday = null;
		}
		
		if (tbluser.getCompany() != null){
			this.company = new Company();
			this.company.convertFromTable(tbluser.getCompany());
		}
		if (tbluser.getContact() != null){
			this.contact = new Contact();
			this.contact.convertFromTableUser(tbluser.getContact());
			
		}
		this.setPath();
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setPath(){
		if (this.getContact() != null){
			if (this.getContact().getCustomer() != null){
				this.entity = this.getContact().getCustomer();
				this.path = "customer";
			}
			if (this.getContact().getTransporter() != null){
				this.entity = this.getContact().getTransporter();
				this.path = "transporter";
			}
			if (this.getContact().getPromoter() != null){
				this.entity = this.getContact().getPromoter();
				this.path = "promoter";
			}
		}else{
			this.path = "";
		}
	}
	public User(){
		
	}
}
