package it.progess.invoicecreator.service;

import it.progess.invoicecreator.dao.BasicDao;
import it.progess.invoicecreator.dao.RegistryDao;
import it.progess.invoicecreator.dao.UserDao;
import it.progess.invoicecreator.hibernate.HibernateUtils;
import it.progess.invoicecreator.vo.Brand;
import it.progess.invoicecreator.vo.CategoryCustomer;
import it.progess.invoicecreator.vo.CategoryProduct;
import it.progess.invoicecreator.vo.CategorySupplier;
import it.progess.invoicecreator.vo.Company;
import it.progess.invoicecreator.vo.Composition;
import it.progess.invoicecreator.vo.Counter;
import it.progess.invoicecreator.vo.Document;
import it.progess.invoicecreator.vo.GECOError;
import it.progess.invoicecreator.vo.GroupCustomer;
import it.progess.invoicecreator.vo.GroupProduct;
import it.progess.invoicecreator.vo.GroupSupplier;
import it.progess.invoicecreator.vo.Payment;
import it.progess.invoicecreator.vo.Region;
import it.progess.invoicecreator.vo.StoreMovement;
import it.progess.invoicecreator.vo.SubCategoryProduct;
import it.progess.invoicecreator.vo.TaxRate;
import it.progess.invoicecreator.vo.UnitMeasure;


import it.progess.invoicecreator.vo.User;

import javax.servlet.http.HttpServletRequest;
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

@Path("basic")
public class BasicService {
	@GET
	@Path("taxrate")
	@Produces(MediaType.TEXT_PLAIN)
	public String getTaxrateList(@Context HttpServletRequest request){
		Gson gson = new Gson();
		BasicDao taxratedao = new BasicDao();
		User loggeduser = HibernateUtils.getUserFromSession(request);
		return gson.toJson(taxratedao.getTaxRateList(loggeduser));
	}
	@PUT
	@Path("taxrate")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	public String saveTaxrates(@FormParam("taxrates") String taxrates,@Context HttpServletRequest request){
	  Gson gson = new Gson();
	  TaxRate[] taxratesarray = gson.fromJson(taxrates,TaxRate[].class);
	  BasicDao taxratedao = new BasicDao();
	  User loggeduser = HibernateUtils.getUserFromSession(request);
	  return gson.toJson(taxratedao.saveUpdatesTaxrate(taxratesarray,loggeduser));
	}
	/***
	Delete user 
   */

	  @POST
	  @Path("taxrate/delete")
	  @Produces(MediaType.APPLICATION_JSON)
	  @Consumes(MediaType.APPLICATION_JSON) 
	  public String deleteTaxRate(String data){
		  Gson gson = new Gson();
		  try{
			  TaxRate taxrate = gson.fromJson(data,TaxRate.class);
			  BasicDao taxratedao = new BasicDao();
			  return gson.toJson(taxratedao.deleteTaxRate(taxrate));
		  }catch(Exception e){
			  return gson.toJson("");
		  }
	  }
	  /*****
	   * 
	   * Unit Measure
	   * @return
	   */
	  @GET
	  @Path("unitmeasure")
 	  @Produces(MediaType.TEXT_PLAIN)
	  public String getUnitMeasureList(@Context HttpServletRequest request){
		  User loggeduser = HibernateUtils.getUserFromSession(request);
		Gson gson = new Gson();
		BasicDao dao = new BasicDao();
		return gson.toJson(dao.getUnitMeasureList(loggeduser));
	  }
	  @PUT
	  @Path("unitmeasure")
	  @Produces(MediaType.TEXT_PLAIN)
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	  public String saveUM(@FormParam("ums") String ums,@Context HttpServletRequest request){
		  User loggeduser = HibernateUtils.getUserFromSession(request);
		  Gson gson = new Gson();
		  UnitMeasure[] umsarray = gson.fromJson(ums,UnitMeasure[].class);
		  BasicDao dao = new BasicDao();
		  return gson.toJson(dao.saveUpdatesUnitMeasure(umsarray,loggeduser));
	  }
		/***
		Delete user 
	   */

