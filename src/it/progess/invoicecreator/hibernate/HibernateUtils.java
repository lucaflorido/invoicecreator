package it.progess.invoicecreator.hibernate;

import it.progess.invoicecreator.pojo.TblUser;
import it.progess.invoicecreator.vo.User;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
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
		/*TblUser tbluser = gson.fromJson(user, TblUser.class);
		User loggeduser = new User();
		if (tbluser != null){
			loggeduser.convertFromTable(tbluser);
		}*/
		User loggeduser = gson.fromJson(user, User.class);
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
	public static float calculatePercentage(float total,float percentage){
		float res =0;
		res  =total/100 *percentage;
		BigDecimal bd = new BigDecimal(res);
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        res= bd.floatValue();
		return res;
	}
	public static float calculateFromPercentage(float total,float percentage){
		float res =0;
		res  =100*total/(100 +percentage);
		BigDecimal bd = new BigDecimal(res);
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        res= bd.floatValue();
		return res;
	}

	/***
	 * 
	 * @param buy
	 * @param sell
	 * @return
	 * 
	 * Calculate percentage between two prices
	 */

	public static float calculatePercentageFromPrices(float buy,float sell){
		float diff = sell-buy;
		float res =0;
		res  =100/buy*diff;
		BigDecimal bd = new BigDecimal(res);
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        res= bd.floatValue();
		return res;
	}
	public static double calculatePercentage(double total,double percentage){
		double res =0;
		res  =total/100 *percentage;
		BigDecimal bd = new BigDecimal(res);
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        res= bd.doubleValue();
		return res;
	}
	public static double calculateFromPercentage(double total,double percentage){
		double res =0;
		res  =100*total/(100 +percentage);
		BigDecimal bd = new BigDecimal(res);
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        res= bd.doubleValue();
		return res;
	}
	public static double calculatePercentageFromPrices(double buy,double sell){
		double diff = sell-buy;
		double res =0;
		res  =100/buy*diff;
		BigDecimal bd = new BigDecimal(res);
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        res= bd.doubleValue();
		return res;
	}
}
