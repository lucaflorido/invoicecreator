package it.progess.invoicecreator.daemon;

import it.progess.invoicecreator.dao.DraftDao;

import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class DraftCleaner implements ServletContextListener {
	public DraftDao d = new DraftDao();
    public void contextInitialized(ServletContextEvent sce) {
    	
        Thread th = new Thread() {
            public void run() {
                // implement daemon logic here.
            	Timer timer = new Timer();
            	TimerTask tt = new TimerTask() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						//System.out.println("ESEGUITA SERVLET");
						d.deleteOldDraft();
					}
				};
				timer.schedule(tt, 5000,120000);
            }
        };
        th.setDaemon(true);
        th.start();
    }

    public void contextDestroyed(ServletContextEvent sce) {
        // you could notify your thread you're shutting down if 
        // you need it to clean up after itself
    }

}