		  @POST
		  @Path("unitmeasure/delete")
		  @Produces(MediaType.APPLICATION_JSON)
		  @Consumes(MediaType.APPLICATION_JSON) 
		  public String deleteUm( String data){
			  Gson gson = new Gson();
			  try{
				  UnitMeasure um = gson.fromJson(data,UnitMeasure.class);
				  BasicDao dao = new BasicDao();
				  return gson.toJson(dao.deleteUM(um));
			  }catch(Exception e){
				  return gson.toJson("");
			  }
		  }

	  /*****
	   * 
	   * Store Movement
	   * @return
	   */
	  @GET
	  @Path("storemovement")
 	  @Produces(MediaType.TEXT_PLAIN)
	  public String getStoreMovementList(){
		Gson gson = new Gson();
		BasicDao dao = new BasicDao();
		return gson.toJson(dao.getStoreMovementList());
	  }
	  @PUT
		@Path("storemovement")
	    @Produces(MediaType.TEXT_PLAIN)
	    @Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
		public String saveSM(@FormParam("sms") String sms){
		  Gson gson = new Gson();
		  StoreMovement[] smsarray = gson.fromJson(sms,StoreMovement[].class);
		  BasicDao dao = new BasicDao();
		  return gson.toJson(dao.saveUpdatesStoreMovement(smsarray));
		}
		/***
		Delete user 
	   */

		  @DELETE
		  @Path("storemovement")
		  @Produces(MediaType.TEXT_PLAIN)
		  @Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
		  public String deleteSM(@FormParam("smobj") String smobj){
			  Gson gson = new Gson();
			  try{
				  StoreMovement sm = gson.fromJson(smobj,StoreMovement.class);
				  BasicDao dao = new BasicDao();
				  dao.deleteSM(sm);
				  return gson.toJson(true);
			  }catch(Exception e){
				  return gson.toJson("");
			  }
		  }

	  /*****
	   * 
	   * Counter
	   * @return
	   */
	  @GET
	  @Path("counter")
 	  @Produces(MediaType.TEXT_PLAIN)
	  public String getCounterList(@Context HttpServletRequest request){
		Gson gson = new Gson();
		BasicDao dao = new BasicDao();
		User loggeduser = HibernateUtils.getUserFromSession(request);
		if (loggeduser.getRole().getAdmin() == true && loggeduser.getCompany() == null){
			return gson.toJson(dao.getCounterList());
		}else{
			return gson.toJson(dao.getCounterList(loggeduser));
		}
	  }
	    @PUT
		@Path("counter")
	    @Produces(MediaType.TEXT_PLAIN)
	    @Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
		public String saveCounter(@FormParam("counters") String counters){
		  Gson gson = new Gson();
		  Counter[] smsarray = gson.fromJson(counters,Counter[].class);
		  BasicDao dao = new BasicDao();
		  return gson.toJson(dao.saveUpdatesCounter(smsarray));
		}
	    @PUT
		@Path("countercompany")
	    @Produces(MediaType.TEXT_PLAIN)
	    @Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
		public String saveCounterCompany(@FormParam("counters") String counters,@Context HttpServletRequest request){
		  Gson gson = new Gson();
		  Counter[] smsarray = gson.fromJson(counters,Counter[].class);
		  User loggeduser = HibernateUtils.getUserFromSession(request);
		  BasicDao dao = new BasicDao();
		  return gson.toJson(dao.saveUpdatesCounter(smsarray, loggeduser));
		}
		/***
		Delete user 
	   */

		  @DELETE
		  @Path("counter")
		  @Produces(MediaType.TEXT_PLAIN)
		  @Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
		  public String deleteCounter(@FormParam("counterobj") String counterobj){
			  Gson gson = new Gson();
			  try{
				  Counter sm = gson.fromJson(counterobj,Counter.class);
				  BasicDao dao = new BasicDao();
				  return gson.toJson(dao.deleteCounter(sm));
			  }catch(Exception e){
				  return gson.toJson("");
			  }
		  }

