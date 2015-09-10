package it.progess.invoicecreator.service;


import it.progess.invoicecreator.dao.DocumentDao;
import it.progess.invoicecreator.dao.RegistryDao;
import it.progess.invoicecreator.hibernate.HibernateUtils;
import it.progess.invoicecreator.vo.Company;
import it.progess.invoicecreator.vo.Bank;
import it.progess.invoicecreator.vo.Customer;
import it.progess.invoicecreator.vo.Destination;
import it.progess.invoicecreator.vo.Head;
import it.progess.invoicecreator.vo.List;
import it.progess.invoicecreator.vo.ListProduct;
import it.progess.invoicecreator.vo.NewList;
import it.progess.invoicecreator.vo.Product;
import it.progess.invoicecreator.vo.Promoter;
import it.progess.invoicecreator.vo.Role;
import it.progess.invoicecreator.vo.Supplier;
import it.progess.invoicecreator.vo.Transporter;
import it.progess.invoicecreator.vo.UnitMeasureProduct;
import it.progess.invoicecreator.vo.User;
import it.progess.invoicecreator.vo.UserCustomer;
import it.progess.invoicecreator.vo.UserPromoter;
import it.progess.invoicecreator.vo.filter.HeadFilter;
import it.progess.invoicecreator.vo.filter.PagesFilter;
import it.progess.invoicecreator.vo.filter.customer.SelectCustomerList;
import it.progess.invoicecreator.vo.filter.product.SelectProductsFilter;
import it.progess.invoicecreator.vo.filter.supplier.SelectSupplierList;

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

import org.hibernate.hql.internal.ast.tree.SelectClause;

import com.google.gson.Gson;

@Path("registry")
public class RegistryService {
	/*****
	   * 
	   * Bank
	   * @return
	   */
	  @GET
	  @Produces(MediaType.TEXT_PLAIN)
	  @Path("company/{idcompany}")
	  public String getCompanyList(@PathParam("idcompany") int id){
		Gson gson = new Gson();
		RegistryDao dao = new RegistryDao();
		return gson.toJson(dao.getCompany(id));
	  }
	  @GET
	  @Path("companylist")
	  @Produces(MediaType.TEXT_PLAIN)
	  public String getCompanyFullList(){
		Gson gson = new Gson();
		RegistryDao dao = new RegistryDao();
		return gson.toJson(dao.getCompanyList());
	  }
	  @GET
	  @Path("company/{key}")
	  @Produces(MediaType.APPLICATION_JSON)
	  public String getCompany(@PathParam("key") String key){
		Gson gson = new Gson();
		RegistryDao dao = new RegistryDao();
		return gson.toJson(dao.getCompany(key));
	  }
	  @PUT
	  @Path("company")
	  @Produces(MediaType.TEXT_PLAIN)
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	  public String saveCompany(@FormParam("companys") String companys){
		  Gson gson = new Gson();
		  Company smsarray = gson.fromJson(companys,Company.class);
		  RegistryDao dao = new RegistryDao();
		  
		  return gson.toJson(dao.saveUpdatesCompany(smsarray));
	  }
		/***
		Delete user 
	   */

	  @DELETE
	  @Path("company")
	  @Produces(MediaType.TEXT_PLAIN)
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	  public String deleteCompany(@FormParam("companyobj") String companyobj){
		  Gson gson = new Gson();
		  try{
			  Company sm = gson.fromJson(companyobj,Company.class);
			  RegistryDao dao = new RegistryDao();
			  dao.deleteCompany(sm);
			  return gson.toJson(true);
		  }catch(Exception e){
			  return gson.toJson("");
		  }
	  }
	  /*****
	   * 
	   * Bank
	   * @return
	   */
	  @GET
	  @Path("bank")
 	  @Produces(MediaType.TEXT_PLAIN)
	  public String getBankList(){
		Gson gson = new Gson();
		RegistryDao dao = new RegistryDao();
		return gson.toJson(dao.getBankList());
	  }
	  /***
		Get Single user
    */
	  @GET
	  @Path("bank/{idbank}")
	  @Produces(MediaType.TEXT_HTML)
	  public String singleBank(@PathParam("idbank") int id) {
		Gson gson = new Gson();
		RegistryDao dao = new RegistryDao();
		Bank bank = new Bank();
		bank = dao.getSingleBank(id);
		return gson.toJson(bank);
	  }
	  @PUT
	  @Path("bank")
	  @Produces(MediaType.TEXT_PLAIN)
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	  public String saveBank(@FormParam("banks") String bank){
		  Gson gson = new Gson();
		  Bank sms = gson.fromJson(bank,Bank.class);
		  RegistryDao dao = new RegistryDao();
		  dao.saveUpdatesBank(sms);
		  return gson.toJson(true);
	  }
		/***
		Delete user 
	   */

