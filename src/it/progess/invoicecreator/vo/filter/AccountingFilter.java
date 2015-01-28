package it.progess.invoicecreator.vo.filter;

import it.progess.invoicecreator.vo.Customer;
import it.progess.invoicecreator.vo.Supplier;

public class AccountingFilter {
	public String dateFrom;
	public String dateTo;
	public Customer customer;
	public Supplier supplier;
	public boolean paid;
	public boolean expired;
	public boolean isCustomer;
	public boolean isSupplier;
}
