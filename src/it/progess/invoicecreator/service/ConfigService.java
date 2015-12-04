package it.progess.invoicecreator.service;

import it.progess.invoicecreator.dao.ConfigDao;
import it.progess.invoicecreator.hibernate.HibernateUtils;
import it.progess.invoicecreator.vo.DocumentFlow;
import it.progess.invoicecreator.vo.User;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

@Path("config")
public class ConfigService {
	@POST
	@Path("documentflow")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String saveDocumentFlow(String data,@Context HttpServletRequest request){
		Gson gson = new Gson();
		DocumentFlow df = (DocumentFlow)gson.fromJson(data,DocumentFlow.class);
		User loggeduser = HibernateUtils.getUserFromSession(request);
		return gson.toJson(new ConfigDao().saveUpdatesDocumentFlow(df, loggeduser));
	}
	@GET
	@Path("documentflow")
	@Produces(MediaType.APPLICATION_JSON)
	public String getDocumentFlow(@Context HttpServletRequest request){
		Gson gson = new Gson();
		User loggeduser = HibernateUtils.getUserFromSession(request);
		return gson.toJson(new ConfigDao().getDocumentFlowList(loggeduser));
	}
	@POST
	@Path("documentflow/delete")
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteDocumentFlow(String data){
		Gson gson = new Gson();
		DocumentFlow df = (DocumentFlow)gson.fromJson(data,DocumentFlow.class);
		return gson.toJson(new ConfigDao().deleteDocumentFlow(df));
	}
}
