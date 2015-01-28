package it.progess.invoicecreator.service;

import it.progess.invoicecreator.vo.HeadTotalCalculation;
import it.progess.invoicecreator.vo.RowTotalCalculator;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

@Path("documenthelp")
public class DocumentAssistantService {
	  @POST
	  @Path("rowtotal")
	  @Produces(MediaType.TEXT_PLAIN)
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	  public String saveRow(@FormParam("row") String row){
		  Gson gson = new Gson();
		  RowTotalCalculator sms = gson.fromJson(row,RowTotalCalculator.class);
		  sms.calculation();
		  return gson.toJson(sms);
	  }
	  @POST
	  @Path("headtotal")
	  @Produces(MediaType.TEXT_PLAIN)
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	  public String saveHead(@FormParam("head") String head){
		  Gson gson = new Gson();
		  HeadTotalCalculation sms = gson.fromJson(head,HeadTotalCalculation.class);
		  sms.calculation();
		  return gson.toJson(sms);
	  }
}