	  @DELETE
	  @Path("bank")
	  @Produces(MediaType.TEXT_PLAIN)
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	  public String deleteBank(@FormParam("bankobj") String bankobj){
		  Gson gson = new Gson();
		  try{
			  Bank sm = gson.fromJson(bankobj,Bank.class);
			  RegistryDao dao = new RegistryDao();
			  dao.deleteBank(sm);
			  return gson.toJson(true);
		  }catch(Exception e){
			  return gson.toJson("");
		  }
	  }
	  
	  
	  /*****
	   * 
	   * Product
	   * @return
	   */
	  @POST
	  @Path("products")
 	  @Produces(MediaType.APPLICATION_JSON)
	  @Consumes(MediaType.APPLICATION_JSON) 
	  public String getProductList(@Context HttpServletRequest request, String data){
		Gson gson = new Gson();
		RegistryDao dao = new RegistryDao();
		SelectProductsFilter filter = gson.fromJson(data, SelectProductsFilter.class);
		User loggeduser = HibernateUtils.getUserFromSession(request);
		return gson.toJson(dao.getProductList(filter,loggeduser));
	  }
	  @POST
	  @Path("public/products/{key}")
 	  @Produces(MediaType.APPLICATION_JSON)
	  @Consumes(MediaType.APPLICATION_JSON) 
	  public String getProductPublicList(@Context HttpServletRequest request, String data,@PathParam("key") String key){
		Gson gson = new Gson();
		RegistryDao dao = new RegistryDao();
		SelectProductsFilter filter = gson.fromJson(data, SelectProductsFilter.class);
		User loggeduser = HibernateUtils.getUserFromSession(request);
		return gson.toJson(dao.getProductPublicList(filter,key));
	  }
	  @GET
	  @Path("product")
 	  @Produces(MediaType.TEXT_HTML)
	  public String getProductList(@Context HttpServletRequest request){
		Gson gson = new Gson();
		RegistryDao dao = new RegistryDao();
		User loggeduser = HibernateUtils.getUserFromSession(request);
		return gson.toJson(dao.getProductList(loggeduser));
	  }
	  @POST
	  @Path("public/product/{key}")
 	  @Produces(MediaType.APPLICATION_JSON)
	  public String getProductPublicList(String data,@PathParam("key") String key){
		Gson gson = new Gson();
		RegistryDao dao = new RegistryDao();
		//User loggeduser = HibernateUtils.getUserFromSession(request);
		SelectProductsFilter sf = gson.fromJson(data, SelectProductsFilter.class);
		return gson.toJson(dao.getProductPublicList(sf,key));
	  }
	  @GET
	  @Path("product/pages/{size}")
	  @Produces(MediaType.TEXT_HTML)
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  public String productPages(@Context HttpServletRequest request,@PathParam("size") int size) {
		Gson gson = new Gson();
		RegistryDao dao = new RegistryDao();
		User loggeduser = HibernateUtils.getUserFromSession(request);
		return gson.toJson(dao.getListPagesNumber(size,loggeduser));
	  }
	  @GET
<<<<<<< HEAD
	  @Path("public/product/pages/{size}/{key}")
	  @Produces(MediaType.TEXT_HTML)
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  public String productPublicPages(@Context HttpServletRequest request,@PathParam("size") int size,@PathParam("key") String key) {
		Gson gson = new Gson();
		RegistryDao dao = new RegistryDao();
		User loggeduser = HibernateUtils.getUserFromSession(request);
		return gson.toJson(dao.getListPublicPagesNumber(size,key));
	  }
	  @GET
=======
>>>>>>> refs/remotes/invoicecreator/styleapp
	  @Path("listproduct/pages/{size}/{idlist}")
	  @Produces(MediaType.TEXT_HTML)
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  public String productListPages(@Context HttpServletRequest request,@PathParam("size") int size,@PathParam("idlist") int idlist) {
		Gson gson = new Gson();
		RegistryDao dao = new RegistryDao();
		return gson.toJson(dao.getListProductsPagesNumber(size,idlist));
	  }
	  /***
		Get Single user
      */
	  @GET
	  @Path("product/{idproduct}")
	  @Produces(MediaType.TEXT_HTML)
	  public String singleProduct(@PathParam("idproduct") int id) {
		Gson gson = new Gson();
		RegistryDao dao = new RegistryDao();
		Product product = new Product();
		product = dao.getSingleProduct(id);
		return gson.toJson(product);
	  }
	  /***
	   * Search Product
	   */
	  @GET
	  @Path("product/search/{value}")
	  @Produces(MediaType.TEXT_HTML)
	  public String searchProducts(@Context HttpServletRequest request,@PathParam("value") String value) {
		Gson gson = new Gson();
		RegistryDao dao = new RegistryDao();
		User loggeduser = HibernateUtils.getUserFromSession(request);
		return gson.toJson(dao.searchProducts(value,loggeduser));
	  }
	  @POST
	  @Path("product/search/{value}")
	  @Produces(MediaType.APPLICATION_JSON)
	  public String searchProductsHead(@Context HttpServletRequest request,@PathParam("value") String value,String data) {
		Gson gson = new Gson();
		RegistryDao dao = new RegistryDao();
		User loggeduser = HibernateUtils.getUserFromSession(request);
		Head h = gson.fromJson(data, Head.class);
		return gson.toJson(dao.searchProducts(value,h,loggeduser));
	  }
	  @POST
	  @Path("product/filtered/")
	  @Produces(MediaType.TEXT_HTML)
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	  public String getFilteredProductsList(@Context HttpServletRequest request,@FormParam("filter") String filter) {
		Gson gson = new Gson();
		RegistryDao dao = new RegistryDao();
		SelectProductsFilter f = gson.fromJson(filter, SelectProductsFilter.class);
		User loggeduser = HibernateUtils.getUserFromSession(request);
		return gson.toJson(dao.getProductFilteredList(f,loggeduser));
	  }
	  @POST
	  @Path("product/increment/")
	  @Produces(MediaType.APPLICATION_JSON)
	  @Consumes(MediaType.APPLICATION_JSON)
	  public String incrementProductsList(@Context HttpServletRequest request, String data) {
		Gson gson = new Gson();
		RegistryDao dao = new RegistryDao();
		SelectProductsFilter f = gson.fromJson(data, SelectProductsFilter.class);
		User loggeduser = HibernateUtils.getUserFromSession(request);
		return gson.toJson(dao.calculateIncrementProducts(f,loggeduser));
	  }
	  /***
		Get Single product
    */
	  @POST
	  @Path("product/code/{code}/{idlist}")
	  @Produces(MediaType.TEXT_HTML)
	  public String singleCodeProduct(@Context HttpServletRequest request,@PathParam("code") String code,@PathParam("idlist") int idlist,@FormParam("head") String head) {
		Gson gson = new Gson();
		RegistryDao dao = new RegistryDao();
		Product product = new Product();
		Head headToSet = gson.fromJson(head, Head.class);
		User loggeduser = HibernateUtils.getUserFromSession(request);
		product = dao.getSingleCodeProduct(code,idlist,headToSet,loggeduser);
		
		return gson.toJson(product);
	  }
	  @PUT
	  @Path("product")
	  @Produces(MediaType.APPLICATION_JSON)
	  @Consumes(MediaType.APPLICATION_JSON) 
	  public String saveProduct(@Context HttpServletRequest request, String data){
		  Gson gson = new Gson();
		  Product sms = gson.fromJson(data,Product.class);
		  RegistryDao dao = new RegistryDao();
		  User loggeduser = HibernateUtils.getUserFromSession(request);
		  return gson.toJson(dao.saveUpdatesProduct(sms,loggeduser));
	  }
		/***
		Delete user 
	   */