	  /*****
	   * 
	   * Payment
	   * @return
	   */
	  @GET
	  @Path("payment")
 	  @Produces(MediaType.TEXT_PLAIN)
	  public String getPaymentList(){
		Gson gson = new Gson();
		BasicDao dao = new BasicDao();
		return gson.toJson(dao.getPaymentList());
	  }
	  @PUT
	  @Path("payment")
	  @Produces(MediaType.TEXT_PLAIN)
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	  public String savePayment(@FormParam("payments") String payments){
		  Gson gson = new Gson();
		  Payment[] smsarray = gson.fromJson(payments,Payment[].class);
		  BasicDao dao = new BasicDao();
		  return gson.toJson(dao.saveUpdatesPayment(smsarray));
	  }
		/***
		Delete user 
	   */

	  @DELETE
	  @Path("payment")
	  @Produces(MediaType.TEXT_PLAIN)
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	  public String deletePayment(@FormParam("paymentobj") String paymentobj){
		  Gson gson = new Gson();
		  try{
			  Payment sm = gson.fromJson(paymentobj,Payment.class);
			  BasicDao dao = new BasicDao();
			  dao.deletePayment(sm);
			  return gson.toJson(true);
		  }catch(Exception e){
			  return gson.toJson("");
		  }
	  }
	  /*****
	   * 
	   * Document
	   * @return
	   */
	  @GET
	  @Path("document")
 	  @Produces(MediaType.TEXT_PLAIN)
	  public String getDocumentList(@Context HttpServletRequest request){
		Gson gson = new Gson();
		BasicDao dao = new BasicDao();
		User loggeduser = HibernateUtils.getUserFromSession(request);
		if (loggeduser.getRole().getAdmin() == true && loggeduser.getCompany() == null){
			return gson.toJson(dao.getDocumentList());
		}else{
			return gson.toJson(dao.getDocumentList(loggeduser));
		}
		
	  }
	  @PUT
	  @Path("document")
	  @Produces(MediaType.TEXT_PLAIN)
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	  public String saveDocument(@FormParam("documents") String documents,@Context HttpServletRequest request){
		  Gson gson = new Gson();
		  Document[] smsarray = gson.fromJson(documents,Document[].class);
		  BasicDao dao = new BasicDao();
		  User user = HibernateUtils.getUserFromSession(request);
		  if (user.getCompany() != null && user.getCompany().getIdCompany() != 0){
			  return gson.toJson(dao.saveUpdatesDocument(smsarray,user));
		  }
		  return gson.toJson(dao.saveUpdatesDocument(smsarray));
	  }
		/***
		Delete user 
	   */

	  @DELETE
	  @Path("document")
	  @Produces(MediaType.TEXT_PLAIN)
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	  public String deleteDocument(@FormParam("documentobj") String documentobj){
		  Gson gson = new Gson();
		  try{
			  Document sm = gson.fromJson(documentobj,Document.class);
			  BasicDao dao = new BasicDao();
			  return gson.toJson(dao.deleteDocument(sm));
		  }catch(Exception e){
			  return gson.toJson("");
		  }
	  }
	  
	  
	  /*****
	   * 
	   * GroupProduct User user = new User();
		user.setCompany(new RegistryDao().getCompany(key));
	   * @return
	   */
	  @GET
	  @Path("groupproduct")
 	  @Produces(MediaType.TEXT_PLAIN)
	  public String getGroupProductList(@Context HttpServletRequest request){
		  User loggeduser = HibernateUtils.getUserFromSession(request);
		Gson gson = new Gson();
		BasicDao dao = new BasicDao();
		return gson.toJson(dao.getGroupProductList(loggeduser));
	  }
	  @GET
	  @Path("groupproduct/{key}")
 	  @Produces(MediaType.TEXT_PLAIN)
	  public String getGroupProductList(@Context HttpServletRequest request,@PathParam("key") String key){
		User user = new User();
		user.setCompany(new RegistryDao().getCompany(key));
		Gson gson = new Gson();
		BasicDao dao = new BasicDao();
		return gson.toJson(dao.getGroupProductList(user));
	  }
	  @GET
	  @Path("groupproduct/prompt")
 	  @Produces(MediaType.TEXT_PLAIN)
	  public String getGroupProductListPromt(@Context HttpServletRequest request){
		  User loggeduser = HibernateUtils.getUserFromSession(request);
		Gson gson = new Gson();
		BasicDao dao = new BasicDao();
		return gson.toJson(dao.getGroupProductList(true,loggeduser));
	  }
	  @PUT
	  @Path("groupproduct")
	  @Produces(MediaType.TEXT_PLAIN)
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	  public String saveGroupProduct(@FormParam("groupproducts") String groupproducts,@Context HttpServletRequest request){
		  User loggeduser = HibernateUtils.getUserFromSession(request);
		  Gson gson = new Gson();
		  GroupProduct[] smsarray = gson.fromJson(groupproducts,GroupProduct[].class);
		  BasicDao dao = new BasicDao();
		  return gson.toJson(dao.saveUpdatesGroupProduct(smsarray,loggeduser));
	  }
		/***
		Delete user 
	   */

