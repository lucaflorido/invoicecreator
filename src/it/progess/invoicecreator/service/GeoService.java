package it.progess.invoicecreator.service;


import it.progess.invoicecreator.dao.GeoDao;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

@Path("geo")
public class GeoService {
	  @GET
	  @Path("countries")
	  @Produces(MediaType.APPLICATION_JSON)
	  public String getCountries() {
		Gson gson = new Gson();
		GeoDao dao = new GeoDao();
		return gson.toJson(dao.getCountries());
	  }
	  @GET
	  @Path("zones")
	  @Produces(MediaType.APPLICATION_JSON)
	  public String getZones() {
		Gson gson = new Gson();
		GeoDao dao = new GeoDao();
		return gson.toJson(dao.getZones());
	  }
	  @GET
	  @Produces(MediaType.APPLICATION_JSON)
	  @Path("zones/{idcountry}")
	  public String getZones(@PathParam("idcountry") int idcountry) {
		Gson gson = new Gson();
		GeoDao dao = new GeoDao();
		return gson.toJson(dao.getZones(idcountry));
	  }
	  @GET
	  @Path("cities")
	  @Produces(MediaType.APPLICATION_JSON)
	  public String getCities() {
		Gson gson = new Gson();
		GeoDao dao = new GeoDao();
		return gson.toJson(dao.getCities());
	  }
	  @GET
	  @Path("cities/zone/{idzone}")
	  @Produces(MediaType.APPLICATION_JSON)
	  public String getCitiesByZone(@PathParam("idzone") int idzone) {
		Gson gson = new Gson();
		GeoDao dao = new GeoDao();
		return gson.toJson(dao.getCitiesByZone(idzone));
	  }
	  @GET
	  @Path("cities/country/{idcountry}")
	  @Produces(MediaType.APPLICATION_JSON)
	  public String getCitiesByCountry(@PathParam("idcountry") int idcountry) {
		Gson gson = new Gson();
		GeoDao dao = new GeoDao();
		return gson.toJson(dao.getCitiesByCountry(idcountry));
	  }
}