	  @DELETE
	  @Path("product")
	  @Produces(MediaType.TEXT_PLAIN)
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	  public String deleteProduct(@FormParam("productobj") String productobj){
		  Gson gson = new Gson();
		  try{
			  Product sm = gson.fromJson(productobj,Product.class);
			  RegistryDao dao = new RegistryDao();
			  dao.deleteProduct(sm);
			  return gson.toJson(true);
		  }catch(Exception e){
			  return gson.toJson("");
		  }
	  }
	  
	  @DELETE
	  @Path("productum")
	  @Produces(MediaType.TEXT_PLAIN)
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	  public String deleteUMProduct(@FormParam("productobj") String productobj){
		  Gson gson = new Gson();
		  try{
			  UnitMeasureProduct sm = gson.fromJson(productobj,UnitMeasureProduct.class);
			  RegistryDao dao = new RegistryDao();
			  dao.deleteUMProduct(sm);
			  return gson.toJson(true);
		  }catch(Exception e){
			  return gson.toJson("");
		  }
	  }
	  @DELETE
	  @Path("productlist")
	  @Produces(MediaType.TEXT_PLAIN)
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	  public String deleteListProduct(@FormParam("productobj") String productobj){
		  Gson gson = new Gson();
		  try{
			  ListProduct sm = gson.fromJson(productobj,ListProduct.class);
			  RegistryDao dao = new RegistryDao();
			  dao.deleteListProduct(sm);
			  return gson.toJson(true);
		  }catch(Exception e){
			  return gson.toJson("");
		  }
	  }
	  /*****
	   * 
	   * Bist
	   * @return
	   */
	  @GET
	  @Path("list")
 	  @Produces(MediaType.TEXT_PLAIN)
	  public String getListList(@Context HttpServletRequest request){
		Gson gson = new Gson();
		RegistryDao dao = new RegistryDao();
		User loggeduser = HibernateUtils.getUserFromSession(request);
		return gson.toJson(dao.getListList( loggeduser));
	  }
	  @POST
	  @Path("list/noproduct")
 	  @Produces(MediaType.TEXT_PLAIN)
	  public String getListNoProduct(@Context HttpServletRequest request,@FormParam("product") String product){
		Gson gson = new Gson();
		RegistryDao dao = new RegistryDao();
		User loggeduser = HibernateUtils.getUserFromSession(request);
		Product p = gson.fromJson(product, Product.class);
		return gson.toJson(dao.getListListNoProduct( loggeduser,p));
	  }
	  /***
		Get Single user
    */
	  @GET
	  @Path("list/{idlist}")
	  @Produces(MediaType.TEXT_HTML)
	  public String singleList(@PathParam("idlist") int id) {
		Gson gson = new Gson();
		RegistryDao dao = new RegistryDao();
		List list = new List();
		list = dao.getSingleList(id);
		return gson.toJson(list);
	  }
	  @POST
	  @Path("list/{idlist}")
	  @Produces(MediaType.APPLICATION_JSON)
	  public String singleListFiltered(@PathParam("idlist") int id,String data,@Context HttpServletRequest request) {
		  User loggeduser = HibernateUtils.getUserFromSession(request);
		  Gson gson = new Gson();
		  RegistryDao dao = new RegistryDao();
		  List list = new List();
		  SelectProductsFilter filter = gson.fromJson(data, SelectProductsFilter.class);
		  list = dao.getSingleList(id, filter, loggeduser);
		  return gson.toJson(list);
	  }
	  @PUT
	  @Path("list")
	  @Produces(MediaType.TEXT_PLAIN)
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	  public String saveList(@Context HttpServletRequest request,@FormParam("lists") String list){
		  Gson gson = new Gson();
		  NewList sms = gson.fromJson(list,NewList.class);
		  RegistryDao dao = new RegistryDao();
		  User loggeduser = HibernateUtils.getUserFromSession(request);
		  return gson.toJson(dao.saveUpdatesList(sms, loggeduser));
	  }
		/***
		Delete user 
	   */