	  @POST
	  @Path("groupproduct/delete")
	  @Produces(MediaType.APPLICATION_JSON)
	  @Consumes(MediaType.APPLICATION_JSON) 
	  public String deleteGroupProduct( String data){
		  Gson gson = new Gson();
		  try{
			  GroupProduct sm = gson.fromJson(data,GroupProduct.class);
			  BasicDao dao = new BasicDao();
			  return gson.toJson(dao.deleteGroupProduct(sm));
		  }catch(Exception e){
			  return gson.toJson("");
		  }
	  }
	  
	  /*****
	   * 
	   * CategoryProduct
	   * @return
	   */
	  @GET
	  @Path("categoryproduct")
 	  @Produces(MediaType.TEXT_PLAIN)
	  public String getCategoryProductList(@Context HttpServletRequest request){
		  User loggeduser = HibernateUtils.getUserFromSession(request);
		  Gson gson = new Gson();
		  BasicDao dao = new BasicDao();
		return gson.toJson(dao.getCategoryProductList(loggeduser));
	  }
	  @GET
	  @Path("categoryproduct/{key}")
 	  @Produces(MediaType.TEXT_PLAIN)
	  public String getCategoryProductList(@Context HttpServletRequest request,@PathParam("key") String key){
		  User user = new User();
		  Company c = new RegistryDao().getCompany(key);
		  user.setCompany(c);
		  Gson gson = new Gson();
		  BasicDao dao = new BasicDao();
		return gson.toJson(dao.getCategoryProductList(user));
	  }
	  @PUT
	  @Path("categoryproduct")
	  @Produces(MediaType.TEXT_PLAIN)
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	  public String saveCategoryProduct(@FormParam("categoryproducts") String categoryproducts,@Context HttpServletRequest request){
		  User loggeduser = HibernateUtils.getUserFromSession(request);
		  Gson gson = new Gson();
		  CategoryProduct[] smsarray = gson.fromJson(categoryproducts,CategoryProduct[].class);
		  BasicDao dao = new BasicDao();
		  return gson.toJson(dao.saveUpdatesCategoryProduct(smsarray,loggeduser));
	  }
		/***
		Delete user 
	   */

