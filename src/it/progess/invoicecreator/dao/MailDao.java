package it.progess.invoicecreator.dao;

import java.util.ArrayList;
import java.util.List;

import javamailhelper.config.SMTPServerConfiguration;
import javamailhelper.message.EMailMessage;
import javamailhelper.runner.EMailSender;

import javax.mail.MessagingException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import it.progess.invoicecreator.hibernate.HibernateUtils;
import it.progess.invoicecreator.pojo.TblMailText;
import it.progess.invoicecreator.pojo.TblStorage;
import it.progess.invoicecreator.pojo.TblUser;
import it.progess.invoicecreator.properties.ICParameter;
import it.progess.invoicecreator.properties.MailParameter;
import it.progess.invoicecreator.vo.GECOError;
import it.progess.invoicecreator.vo.GECOObject;
import it.progess.invoicecreator.vo.GECOSuccess;
import it.progess.invoicecreator.vo.Head;
import it.progess.invoicecreator.vo.MailConfig;
import it.progess.invoicecreator.vo.PrintUrl;
import it.progess.invoicecreator.vo.Product;
import it.progess.invoicecreator.vo.Storage;
import it.progess.invoicecreator.vo.User;

public class MailDao {
	private String testResponseEmail = "lucaflorido@gmail.com"; // "a1d1@libero.it"; //"lucaflorido@gmail.com";
	public GECOObject testEmail(User user,MailConfig conf ){
		if (conf == null){
			return new GECOError("MAIL", "Mail non configurata");
		}
		EMailMessage message = new EMailMessage(conf.getEmail(),user.getCompany().getContact().getEmail1(),"Test Email","Verifica invio email");
		SMTPServerConfiguration config = new SMTPServerConfiguration(String.valueOf(conf.isAuth()),String.valueOf(conf.isStarttls()),conf.getHost());
		if (conf.getPort() != null && conf.getPort().equals("") == false){
			config.setPort(conf.getPort());
		}
		try{
			if (conf.isPec() == false){
				EMailSender.send(message, config, conf.getPassword(),false);
			}else{
				EMailSender.sendpec(message, config, conf.getPassword(),false);
			}
			System.out.println("Email success");
		}catch(MessagingException ex){
			return new GECOError("MAIL",ex.getMessage());
		}
		return new GECOSuccess();
	}
	public GECOObject documentEmail(ServletContext context,User user,Head head,MailConfig conf  ){
		if (conf == null){
			return new GECOError("MAIL", "Mail non configurata");
		}
		if (head.getCustomer().getContact().getEmail1() == null || head.getCustomer().getContact().getEmail1().equals("") == true ){
			return new GECOError("MAIL", "Email del cliente non definita");
		}
		EMailMessage message = new EMailMessage(conf.getEmail(),head.getCustomer().getContact().getEmail1(),"Document","Invio del documento "+head.getDocument().getDescription());
		SMTPServerConfiguration config = new SMTPServerConfiguration(String.valueOf(conf.isAuth()),String.valueOf(conf.isStarttls()),conf.getHost());
		if (conf.getPort() != null && conf.getPort().equals("") == false){
			config.setPort(conf.getPort());
		}
		try{
			PrintUrl pu = new PrinterDao().getSingleDocumentPath(context, head.getIdHead(), user);
			String filename = pu.getUrl();
			if (conf.isPec() == false){
				EMailSender.send(message, config, conf.getPassword(),filename,false);
			}else{
				EMailSender.sendpec(message, config, conf.getPassword(),filename,false);
			}
			System.out.println("Email success");
		}catch(MessagingException ex){
			return new GECOError("MAIL",ex.getMessage());
		}
		return new GECOSuccess();
	}
	public GECOObject sendNewCustomerUser(User user,User newUser,String param,HttpServletRequest request){
		/**REAL CONFIGURATION**/
		/*if (conf == null){
			return new GECOError("MAIL", "Mail non configurata");
		}
		EMailMessage message = new EMailMessage(conf.getEmail(),head.getCustomer().getContact().getEmail1(),"Document","Invio del documento "+head.getDocument().getDescription());
		SMTPServerConfiguration config = new SMTPServerConfiguration(String.valueOf(conf.isAuth()),String.valueOf(conf.isStarttls()),conf.getHost());
		if (conf.getPort() != null && conf.getPort().equals("") == false){
			config.setPort(conf.getPort());
		}*/
		/*TODO calculate the main domain*/
		ArrayList<String> values = new ArrayList<String>();
		values.add(newUser.getName()+" "+newUser.getSurname());
		values.add(newUser.getUsername());
		values.add(newUser.getPassword());
		values.add(request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+ICParameter.SECOND_DOMAIN+"activate.html?code="+newUser.getCode());
		TblMailText tmt = getMailProperties(param,user.getCompany().getCode());
		if(tmt == null){
			return new GECOError("MAIL", "Testo Mail non configurato nel sistema");
		}
		EMailMessage message = new EMailMessage("lucaflorido@hotmail.com",testResponseEmail,tmt.getObject(),fillMailText(tmt.getText(), values));
		SMTPServerConfiguration config = new SMTPServerConfiguration("true","true","smtp.live.com");
		config.setPort("587");
		try{
			EMailSender.send(message, config, "svnf0rl0s",true);
			System.out.println("Email success");
		}catch(MessagingException ex){
			System.out.println(ex.getMessage());
		}
		return new GECOSuccess();
	}
	