	  @DELETE
	  @Path("list")
	  @Produces(MediaType.TEXT_PLAIN)
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	  public String deleteList(@FormParam("listobj") String listobj){
		  Gson gson = new Gson();
		  try{
			  List sm = gson.fromJson(listobj,List.class);
			  RegistryDao dao = new RegistryDao();
			  dao.deleteList(sm);
			  return gson.toJson(true);
		  }catch(Exception e){
			  return gson.toJson("");
		  }
	  }
	  @GET
	  @Path("list/customer/{idcustomer}")
 	  @Produces(MediaType.TEXT_PLAIN)
	  public String getPriceCustomerList(@PathParam("idcustomer") int id){
		Gson gson = new Gson();
		RegistryDao dao = new RegistryDao();
		return gson.toJson(dao.getCustomerPriceList(id));
	  }
	  
	  /*****
	   * 
	   * Product
	   * @return
	   */
	  @GET
	  @Path("customer")
 	  @Produces(MediaType.TEXT_PLAIN)
	  public String getCustomerList(@Context HttpServletRequest request){
		Gson gson = new Gson();
		RegistryDao dao = new RegistryDao();
		User loggeduser = HibernateUtils.getUserFromSession(request);
		return gson.toJson(dao.getCustomerList(loggeduser));
	  }
	  @POST
	  @Path("customer")
 	  @Produces(MediaType.TEXT_PLAIN)
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	  public String getCustomerListSelected(@Context HttpServletRequest request,@FormParam("filter") String filter){
		Gson gson = new Gson();
		RegistryDao dao = new RegistryDao();
		SelectCustomerList filterObj = gson.fromJson(filter, SelectCustomerList.class);
		User loggeduser = HibernateUtils.getUserFromSession(request);
		return gson.toJson(dao.getCustomerList(filterObj,loggeduser));
	  }
	  /***
		Get Single user
	   */
	  @GET
	  @Path("customer/{idcustomer}")
	  @Produces(MediaType.TEXT_HTML)
	  public String singleCustomer(@PathParam("idcustomer") int id) {
		Gson gson = new Gson();
		RegistryDao dao = new RegistryDao();
		Customer customer = new Customer();
		customer = dao.getSingleCustomer(id);
		return gson.toJson(customer);
	  }
	  @PUT
	  @Path("customer/user")
	  @Produces(MediaType.APPLICATION_JSON)
	  @Consumes(MediaType.APPLICATION_JSON) 
	  public String createUserCustomer(@Context HttpServletRequest request,String data){
		  Gson gson = new Gson();
		  UserCustomer sms = gson.fromJson(data,UserCustomer.class);
		  RegistryDao dao = new RegistryDao();
		  User loggeduser = HibernateUtils.getUserFromSession(request);
		  return gson.toJson(dao.createUserCustomer(loggeduser,sms));
	  }
	  @PUT
	  @Path("customer")
	  @Produces(MediaType.TEXT_PLAIN)
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	  public String saveCustomer(@Context HttpServletRequest request,@FormParam("customers") String customer){
		  Gson gson = new Gson();
		  Customer sms = gson.fromJson(customer,Customer.class);
		  RegistryDao dao = new RegistryDao();
		  User loggeduser = HibernateUtils.getUserFromSession(request);
		  return gson.toJson(dao.saveUpdatesCustomer(sms,loggeduser));
	  }
		/***
		Delete user 
	   */

