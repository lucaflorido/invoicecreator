package it.progess.invoicecreator.dao;



import it.progess.invoicecreator.vo.GECOError;
import it.progess.invoicecreator.vo.GECOObject;
import it.progess.invoicecreator.vo.GECOSuccess;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletContext;

import net.sf.jasperreports.engine.JasperCompileManager;

import com.sun.jersey.core.header.FormDataContentDisposition;

public class UploadFileDao {
	public GECOObject uploadFile(InputStream fileInputStream,FormDataContentDisposition contentDispositionHeader,ServletContext context) {
	 	String filePath = "import/" + contentDispositionHeader.getFileName();
	    GECOObject obj = new GECOObject();
	    // save the file to the server
	    obj = saveFile(fileInputStream, context.getRealPath(filePath),filePath);
	 return obj;
    }
	private GECOObject saveFile(InputStream uploadedInputStream,
            String serverLocation,String filePath) {
		 
        try {
	            OutputStream outpuStream = new FileOutputStream(new File(serverLocation));
	            int read = 0;
            byte[] bytes = new byte[1024];
	 
            outpuStream = new FileOutputStream(new File(serverLocation));
	            while ((read = uploadedInputStream.read(bytes)) != -1) {
	                outpuStream.write(bytes, 0, read);
	            }
            outpuStream.flush();
	            outpuStream.close();
        } catch (IOException e) {
	            e.printStackTrace();
	            return new GECOError("SAVE REPORT", e.getMessage());
        }
        return new GECOSuccess(filePath);
	 }
}
