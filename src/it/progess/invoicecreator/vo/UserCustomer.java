package it.progess.invoicecreator.vo;

public class UserCustomer {
	private Customer customer;
	private Role role;
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public GECOObject control(){
		if (customer.getContact() == null || customer.getContact().getEmail1().equals(null) || customer.getContact().getEmail1().equals("") ){
			return new GECOError("Attenzione", "Inserire la mail del cliente per procedere con la creazione dell'utente");
		}
		return null;
	}
}