	  @DELETE
	  @Path("customer")
	  @Produces(MediaType.TEXT_PLAIN)
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	  public String deleteCustomer(@FormParam("customerobj") String customerobj){
		  Gson gson = new Gson();
		  try{
			  Customer sm = gson.fromJson(customerobj,Customer.class);
			  RegistryDao dao = new RegistryDao();
			  dao.deleteCustomer(sm);
			  return gson.toJson(true);
		  }catch(Exception e){
			  return gson.toJson("");
		  }
	  }
	  
	  
	  
	  
	  /*****
	   * 
	   * Product
	   * @return
	   */
	  @GET
	  @Path("destination")
 	  @Produces(MediaType.TEXT_PLAIN)
	  public String getDestinationList(){
		Gson gson = new Gson();
		RegistryDao dao = new RegistryDao();
		return gson.toJson(dao.getDestinationList());
	  }
	  /***
		Get Single user
    */
	  @GET
	  @Path("destination/{iddestination}")
	  @Produces(MediaType.TEXT_HTML)
	  public String singleDestination(@PathParam("iddestination") int id) {
		Gson gson = new Gson();
		RegistryDao dao = new RegistryDao();
		Destination destination = new Destination();
		destination = dao.getSingleDestination(id);
		return gson.toJson(destination);
	  }
	  @GET
	  @Path("destination/customer/{idcustomer}")
 	  @Produces(MediaType.TEXT_PLAIN)
	  public String getDestinationCustomerList(@PathParam("idcustomer") int id){
		Gson gson = new Gson();
		RegistryDao dao = new RegistryDao();
		return gson.toJson(dao.getCustomerDestinationList(id));
	  }
	  @PUT
	  @Path("destination")
	  @Produces(MediaType.TEXT_PLAIN)
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	  public String saveDestination(@FormParam("destinations") String destination){
		  Gson gson = new Gson();
		  Destination sms = gson.fromJson(destination,Destination.class);
		  RegistryDao dao = new RegistryDao();
		  return gson.toJson(dao.saveUpdatesDestination(sms));
	  }
		/***
		Delete user 
	   */

