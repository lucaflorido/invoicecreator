package it.progess.invoicecreator.service;

import it.progess.invoicecreator.dao.AccountingDao;
import it.progess.invoicecreator.vo.Paid;
import it.progess.invoicecreator.vo.filter.AccountingFilter;
import it.progess.invoicecreator.vo.filter.AccountingMovementFilter;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.google.gson.Gson;

@Path("accounting")
public class AccountingService {
	  @POST
	  @Path("generationdocs/filterdocs")
	  @Produces(MediaType.TEXT_HTML)
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	  public String suspendedList(@FormParam("filter") String filter) {
		Gson gson = new Gson();
		AccountingDao dao = new AccountingDao();
		AccountingFilter filterDoc = gson.fromJson(filter,AccountingFilter.class);
		return gson.toJson(dao.getList(filterDoc));
	  }
	  @PUT
	  @Path("paid")
	  @Produces(MediaType.TEXT_HTML)
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	  public String savePaid(@FormParam("filter") String paid) {
		Gson gson = new Gson();
		AccountingDao dao = new AccountingDao();
		Paid paidObj = gson.fromJson(paid,Paid.class);
		paidObj.setSusp(null);
		return gson.toJson(dao.savePaid(paidObj));
	  }
	  @PUT
	  @Path("paid/selsusp")
	  @Produces(MediaType.TEXT_HTML)
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	  public String savePaidSelected(@FormParam("filter") String paid) {
		Gson gson = new Gson();
		AccountingDao dao = new AccountingDao();
		Paid paidObj = gson.fromJson(paid,Paid.class);
		return gson.toJson(dao.savePaid(paidObj));
	  }
	  @POST
	  @Path("accounting/movements")
	  @Produces(MediaType.TEXT_HTML)
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	  public String getAccountingMovement(@FormParam("filter") String filter) {
		Gson gson = new Gson();
		AccountingDao dao = new AccountingDao();
		AccountingMovementFilter filterDoc = gson.fromJson(filter,AccountingMovementFilter.class);
		return gson.toJson(dao.getAccountigList(filterDoc));
	  }
}
