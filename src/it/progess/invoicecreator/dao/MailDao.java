package it.progess.invoicecreator.dao;

import javamailhelper.config.SMTPServerConfiguration;
import javamailhelper.message.EMailMessage;
import javamailhelper.runner.EMailSender;

import javax.mail.MessagingException;
import javax.servlet.ServletContext;

import it.progess.invoicecreator.vo.GECOError;
import it.progess.invoicecreator.vo.GECOObject;
import it.progess.invoicecreator.vo.GECOSuccess;
import it.progess.invoicecreator.vo.Head;
import it.progess.invoicecreator.vo.MailConfig;
import it.progess.invoicecreator.vo.PrintUrl;
import it.progess.invoicecreator.vo.User;

public class MailDao {
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
				EMailSender.send(message, config, conf.getPassword());
			}else{
				EMailSender.sendpec(message, config, conf.getPassword());
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
				EMailSender.send(message, config, conf.getPassword(),filename);
			}else{
				EMailSender.sendpec(message, config, conf.getPassword(),filename);
			}
			System.out.println("Email success");
		}catch(MessagingException ex){
			return new GECOError("MAIL",ex.getMessage());
		}
		return new GECOSuccess();
	}
}
