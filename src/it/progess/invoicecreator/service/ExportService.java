package it.progess.invoicecreator.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import it.progess.invoicecreator.dao.ExportDao;
import it.progess.invoicecreator.dao.PrinterDao;
import it.progess.invoicecreator.hibernate.HibernateUtils;
import it.progess.invoicecreator.vo.Head;
import it.progess.invoicecreator.vo.User;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

@Path("export")
public class ExportService {
	@Context
	ServletContext context;
	@POST
	@Path("heads")
	@Produces(MediaType.TEXT_HTML)
	public String singleHead(@Context HttpServletRequest request,String data) {
		ExportDao dao = new ExportDao();
		Gson gson = new Gson();
		User loggeduser = HibernateUtils.getUserFromSession(request);
		Head[] headsArray = gson.fromJson(data, Head[].class);
		return gson.toJson(dao.exportHeads(context,headsArray,loggeduser));
	}
}