	  @POST
	  @Path("categoryproduct/delete")
	  @Produces(MediaType.APPLICATION_JSON)
	  @Consumes(MediaType.APPLICATION_JSON) 
	  public String deleteCategoryProduct( String data){
		  Gson gson = new Gson();
		  try{
			  CategoryProduct sm = gson.fromJson(data,CategoryProduct.class);
			  BasicDao dao = new BasicDao();
			  return gson.toJson(dao.deleteCategoryProduct(sm));
		  }catch(Exception e){
			  return gson.toJson(new GECOError("err",e.getMessage()));
		  }
	  }
	  @POST
	  @Path("composition/delete")
	  @Produces(MediaType.APPLICATION_JSON)
	  @Consumes(MediaType.APPLICATION_JSON) 
	  public String deleteComposition( String data){
		  Gson gson = new Gson();
		  try{
			  Composition sm = gson.fromJson(data,Composition.class);
			  BasicDao dao = new BasicDao();
			  return gson.toJson(dao.deleteComposition(sm));
		  }catch(Exception e){
			  return gson.toJson("");
		  }
	  }
	  @POST
	  @Path("subcategoryproduct/delete")
	  @Produces(MediaType.APPLICATION_JSON)
	  public String deleteSubCategoryProduct( String data){
		  Gson gson = new Gson();
		  try{
			  SubCategoryProduct sm = gson.fromJson(data,SubCategoryProduct.class);
			  BasicDao dao = new BasicDao();
			  return gson.toJson(dao.deleteSubCategoryProduct(sm));
		  }catch(Exception e){
			  return gson.toJson("");
		  }
	  }
	  /*****
	   * 
	   * CategoryProduct
	   * @return
	   */
	  @GET
	  @Path("categorycustomer")
 	  @Produces(MediaType.TEXT_PLAIN)
	  public String getCategoryCustomerList(@Context HttpServletRequest request){
		  User loggeduser = HibernateUtils.getUserFromSession(request);
		  Gson gson = new Gson();
		BasicDao dao = new BasicDao();
		
		return gson.toJson(dao.getCategoryCustomerList(loggeduser));
	  }
	  @GET
	  @Path("composition")
 	  @Produces(MediaType.TEXT_PLAIN)
	  public String getCompositionList(@Context HttpServletRequest request){
		  User loggeduser = HibernateUtils.getUserFromSession(request);
		  Gson gson = new Gson();
		BasicDao dao = new BasicDao();
		
		return gson.toJson(dao.getComposition(loggeduser));
	  }
	  @PUT
	  @Path("composition")
	  @Produces(MediaType.APPLICATION_JSON)
	  @Consumes(MediaType.APPLICATION_JSON) 
	  public String saveComposition( String data,@Context HttpServletRequest request){
		  Gson gson = new Gson();
		  Composition[] smsarray = gson.fromJson(data,Composition[].class);
		  BasicDao dao = new BasicDao();
		  User loggeduser = HibernateUtils.getUserFromSession(request);
		  return gson.toJson(dao.saveUpdatesComposition(smsarray,loggeduser));
	  }
	  @PUT
	  @Path("categorycustomer")
	  @Produces(MediaType.TEXT_PLAIN)
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	  public String saveCategoryCustomer(@FormParam("categorycustomers") String categorycustomers,@Context HttpServletRequest request){
		  Gson gson = new Gson();
		  CategoryCustomer[] smsarray = gson.fromJson(categorycustomers,CategoryCustomer[].class);
		  BasicDao dao = new BasicDao();
		  User loggeduser = HibernateUtils.getUserFromSession(request);
		  return gson.toJson(dao.saveUpdatesCategoryCustomer(smsarray,loggeduser));
	  }
		/***
		Delete user 
	   */

	  @POST
	  @Path("categorycustomer/delete")
	  @Produces(MediaType.APPLICATION_JSON)
	  @Consumes(MediaType.APPLICATION_JSON) 
	  public String deleteCategoryCustomer( String data){
		  Gson gson = new Gson();
		  try{
			  CategoryCustomer sm = gson.fromJson(data,CategoryCustomer.class);
			  BasicDao dao = new BasicDao();
			  return gson.toJson( dao.deleteCategoryCustomer(sm));
		  }catch(Exception e){
			  return gson.toJson("");
		  }
	  }
	  
	  
	  
	  /*****
	   * 
	   * CategoryProduct
	   * @return
	   */
	  @GET
	  @Path("groupcustomer")
 	  @Produces(MediaType.TEXT_PLAIN)
	  public String getGroupCustomerList(@Context HttpServletRequest request){
		  User loggeduser = HibernateUtils.getUserFromSession(request);
		  Gson gson = new Gson();
		BasicDao dao = new BasicDao();
		
		return gson.toJson(dao.getGroupCustomerList(loggeduser));
	  }
	  @PUT
	  @Path("groupcustomer")
	  @Produces(MediaType.TEXT_PLAIN)
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	  public String saveGroupCustomer(@FormParam("groupcustomers") String groupcustomers,@Context HttpServletRequest request){
		  Gson gson = new Gson();
		  GroupCustomer[] smsarray = gson.fromJson(groupcustomers,GroupCustomer[].class);
		  BasicDao dao = new BasicDao();
		  User loggeduser = HibernateUtils.getUserFromSession(request);
		  return gson.toJson(dao.saveUpdatesGroupCustomer(smsarray,loggeduser));
	  }
		/***
		Delete user 
	   */

