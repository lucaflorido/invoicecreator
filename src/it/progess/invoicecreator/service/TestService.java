package it.progess.invoicecreator.service;

import it.progess.invoicecreator.dao.UserDao;
import it.progess.invoicecreator.vo.User;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

@Path("example")
public class TestService  {
	
	  @GET
	  @Produces(MediaType.TEXT_HTML)
	  public String sayHtmlHello() {
		User user = new User();
		user.set_iduser(1);
		user.setUsername("luca");
		Gson gson = new Gson();
		UserDao userdao = new UserDao();
	    return "<html> " + "<title>" + "Hello Jersey" + "</title>"
	        + "<body><h1>" + "Hello Jersey and JSON : "+gson.toJson(userdao.getListUser()) + "</body></h1>" + "</html> ";
	  }
	
}