	  @DELETE
	  @Path("destination")
	  @Produces(MediaType.TEXT_PLAIN)
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	  public String deleteDestination(@FormParam("destinationobj") String destinationobj){
		  Gson gson = new Gson();
		  try{
			  Destination sm = gson.fromJson(destinationobj,Destination.class);
			  RegistryDao dao = new RegistryDao();
			  dao.deleteDestination(sm);
			  return gson.toJson(true);
		  }catch(Exception e){
			  return gson.toJson("");
		  }
	  }

	  
	  
	  
	  
	  /*****
	   * 
	   * Product
	   * @return
	   */
	  @GET
	  @Path("supplier")
 	  @Produces(MediaType.TEXT_PLAIN)
	  public String getSupplierList(){
		Gson gson = new Gson();
		RegistryDao dao = new RegistryDao();
		return gson.toJson(dao.getSupplierList());
	  }
	  @POST
	  @Path("supplier")
 	  @Produces(MediaType.TEXT_PLAIN)
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	  public String getSupplierList(@FormParam("filter") String filter){
		Gson gson = new Gson();
		RegistryDao dao = new RegistryDao();
		SelectSupplierList filterObj = gson.fromJson(filter, SelectSupplierList.class);
		return gson.toJson(dao.getSupplierList(filterObj));
	  }
	  /***
		Get Single user
    */
	  @GET
	  @Path("supplier/{idsupplier}")
	  @Produces(MediaType.TEXT_HTML)
	  public String singleSupplier(@PathParam("idsupplier") int id) {
		Gson gson = new Gson();
		RegistryDao dao = new RegistryDao();
		Supplier supplier = new Supplier();
		supplier = dao.getSingleSupplier(id);
		return gson.toJson(supplier);
	  }
	  @PUT
	  @Path("supplier")
	  @Produces(MediaType.TEXT_PLAIN)
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	  public String saveSupplier(@FormParam("suppliers") String supplier){
		  Gson gson = new Gson();
		  Supplier sms = gson.fromJson(supplier,Supplier.class);
		  RegistryDao dao = new RegistryDao();
		  return gson.toJson(dao.saveUpdatesSupplier(sms));
	  }
		/***
		Delete user 
	   */

	  @DELETE
	  @Path("supplier")
	  @Produces(MediaType.TEXT_PLAIN)
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	  public String deleteSupplier(@FormParam("supplierobj") String supplierobj){
		  Gson gson = new Gson();
		  try{
			  Supplier sm = gson.fromJson(supplierobj,Supplier.class);
			  RegistryDao dao = new RegistryDao();
			  dao.deleteSupplier(sm);
			  return gson.toJson(true);
		  }catch(Exception e){
			  return gson.toJson("");
		  }
	  }
	  
	  
	  /*****
	   * 
	   * Product
	   * @return
	   */
	  @GET
	  @Path("transporter")
 	  @Produces(MediaType.TEXT_PLAIN)
	  public String getTransporterList(){
		Gson gson = new Gson();
		RegistryDao dao = new RegistryDao();
		return gson.toJson(dao.getTransporterList());
	  }
	  /***
		Get Single user
    */
	  @GET
	  @Path("transporter/{idtransporter}")
	  @Produces(MediaType.TEXT_HTML)
	  public String singleTransporter(@PathParam("idtransporter") int id) {
		Gson gson = new Gson();
		RegistryDao dao = new RegistryDao();
		Transporter transporter = new Transporter();
		transporter = dao.getSingleTransporter(id);
		return gson.toJson(transporter);
	  }
	  @PUT
	  @Path("transporter")
	  @Produces(MediaType.TEXT_PLAIN)
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	  public String saveTransporter(@FormParam("transporters") String transporter){
		  Gson gson = new Gson();
		  Transporter sms = gson.fromJson(transporter,Transporter.class);
		  RegistryDao dao = new RegistryDao();
		  return gson.toJson(dao.saveUpdatesTransporter(sms));
	  }
	  @PUT
	  @Path("transporter/user")
	  @Produces(MediaType.TEXT_PLAIN)
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	  public String createUserTransporter(@Context HttpServletRequest request,@FormParam("transporters") String transporter,@FormParam("role") String role){
		  Gson gson = new Gson();
		  Transporter sms = gson.fromJson(transporter,Transporter.class);
		  Role r = gson.fromJson(role,Role.class);
		  RegistryDao dao = new RegistryDao();
		  User loggeduser = HibernateUtils.getUserFromSession(request);
		  return gson.toJson(dao.createUserTransporter(sms,loggeduser,r));
	  }
		/***
		Delete user 
	   */

