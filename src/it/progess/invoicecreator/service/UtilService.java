package it.progess.invoicecreator.service;

import it.progess.invoicecreator.dao.UtilDao;
import it.progess.invoicecreator.vo.helpobject.ProductBasicPricesCalculation;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

@Path("util")
public class UtilService {
	@POST
	@Path("prodbasicprice/percentage")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	public String productBasicPrice(@FormParam("prices") String prices){
		Gson g = new Gson();
		ProductBasicPricesCalculation p = g.fromJson(prices,ProductBasicPricesCalculation.class);
		return g.toJson(new UtilDao().calculateBasicPricesProduct(p,false));
	}
	@POST
	@Path("prodbasicprice/sellprice")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	public String productBasicSellPrice(@FormParam("prices") String prices){
		Gson g = new Gson();
		ProductBasicPricesCalculation p = g.fromJson(prices,ProductBasicPricesCalculation.class);
		return g.toJson(new UtilDao().calculateBasicPricesProduct(p,true));
	}
}
