package it.progess.invoicecreator.service;

import it.progess.invoicecreator.dao.DraftDao;
import it.progess.invoicecreator.dao.ExportDao;
import it.progess.invoicecreator.hibernate.HibernateUtils;
import it.progess.invoicecreator.vo.DraftElement;
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
	@Path("init/{key}")
	@Produces(MediaType.APPLICATION_JSON)
	public String initDraft(@Context HttpServletRequest request,String data,@PathParam("key") String key) {
		HttpSession session = request.getSession();  
		DraftDao dao = new DraftDao();
		Gson gson = new Gson();
		return gson.toJson(dao.setupDraft(data,key, session));
	}
	@POST
	@Path("addtodraft/{draftid}/{key}")
	@Produces(MediaType.APPLICATION_JSON)
	public String addDraft(@Context HttpServletRequest request,String data,@PathParam("draftid") String draftid,@PathParam("draftid") String key) {
		HttpSession session = request.getSession();  
		DraftDao dao = new DraftDao();
		Gson gson = new Gson();
		DraftElement de = gson.fromJson(data, DraftElement.class);
		return gson.toJson(dao.addToDraft(draftid, session, de,key));
	}
	@POST
	@Path("removefromdraft/{draftid}/{key}")
	@Produces(MediaType.APPLICATION_JSON)
	public String removeDraft(@Context HttpServletRequest request,String data,@PathParam("draftid") String draftid,@PathParam("draftid") String key) {
		HttpSession session = request.getSession();  
		DraftDao dao = new DraftDao();
		Gson gson = new Gson();
		DraftElement de = gson.fromJson(data, DraftElement.class);
		
		return gson.toJson(dao.removeFromDraft(draftid, session, de,key));
	}
	@POST
	@Path("updatedraft/{draftid}")
	@Produces(MediaType.APPLICATION_JSON)
	public String updateDraft(@Context HttpServletRequest request,String data,@PathParam("draftid") String draftid) {
		HttpSession session = request.getSession();  
		DraftDao dao = new DraftDao();
		Gson gson = new Gson();
		DraftElement de = gson.fromJson(data, DraftElement.class);
		return gson.toJson(dao.updateDraft(draftid, session, de));
	}
	@POST
	@Path("confirmdraft/{draftid}/{paymenttype}")
	@Produces(MediaType.APPLICATION_JSON)
	public String confirmDraft(@Context HttpServletRequest request,String data,@PathParam("draftid") String draftid,@PathParam("paymenttype") String paymenttype) {
		HttpSession session = request.getSession();  
		DraftDao dao = new DraftDao();
		Gson gson = new Gson();
		User user = gson.fromJson(data, User.class);
		return gson.toJson(dao.confirmPayment(session, user, draftid, paymenttype));
	}
}
