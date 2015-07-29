package it.progess.invoicecreator.service;

import it.progess.invoicecreator.dao.UtilDao;
import it.progess.invoicecreator.vo.Product;
import it.progess.invoicecreator.vo.helpobject.ProductBasicPricesCalculation;
import it.progess.invoicecreator.vo.helpobject.ProductListIncrementVo;

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
	@POST
	@Path("prodbasicprice/endprice")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	public String productBasicEndPrice(@FormParam("prices") String prices){
		Gson g = new Gson();
		ProductBasicPricesCalculation p = g.fromJson(prices,ProductBasicPricesCalculation.class);
		return g.toJson(new UtilDao().calculateEndPriceProduct(p));
	}
	@POST
	@Path("prodbasicprice/list")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	public String productBasicListPrice(@FormParam("product") String product){
		Gson g = new Gson();
		Product p = g.fromJson(product,Product.class);
		return g.toJson(new UtilDao().calculateProductListPrices(p));
	}
	@POST
	@Path("incrementlist")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON) 
	public String productListPriceIncrement(String data){
		Gson g = new Gson();
		ProductListIncrementVo p = g.fromJson(data,ProductListIncrementVo.class);
		return g.toJson(new UtilDao().calculateIncrementProductListPrice(p));
	}
}