	  @DELETE
	  @Path("transporter")
	  @Produces(MediaType.TEXT_PLAIN)
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	  public String deleteTransporter(@FormParam("transporterobj") String transporterobj){
		  Gson gson = new Gson();
		  try{
			  Transporter sm = gson.fromJson(transporterobj,Transporter.class);
			  RegistryDao dao = new RegistryDao();
			  dao.deleteTransporter(sm);
			  return gson.toJson(true);
		  }catch(Exception e){
			  return gson.toJson("");
		  }
	  }
	  
	  
	  @GET
	  @Path("promoter")
 	  @Produces(MediaType.TEXT_PLAIN)
	  public String getPromoterList(@Context HttpServletRequest request){
		Gson gson = new Gson();
		RegistryDao dao = new RegistryDao();
		User loggeduser = HibernateUtils.getUserFromSession(request);
		return gson.toJson(dao.getPromoterList(loggeduser));
	  }
	  @GET
	  @Path("promoter/{idpromoter}")
	  @Produces(MediaType.TEXT_HTML)
	  public String singlePromoter(@PathParam("idpromoter") int id) {
		Gson gson = new Gson();
		RegistryDao dao = new RegistryDao();
		Promoter promoter = new Promoter();
		promoter = dao.getSinglePromoter(id);
		return gson.toJson(promoter);
	  }
	  
	  @PUT
	  @Path("promoter")
	  @Produces(MediaType.TEXT_PLAIN)
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	  public String savePromoter(@Context HttpServletRequest request,@FormParam("promoters") String promoter){
		  Gson gson = new Gson();
		  Promoter sms = gson.fromJson(promoter,Promoter.class);
		  RegistryDao dao = new RegistryDao();
		  User loggeduser = HibernateUtils.getUserFromSession(request);
		  return gson.toJson(dao.saveUpdatesPromoter(sms,loggeduser));
	  }
		/***
		Delete user 
	   */

	  @DELETE
	  @Path("promoter")
	  @Produces(MediaType.TEXT_PLAIN)
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	  public String deletePromoter(@FormParam("promoterobj") String promoterobj){
		  Gson gson = new Gson();
		  try{
			  Promoter sm = gson.fromJson(promoterobj,Promoter.class);
			  RegistryDao dao = new RegistryDao();
			  dao.deletePromoter(sm);
			  return gson.toJson(true);
		  }catch(Exception e){
			  return gson.toJson("");
		  }
	  }
	  @PUT
	  @Path("promoter/user")
	  @Produces(MediaType.APPLICATION_JSON)
	  @Consumes(MediaType.APPLICATION_JSON) 
	  public String createUserPromoter(@Context HttpServletRequest request,String data){
		  Gson gson = new Gson();
		  UserPromoter up = gson.fromJson(data,UserPromoter.class);
		  RegistryDao dao = new RegistryDao();
		  User loggeduser = HibernateUtils.getUserFromSession(request);
		  return gson.toJson(dao.createUserPromoter(loggeduser,up));
	  }
<<<<<<< HEAD
	  @GET
	  @Path("paymentsolution")
 	  @Produces(MediaType.TEXT_PLAIN)
	  public String getPaymentSolutionList(){
		Gson gson = new Gson();
		RegistryDao dao = new RegistryDao();
		return gson.toJson(dao.getPaymentSolutionList());
	  }
=======
>>>>>>> refs/remotes/invoicecreator/styleapp
}
