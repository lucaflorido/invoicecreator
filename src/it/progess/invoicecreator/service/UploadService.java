package it.progess.invoicecreator.service;


import it.progess.invoicecreator.dao.UploadFileDao;

import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Path("upload")
public class UploadService {
	@Context
	ServletContext context;
	@POST
	@Path("/file")
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
    public String uploadFile(
		            @FormDataParam("file") InputStream fileInputStream,
		            @FormDataParam("file") FormDataContentDisposition contentDispositionHeader) {
			Gson gson = new Gson(); 
	        return  gson.toJson(new UploadFileDao().uploadFile(fileInputStream, contentDispositionHeader, context));
	 
		    }
}
