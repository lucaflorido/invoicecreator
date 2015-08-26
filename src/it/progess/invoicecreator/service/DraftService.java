package it.progess.invoicecreator.service;

import it.progess.invoicecreator.dao.DraftDao;
import it.progess.invoicecreator.dao.ExportDao;
import it.progess.invoicecreator.hibernate.HibernateUtils;
import it.progess.invoicecreator.vo.Head;
import it.progess.invoicecreator.vo.Product;
import it.progess.invoicecreator.vo.User;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

@Path("draft")
public class DraftService {
	@Context
	ServletContext context;
	@POST
	@Path("init")
	@Produces(MediaType.APPLICATION_JSON)
	public String initDraft(@Context HttpServletRequest request,String data) {
		HttpSession session = request.getSession();  
		DraftDao dao = new DraftDao();
		Gson gson = new Gson();
		return gson.toJson(dao.setupDraft(data, session));
	}
	@POST
	@Path("addtodraft/{draftid}")
	@Produces(MediaType.APPLICATION_JSON)
	public String addDraft(@Context HttpServletRequest request,String data,@PathParam("draftid") String draftid) {
		HttpSession session = request.getSession();  
		DraftDao dao = new DraftDao();
		Gson gson = new Gson();
		Product product = gson.fromJson(data, Product.class);
		return gson.toJson(dao.addToDraft(draftid, session, product));
	}
}