	  @POST
	  @Path("groupcustomer/delete")
	  @Produces(MediaType.APPLICATION_JSON)
	  @Consumes(MediaType.APPLICATION_JSON) 
	  public String deleteGroupCustomer( String data){
		  Gson gson = new Gson();
		  try{
			  GroupCustomer sm = gson.fromJson(data,GroupCustomer.class);
			  BasicDao dao = new BasicDao();
			  return gson.toJson(dao.deleteGroupCustomer(sm));
		  }catch(Exception e){
			  return gson.toJson("");
		  }
	  }
	  
	  /*****
	   * 
	   * CategoryProduct
	   * @return
	   */
	  @GET
	  @Path("categorysupplier")
 	  @Produces(MediaType.TEXT_PLAIN)
	  public String getCategorySupplierList(){
		Gson gson = new Gson();
		BasicDao dao = new BasicDao();
		return gson.toJson(dao.getCategorySupplierList());
	  }
	  @PUT
	  @Path("categorysupplier")
	  @Produces(MediaType.TEXT_PLAIN)
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	  public String saveCategorySupplier(@FormParam("categorysuppliers") String categorysuppliers){
		  Gson gson = new Gson();
		  CategorySupplier[] smsarray = gson.fromJson(categorysuppliers,CategorySupplier[].class);
		  BasicDao dao = new BasicDao();
		  return gson.toJson(dao.saveUpdatesCategorySupplier(smsarray));
	  }
		/***
		Delete user 
	   */

	  @DELETE
	  @Path("categorysupplier")
	  @Produces(MediaType.TEXT_PLAIN)
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	  public String deleteCategorySupplier(@FormParam("categorysupplierobj") String categorysupplierobj){
		  Gson gson = new Gson();
		  try{
			  CategorySupplier sm = gson.fromJson(categorysupplierobj,CategorySupplier.class);
			  BasicDao dao = new BasicDao();
			  dao.deleteCategorySupplier(sm);
			  return gson.toJson(true);
		  }catch(Exception e){
			  return gson.toJson("");
		  }
	  }
	  
	  
	  
	  /*****
	   * 
	   * CategoryProduct
	   * @return
	   */
	  @GET
	  @Path("groupsupplier")
 	  @Produces(MediaType.TEXT_PLAIN)
	  public String getGroupSupplierList(){
		Gson gson = new Gson();
		BasicDao dao = new BasicDao();
		return gson.toJson(dao.getGroupSupplierList());
	  }
	  @PUT
	  @Path("groupsupplier")
	  @Produces(MediaType.TEXT_PLAIN)
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	  public String saveGroupSupplier(@FormParam("groupsuppliers") String groupsuppliers){
		  Gson gson = new Gson();
		  GroupSupplier[] smsarray = gson.fromJson(groupsuppliers,GroupSupplier[].class);
		  BasicDao dao = new BasicDao();
		  return gson.toJson(dao.saveUpdatesGroupSupplier(smsarray));
	  }
		/***
		Delete user 
	   */

	  @DELETE
	  @Path("groupsupplier")
	  @Produces(MediaType.TEXT_PLAIN)
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	  public String deleteGroupSupplier(@FormParam("groupsupplierobj") String groupsupplierobj){
		  Gson gson = new Gson();
		  try{
			  GroupSupplier sm = gson.fromJson(groupsupplierobj,GroupSupplier.class);
			  BasicDao dao = new BasicDao();
			  dao.deleteGroupSupplier(sm);
			  return gson.toJson(true);
		  }catch(Exception e){
			  return gson.toJson("");
		  }
	  }
	  
	  
	  
