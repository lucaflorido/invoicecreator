package it.progess.invoicecreator.service;

import it.progess.invoicecreator.dao.UserDao;
import it.progess.invoicecreator.hibernate.HibernateUtils;
import it.progess.invoicecreator.pojo.TblUser;
import it.progess.invoicecreator.vo.User;
import it.progess.transport.config.ProgessParameters;
import it.progess.transport.vo.ProgessObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

@Path("user")
public class UserService {
	/***
	 * List of users 
	 */
	  @GET
	  @Produces(MediaType.TEXT_PLAIN)
	  public String userList(@Context HttpServletRequest request) {
		HttpSession session = request.getSession();  
		String user = session.getAttribute("user").toString();
		System.out.println("USER: "+user);
		Gson gson = new Gson();
		UserDao userdao = new UserDao();
		return gson.toJson(userdao.getListUser());
	  }
	  /***
		 * List of users 
		 */
		  @GET
		  @Path("startup")
		  @Produces(MediaType.TEXT_PLAIN)
		  public String startup() {
			Gson gson = new Gson();
			UserDao userdao = new UserDao();
			userdao.checkAdmin();
			return gson.toJson(true);
		  }
	  /***
	  * Logged in user 
	  */
	  @GET
	  @Path("loggedinuser")
	  @Produces(MediaType.TEXT_PLAIN)
	  public String userloggedin(@Context HttpServletRequest request) {
		Gson gson = new Gson();
		User loggeduser = HibernateUtils.getUserFromSession(request);
		return gson.toJson(loggeduser);
	  }
	  /***
	  * Logout 
	  */
	  @GET
	  @Path("logout")
	  @Produces(MediaType.TEXT_PLAIN)
	  public String userlogout(@Context HttpServletRequest request) {
		HttpSession session = request.getSession();  
		session.setAttribute("user",null);
		Gson gson = new Gson();
		return gson.toJson(true);
	  }
	  /***
		Check credentials 
       */
	  @POST
	  @Produces(MediaType.APPLICATION_JSON)
	  @Consumes(MediaType.APPLICATION_JSON) 
	  public String checkCredentials( String data,@Context HttpServletRequest request){
		  HttpSession session = request.getSession();
		  Gson gson = new Gson();
		  User user = gson.fromJson(data,User.class);
		  UserDao userdao = new UserDao();
		  return gson.toJson(userdao.checkCredentials(user.getUsername(), user.getPassword(),session));
	  }
	  /***
		Save new User 
      */
	  @PUT
	  @Produces(MediaType.TEXT_PLAIN)
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	  public String saveUser(@FormParam("loginobj") String loginobj,@Context HttpServletRequest request){
		  Gson gson = new Gson();
		  User user = gson.fromJson(loginobj,User.class);
		  UserDao userdao = new UserDao();
		  HttpSession session = request.getSession();  
		  int iduser = userdao.saveUpdate(user,session,request);
		  TblUser tbluser = userdao.getSingleUser(iduser);
		  user.convertFromTable(tbluser);
		  return gson.toJson(user);
	  }
	  /***
		Get Single user
      */
	  @GET
	  @Path("{userid}")
	  @Produces(MediaType.TEXT_HTML)
	  public String singleUser(@PathParam("userid") int id) {
		Gson gson = new Gson();
		UserDao userdao = new UserDao();
		User user = new User();
		TblUser tbluser = userdao.getSingleUser(id);
		user.convertFromTable(tbluser);
		return gson.toJson(user);
	  }
	  /***
		Delete user 
	   */
	  @DELETE
	  @Produces(MediaType.TEXT_PLAIN)
	  public String deleteUser(@FormParam("userobj") String userobj){
		  Gson gson = new Gson();
		  try{
			  User user = gson.fromJson(userobj,User.class);
			  UserDao userdao = new UserDao();
			  userdao.deleteUser(user);
			  return gson.toJson(true);
		  }catch(Exception e){
			  return gson.toJson(false);
		  }
	  }
	  /***
		Save new User 
    */
	  @PUT
	  @Path("changepassword")
	  @Produces(MediaType.TEXT_PLAIN)
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	  public String changePassword(@FormParam("userobj") String userobj,@Context HttpServletRequest request){
		  Gson gson = new Gson();
		  User user = gson.fromJson(userobj,User.class);
		  UserDao userdao = new UserDao();
		  int iduser = userdao.changePassword(user);
		  TblUser tbluser = userdao.getSingleUser(iduser);
		  user.convertFromTable(tbluser);
		  return gson.toJson(user);
	  }
}
