package it.progess.invoicecreator.service;



import it.progess.invoicecreator.dao.RoleDao;

import it.progess.invoicecreator.vo.Role;


import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

@Path("role")
public class RoleService {
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String userList() {
		Gson gson = new Gson();
		RoleDao roledao = new RoleDao();
		return gson.toJson(roledao.getRoleList());
	}
	
	@PUT
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	public String saveUser(@FormParam("roles") String roles){
	  Gson gson = new Gson();
	  Role[] rolesarray = gson.fromJson(roles,Role[].class);
	  RoleDao roledao = new RoleDao();
	  roledao.saveUpdatesRoles(rolesarray);
	  return gson.toJson(true);
	}
	/***
	Delete user 
   */

	  @DELETE
	  @Produces(MediaType.TEXT_PLAIN)
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	  public String deleteRole(@FormParam("roleobj") String roleobj){
		  Gson gson = new Gson();
		  try{
			  Role role = gson.fromJson(roleobj,Role.class);
			  RoleDao roledao = new RoleDao();
			  roledao.deleteRole(role);
			  return gson.toJson(true);
		  }catch(Exception e){
			  return gson.toJson("");
		  }
	  }
	
}
