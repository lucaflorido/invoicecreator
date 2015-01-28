package it.progess.invoicecreator.hibernate;

import it.progess.invoicecreator.pojo.TblUser;
import it.progess.invoicecreator.vo.User;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.google.gson.Gson;

public class HibernateUtils {
	public static String md5Java(String message){
        String digest = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(message.getBytes("UTF-8"));
           
            //converting byte array to Hexadecimal String
           StringBuilder sb = new StringBuilder(2*hash.length);
           for(byte b : hash){
               sb.append(String.format("%02x", b&0xff));
           }
          
           digest = sb.toString();
          
        } catch (UnsupportedEncodingException ex) {
            //Logger.getLogger(StringReplac.class.getName()).log(Level.SEVERE, null, ex);
        	ex.printStackTrace();
        } catch (NoSuchAlgorithmException ex) {
            //Logger.getLogger(StringReplace.class.getName()).log(Level.SEVERE, null, ex);
        	ex.printStackTrace();
        }
        return digest;
    }
	
	static {
	    try {
	      // Crea la SessionFactory from hibernate.cfg.xml
	      factory = new Configuration().configure().buildSessionFactory();
	    } catch (Throwable ex) {
	      // Make sure you log the exception, as it might be swallowed
	      System.err.println("Initial SessionFactory creation failed." + ex);
	      throw new ExceptionInInitializerError(ex);
	    }
	  }
	public static SessionFactory getSessionFactory() {
	    return factory;
	 }
	private static SessionFactory factory;
	public static Session getSession(){
		try{
			if (factory == null){
				factory = new Configuration().configure().buildSessionFactory();
			}
		}catch(Throwable ex){
			System.err.println("Failed to create sessionFactory "+ex);
			throw new ExceptionInInitializerError(ex);
		}
		//factory.getCache().evictCollectionRegions();
		return factory.getCurrentSession();
	}
	public static User getUserFromSession (HttpServletRequest request){
		HttpSession session = request.getSession();  
		String user = "";
		if (session.getAttribute("user") != null){
		   user = session.getAttribute("user").toString();	
		}
		
		Gson gson = new Gson();
		TblUser tbluser = gson.fromJson(user, TblUser.class);
		User loggeduser = new User();
		if (tbluser != null){
			loggeduser.convertFromTable(tbluser);
		}
		return loggeduser;
	}
	public static float roundfloat(float a){
		float b = Math.round(a*100.0f)/100.0f;
		return b;
	}
	public static double rounddouble(double a){
		double b = Math.round(a*100.0d)/100.0d;
		return b;
	}
}