	public GECOObject sendNewCustomerUser(String key,User newUser,String param,HttpServletRequest request){
		/**REAL CONFIGURATION**/
		/*if (conf == null){
			return new GECOError("MAIL", "Mail non configurata");
		}
		EMailMessage message = new EMailMessage(conf.getEmail(),head.getCustomer().getContact().getEmail1(),"Document","Invio del documento "+head.getDocument().getDescription());
		SMTPServerConfiguration config = new SMTPServerConfiguration(String.valueOf(conf.isAuth()),String.valueOf(conf.isStarttls()),conf.getHost());
		if (conf.getPort() != null && conf.getPort().equals("") == false){
			config.setPort(conf.getPort());
		}*/
		/*TODO calculate the main domain*/
		ArrayList<String> values = new ArrayList<String>();
		values.add(newUser.getName()+" "+newUser.getSurname());
		values.add(newUser.getUsername());
		values.add(newUser.getPassword());
		values.add(request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+ICParameter.SECOND_DOMAIN+"activate.html?code="+newUser.getCode());
		TblMailText tmt = getMailProperties(param,key);
		if(tmt == null){
			return new GECOError("MAIL", "Testo Mail non configurato nel sistema");
		}
		EMailMessage message = new EMailMessage("lucaflorido@hotmail.com",testResponseEmail,tmt.getObject(),fillMailText(tmt.getText(), values));
		SMTPServerConfiguration config = new SMTPServerConfiguration("true","true","smtp.live.com");
		config.setPort("587");
		try{
			EMailSender.send(message, config, "svnf0rl0s",true);
			System.out.println("Email success");
		}catch(MessagingException ex){
			System.out.println(ex.getMessage());
		}
		return new GECOSuccess();
	}
	public GECOObject sendOrderOnline(ServletContext context,HttpServletRequest request,Head head,User user,boolean newuser  ){
		/*if (conf == null){
			return new GECOError("MAIL", "Mail non configurata");
		}*/
		if (head.getCustomer().getContact().getEmail1() == null || head.getCustomer().getContact().getEmail1().equals("") == true ){
			return new GECOError("MAIL", "Email del cliente non definita");
		}
		/*EMailMessage message = new EMailMessage(conf.getEmail(),head.getCustomer().getContact().getEmail1(),"Document","Invio del documento "+head.getDocument().getDescription());
		SMTPServerConfiguration config = new SMTPServerConfiguration(String.valueOf(conf.isAuth()),String.valueOf(conf.isStarttls()),conf.getHost());
		if (conf.getPort() != null && conf.getPort().equals("") == false){
			config.setPort(conf.getPort());
		}*/
		ArrayList<String> values = new ArrayList<String>();
		values.add(head.getCustomer().getCustomername());
		String mailtype = MailParameter.NEW_ONLINE_ORDER_MAIL;
		if (newuser == true){
			values.add(request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+ICParameter.SECOND_DOMAIN+"activate.html?code="+user.getCode());
			mailtype = MailParameter.NEW_USER_NEW_ORDER_ONLINE;
		}
		
		TblMailText tmt = getMailProperties(mailtype,user.getCompany().getCode());
		EMailMessage message = new EMailMessage("lucaflorido@hotmail.com",testResponseEmail,tmt.getObject(),fillMailText(tmt.getText(), values));
		SMTPServerConfiguration config = new SMTPServerConfiguration("true","true","smtp.live.com");
		config.setPort("587");
		try{
			PrintUrl pu = new PrinterDao().getSingleDocumentPath(context, head.getIdHead(), user);
			String filename = pu.getUrl();
			/*if (conf.isPec() == false){
				EMailSender.send(message, config, conf.getPassword(),filename,false);
			}else{
				EMailSender.sendpec(message, config, conf.getPassword(),filename,false);
			}*/
			EMailSender.send(message, config, "svnf0rl0s",filename,true);
			System.out.println("Email success");
		}catch(MessagingException ex){
			return new GECOError("MAIL",ex.getMessage());
		}
		return new GECOSuccess();
	}
	
