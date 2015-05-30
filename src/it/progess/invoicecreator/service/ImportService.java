package it.progess.invoicecreator.service;


import it.progess.invoicecreator.dao.ImportDao;
import it.progess.invoicecreator.hibernate.HibernateUtils;
import it.progess.invoicecreator.vo.User;
import it.progess.invoicecreator.vo.importvo.ImportCustomer;
import it.progess.invoicecreator.vo.importvo.ImportProduct;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

@Path("import")
public class ImportService {
	@Context
	ServletContext context;
	@POST
	@Path("products")
	@Produces(MediaType.TEXT_HTML)
	public String importProducts(@Context HttpServletRequest request,String data) {
		ImportDao dao = new ImportDao();
		Gson gson = new Gson();
		User loggeduser = HibernateUtils.getUserFromSession(request);
		ImportProduct ip = gson.fromJson(data, ImportProduct.class);
		return gson.toJson(dao.importProducts(context,ip, loggeduser));
	}
	@POST
	@Path("customers")
	@Produces(MediaType.TEXT_HTML)
	public String importCustomers(@Context HttpServletRequest request,String data) {
		ImportDao dao = new ImportDao();
		Gson gson = new Gson();
		User loggeduser = HibernateUtils.getUserFromSession(request);
		ImportCustomer ic = gson.fromJson(data, ImportCustomer.class);
		return gson.toJson(dao.importCustomers(context,ic, loggeduser));
	}
}