	  /*****
	   * 
	   * CategoryProduct
	   * @return
	   */
	  @GET
	  @Path("brand")
 	  @Produces(MediaType.TEXT_PLAIN)
	  public String getBrandList(@Context HttpServletRequest request){
		  User loggeduser = HibernateUtils.getUserFromSession(request);
		Gson gson = new Gson();
		BasicDao dao = new BasicDao();
		return gson.toJson(dao.getBrandList(loggeduser));
	  }
	  @GET
	  @Path("brand/{key}")
 	  @Produces(MediaType.TEXT_PLAIN)
	  public String getBrandList(@Context HttpServletRequest request,@PathParam("key") String key){
		User user = new User();
		user.setCompany(new RegistryDao().getCompany(key));
		Gson gson = new Gson();
		BasicDao dao = new BasicDao();
		return gson.toJson(dao.getBrandList(user));
	  }
	  @PUT
	  @Path("brand")
	  @Produces(MediaType.TEXT_PLAIN)
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	  public String saveBrand(@FormParam("brands") String brands,@Context HttpServletRequest request){
		  User loggeduser = HibernateUtils.getUserFromSession(request);
		  Gson gson = new Gson();
		  Brand[] smsarray = gson.fromJson(brands,Brand[].class);
		  BasicDao dao = new BasicDao();
		  return gson.toJson(dao.saveUpdatesBrand(smsarray,loggeduser));
	  }
		/***
		Delete user 
	   */

	  @POST
	  @Path("brand/delete")
	  @Produces(MediaType.APPLICATION_JSON)
	  @Consumes(MediaType.APPLICATION_JSON) 
	  public String deleteBrand(String data){
		  Gson gson = new Gson();
		  try{
			  Brand sm = gson.fromJson(data,Brand.class);
			  BasicDao dao = new BasicDao();
			  return gson.toJson(dao.deleteBrand(sm));
		  }catch(Exception e){
			  return gson.toJson("");
		  }
	  }
	  
	  
	  /*****
	   * 
	   * Region
	   * @return 
	   */
	  @GET
	  @Path("region")
 	  @Produces(MediaType.TEXT_PLAIN)
	  public String getRegionList(@Context HttpServletRequest request){
		  User loggeduser = HibernateUtils.getUserFromSession(request);
		Gson gson = new Gson();
		BasicDao dao = new BasicDao();
		return gson.toJson(dao.getRegionList(loggeduser));
	  }
	  @GET
	  @Path("region/{key}")
 	  @Produces(MediaType.TEXT_PLAIN)
	  public String getRegionList(@Context HttpServletRequest request,@PathParam("key") String key){
		User user = new User();
		user.setCompany(new RegistryDao().getCompany(key));
		Gson gson = new Gson();
		BasicDao dao = new BasicDao();
		return gson.toJson(dao.getRegionList(user));
	  }
	  @PUT
	  @Path("region")
	  @Produces(MediaType.TEXT_PLAIN)
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	  public String saveRegion(@FormParam("regions") String regions,@Context HttpServletRequest request){
		  User loggeduser = HibernateUtils.getUserFromSession(request);
		  Gson gson = new Gson();
		  Region[] smsarray = gson.fromJson(regions,Region[].class);
		  BasicDao dao = new BasicDao();
		  return gson.toJson(dao.saveUpdatesRegion(smsarray,loggeduser));
	  }
		/***
		Delete user 
	   */

	  @POST
	  @Path("region/delete")
	  @Produces(MediaType.APPLICATION_JSON)
	  @Consumes(MediaType.APPLICATION_JSON) 
	  public String deleteRegion( String data){
		  Gson gson = new Gson();
		  try{
			  Region sm = gson.fromJson(data,Region.class);
			  BasicDao dao = new BasicDao();
			  return gson.toJson(dao.deleteRegion(sm));
		  }catch(Exception e){
			  return gson.toJson("");
		  }
	  }
	  @GET
	  @Path("paymentsolution")
 	  @Produces(MediaType.APPLICATION_JSON)
	  public String getPaymentSolutionList(){
		Gson gson = new Gson();
		BasicDao dao = new BasicDao();
		return gson.toJson(dao.getPaymentSolutionList());
	  }
	  @GET
	  @Path("currency")
 	  @Produces(MediaType.APPLICATION_JSON)
	  public String getCurrency(){
		Gson gson = new Gson();
		BasicDao dao = new BasicDao();
		return gson.toJson(dao.getCurrencyList());
	  }
}