	public GECOObject sendEcRecoverUsername(TblUser u){
		/**REAL CONFIGURATION**/
		/*if (conf == null){
			return new GECOError("MAIL", "Mail non configurata");
		}
		EMailMessage message = new EMailMessage(conf.getEmail(),head.getCustomer().getContact().getEmail1(),"Document","Invio del documento "+head.getDocument().getDescription());
		SMTPServerConfiguration config = new SMTPServerConfiguration(String.valueOf(conf.isAuth()),String.valueOf(conf.isStarttls()),conf.getHost());
		if (conf.getPort() != null && conf.getPort().equals("") == false){
			config.setPort(conf.getPort());
		}*/
		/*TODO calculate the main domain*/
		ArrayList<String> values = new ArrayList<String>();
		values.add(u.getName()+" "+u.getSurname());
		values.add(u.getUsername());
		TblMailText tmt = getMailProperties(MailParameter.EC_RECOVER_USERNAME,u.getCompany().getCode());
		if(tmt == null){
			return new GECOError("MAIL", "Testo Mail non configurato nel sistema");
		}
		
		EMailMessage message = new EMailMessage("lucaflorido@hotmail.com",testResponseEmail,tmt.getObject(),fillMailText(tmt.getText(), values));
		SMTPServerConfiguration config = new SMTPServerConfiguration("true","true","smtp.live.com");
		config.setPort("587");
		try{
			EMailSender.send(message, config, "svnf0rl0s",true);
			System.out.println("Email success");
		}catch(MessagingException ex){
			System.out.println(ex.getMessage());
			return new GECOError("MAIL", "Errore nell'invio della mail");
		}
		return new GECOSuccess();
	}
	public GECOObject sendEcRecoverPassword(TblUser u,HttpServletRequest request){
		/**REAL CONFIGURATION**/
		/*if (conf == null){
			return new GECOError("MAIL", "Mail non configurata");
		}
		EMailMessage message = new EMailMessage(conf.getEmail(),head.getCustomer().getContact().getEmail1(),"Document","Invio del documento "+head.getDocument().getDescription());
		SMTPServerConfiguration config = new SMTPServerConfiguration(String.valueOf(conf.isAuth()),String.valueOf(conf.isStarttls()),conf.getHost());
		if (conf.getPort() != null && conf.getPort().equals("") == false){
			config.setPort(conf.getPort());
		}*/
		/*TODO calculate the main domain*/
		ArrayList<String> values = new ArrayList<String>();
		values.add(u.getName()+" "+u.getSurname());
		values.add(request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+ICParameter.SECOND_DOMAIN+"#/ecpassword?uid="+u.getCode());
		TblMailText tmt = getMailProperties(MailParameter.EC_RECOVER_PASSWORD,u.getCompany().getCode());
		if(tmt == null){
			return new GECOError("MAIL", "Testo Mail non configurato nel sistema");
		}
		
		EMailMessage message = new EMailMessage("lucaflorido@hotmail.com",testResponseEmail,tmt.getObject(),fillMailText(tmt.getText(), values));
		SMTPServerConfiguration config = new SMTPServerConfiguration("true","true","smtp.live.com");
		config.setPort("587");
		try{
			EMailSender.send(message, config, "svnf0rl0s",true);
			System.out.println("Email success");
		}catch(MessagingException ex){
			System.out.println(ex.getMessage());
			return new GECOError("MAIL", "Errore nell'invio della mail");
		}
		return new GECOSuccess();
	}
	private TblMailText getMailProperties(String code,String key){
		
		Session session = HibernateUtils.getSessionFactory().openSession();
		TblMailText mt = null;
		try{
			Criteria cr = session.createCriteria(TblMailText.class,"mail");
			cr.add(Restrictions.eq("mail.code",code));
			cr.createAlias("mail.company", "company");
			cr.add(Restrictions.eq("company.code",key));
			List<TblMailText> mails = cr.list();
			if (mails.size() > 0){
				mt = mails.get(0);
			}
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}finally{
			session.close();
		}
		return mt;
	}
	private String fillMailText(String text ,ArrayList<String> values){
		String textcloned = new String(text);
		for (int i = 0; i <values.size();i++){
			String value = values.get(i);
			textcloned = textcloned.replace("{"+i+"}", value);
		}
		return textcloned;
	}
	
}
