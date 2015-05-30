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
	  @Produces(MediaType.APPLICATION_JSON)
	  public String calculateRowTotal( String data){
		  Gson gson = new Gson();
		  RowTotalCalculator sms = gson.fromJson(data,RowTotalCalculator.class);
		  sms.calculation();
		  return gson.toJson(sms);
	  }
	  @POST
	  @Path("headtotal")
	  @Produces(MediaType.APPLICATION_JSON)
	  public String saveHead(String data){
		  Gson gson = new Gson();
		  HeadTotalCalculation sms = gson.fromJson(data,HeadTotalCalculation.class);
		  sms.calculation();
		  return gson.toJson(sms);
	  }
}
