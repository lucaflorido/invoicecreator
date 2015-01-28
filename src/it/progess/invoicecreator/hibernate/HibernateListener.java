package it.progess.invoicecreator.hibernate;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class HibernateListener implements ServletContextListener {
		 
	  public void contextInitialized(ServletContextEvent event) {
	    HibernateUtils.getSessionFactory(); 
	  }
	 
	  public void contextDestroyed(ServletContextEvent event) {
	    HibernateUtils.getSessionFactory().close();
	  }
		
}
