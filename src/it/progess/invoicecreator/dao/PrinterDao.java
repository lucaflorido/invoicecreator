package it.progess.invoicecreator.dao;


import it.progess.invoicecreator.print.PrintPriceList;
import it.progess.invoicecreator.print.PrintProductList;
import it.progess.invoicecreator.print.PrintReportOrder;
import it.progess.invoicecreator.print.PrintReportOrderSubreport;
import it.progess.invoicecreator.print.PrintSingleHead;
import it.progess.invoicecreator.properties.GECOParameter;
import it.progess.invoicecreator.vo.Company;
import it.progess.invoicecreator.vo.Document;
import it.progess.invoicecreator.vo.GECOObject;
import it.progess.invoicecreator.vo.GECOReportOrder;
import it.progess.invoicecreator.vo.GECOReportOrderCustomerQuantity;
import it.progess.invoicecreator.vo.GECOReportOrderProduct;
import it.progess.invoicecreator.vo.GECOSuccess;
import it.progess.invoicecreator.vo.Head;
import it.progess.invoicecreator.vo.ListProduct;
import it.progess.invoicecreator.vo.Product;
import it.progess.invoicecreator.vo.Row;
import it.progess.invoicecreator.vo.User;
import it.progess.invoicecreator.vo.filter.product.SelectProductsFilter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import javax.print.attribute.standard.PrinterResolution;
import javax.servlet.ServletContext;




import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class PrinterDao {
	public String printSingleDocument(ServletContext context,int id,User user){
		String documentType ="";
		try{
			//generateAshwinFriends();
			Company comp = user.getCompany();
			Head head = new DocumentDao().getSingleHead(id);
		    documentType = getReportName(head.getDocument());
			File f = new File(context.getRealPath("report/"+documentType+".jasper"));
			if(f.exists() == false){
				JasperCompileManager.compileReportToFile(context.getRealPath("report/"+documentType+".jrxml"), context.getRealPath("report/"+documentType+".jasper"));
			}
		    
			Collection<PrintSingleHead> headcoll = new ArrayList<PrintSingleHead>();
			Map<String, Object> map = new HashMap<String ,Object>();
			map.put("title","Fattura");
			double totqta = 0;
			double totnecks = 0;
			for (Iterator<Row> it = head.getRows().iterator();it.hasNext();){
				Row r = it.next();
				PrintSingleHead ph = new PrintSingleHead();
				ph.setFromObject(comp,head, r);
				headcoll.add(ph);
				totqta = totqta + r.getQuantity();
				totnecks = totnecks + r.getNecks();
			}
			if (head.getDocument().isOrder() && head.getDocument().getSupplier()){
				for (Iterator<PrintSingleHead> itp = headcoll.iterator();itp.hasNext();){
					PrintSingleHead p = itp.next();
					p.setTot_colli(String.valueOf(totnecks));
					p.setTot_qta(String.valueOf(totqta));
				}
			}
			JRDataSource datasource = new JRBeanCollectionDataSource(headcoll);
			JasperPrint print = JasperFillManager.fillReport(context.getRealPath("report/"+documentType+".jasper"),map,datasource );
			FileOutputStream fileOutputStream = new FileOutputStream(context.getRealPath("report/"+documentType+".pdf"));
			JasperExportManager.exportReportToPdfStream(print, fileOutputStream);
		}catch(Exception ex){
			ex.printStackTrace();
			throw new ExceptionInInitializerError(ex);
		}
		
		return "/InvoiceCreator/report/"+documentType+".pdf";
	}
	private String getReportName(Document d){
		if (d.getCustomer() == true){
			if (d.isOrder() == true){
				if (d.getCustomer() == true)
					return "order";
				else
					return "ordersupplier";
			}else{
				return "document";
			}
				
		}else if (d.getSupplier()){
			if (d.isOrder() == true){
				return "ordersupplier";
			}else{
				return "document";
			}
		}
		return "document";
	}
	public String printMultipleDocument(ServletContext context,int[] ids,User user){
		try{
			//generateAshwinFriends();
			File f = new File(context.getRealPath("report/document.jasper"));
			if(f.exists() == false){
				JasperCompileManager.compileReportToFile(context.getRealPath("report/document.jrxml"), context.getRealPath("report/document.jasper"));
		    }
			Map<String, Object> map = new HashMap<String ,Object>();
			map.put("title","Fattura");
			
			Company comp = user.getCompany();
		    JasperPrint print = null; 
		    for(int i=0;i<ids.length;i++){
		    	Head head = new DocumentDao().getSingleHead(ids[i]);
				JasperCompileManager.compileReportToFile(context.getRealPath("report/"+getReportName(head.getDocument())+".jrxml"), context.getRealPath("report/"+getReportName(head.getDocument())+""+ids[i]+".jasper"));
				Collection<PrintSingleHead> headcoll = new ArrayList<PrintSingleHead>();
				
				for (Iterator<Row> it = head.getRows().iterator();it.hasNext();){
					PrintSingleHead ph = new PrintSingleHead();
					ph.setFromObject(comp,head, it.next());
					headcoll.add(ph);
				}
				JRDataSource datasource = new JRBeanCollectionDataSource(headcoll);
				if (i == 0){
				print = JasperFillManager.fillReport(context.getRealPath("report/"+getReportName(head.getDocument())+""+ids[i]+".jasper"),map,datasource );
				}else{
					JasperPrint print2 = JasperFillManager.fillReport(context.getRealPath("report/"+getReportName(head.getDocument())+""+ids[i]+".jasper"),map,datasource );
					List pages = print2 .getPages();
		            for (int j = 0; j < pages.size(); j++) {
			            JRPrintPage object = (JRPrintPage)pages.get(j);
			            print.addPage(object);
		            }
				}
		    }
			FileOutputStream fileOutputStream = new FileOutputStream(context.getRealPath("report/multidocument.pdf"));
			JasperExportManager.exportReportToPdfStream(print, fileOutputStream);
			for(int y=0;y<ids.length;y++){
				Head head = new DocumentDao().getSingleHead(ids[y]);
				File fd = new File(context.getRealPath("report/"+getReportName(head.getDocument())+""+ids[y]+".jasper"));
				 
			    if(fd.exists() == true){
					fd.delete();
			    }

			}
		}catch(Exception ex){
			ex.printStackTrace();
			throw new ExceptionInInitializerError(ex);
		}
		return "/InvoiceCreator/report/multidocument.pdf";
	}
	@SuppressWarnings("unchecked")
	public String printProductList(ServletContext context,SelectProductsFilter filter,User user){
		try{
			//generateAshwinFriends();
			File f = new File(context.getRealPath("report/productlist.jasper"));
			 
		    if(f.exists() == false){
				JasperCompileManager.compileReportToFile(context.getRealPath("report/productlist.jrxml"), context.getRealPath("report/productlist.jasper"));
			}
		    Company comp = user.getCompany();
		    GECOObject obj = new RegistryDao().getProductList(filter,user);
		    TreeSet<Product> prods = new TreeSet<Product>();
		    if (obj.type == GECOParameter.SUCCESS_TYPE){
		    	prods = (TreeSet<Product>)((GECOSuccess)obj).success;
		    }
			 
			Collection<PrintProductList> headcoll = new ArrayList<PrintProductList>();
			Map<String, Object> map = new HashMap<String ,Object>();
			map.put("title","Lista");
			for (Iterator<Product> it = prods.iterator();it.hasNext();){
				PrintProductList ph = new PrintProductList();
				ph.setFromObject(comp, it.next());
				headcoll.add(ph);
			}
			JRDataSource datasource = new JRBeanCollectionDataSource(headcoll);
			JasperPrint print = JasperFillManager.fillReport(context.getRealPath("report/productlist.jasper"),map,datasource );
			FileOutputStream fileOutputStream = new FileOutputStream(context.getRealPath("report/productlist.pdf"));
			JasperExportManager.exportReportToPdfStream(print, fileOutputStream);
		}catch(Exception ex){
			ex.printStackTrace();
			throw new ExceptionInInitializerError(ex);
		}
		
		return "/InvoiceCreator/report/productlist.pdf";
	}
	public String printList(ServletContext context,int id,User user){
		try{
			//generateAshwinFriends();
			File f = new File(context.getRealPath("report/pricelist.jasper"));
			 
		    if(f.exists() == false){
				JasperCompileManager.compileReportToFile(context.getRealPath("report/pricelist.jrxml"), context.getRealPath("report/pricelist.jasper"));
			}
		    Company comp = user.getCompany();
			it.progess.invoicecreator.vo.List list  = new RegistryDao().getSingleList(id);
			Collection<PrintPriceList> headcoll = new ArrayList<PrintPriceList>();
			Map<String, Object> map = new HashMap<String ,Object>();
			map.put("title","Fattura");
			for (Iterator<ListProduct> it = list.getListproduct().iterator();it.hasNext();){
				PrintPriceList ph = new PrintPriceList();
				ListProduct lp = it.next();
				ph.setFromObject(comp,lp.getProduct(), list,lp);
				headcoll.add(ph);
			}
			JRDataSource datasource = new JRBeanCollectionDataSource(headcoll);
			JasperPrint print = JasperFillManager.fillReport(context.getRealPath("report/pricelist.jasper"),map,datasource );
			FileOutputStream fileOutputStream = new FileOutputStream(context.getRealPath("report/pricelist.pdf"));
			JasperExportManager.exportReportToPdfStream(print, fileOutputStream);
		}catch(Exception ex){
			ex.printStackTrace();
			throw new ExceptionInInitializerError(ex);
		}
		
		return "/InvoiceCreator/report/pricelist.pdf";
	}
	public String printReportOrder(ServletContext context,GECOReportOrder[] report){
		try{
			//generateAshwinFriends();
			File f = new File(context.getRealPath("report/reportOrder.jasper"));
			 
		    if(f.exists() == false){
				JasperCompileManager.compileReportToFile(context.getRealPath("report/reportOrder.jrxml"), context.getRealPath("report/reportOrder.jasper"));
			}
		    
		   
			Collection<PrintReportOrder> headcoll = new ArrayList<PrintReportOrder>();
			
			Map<String, Object> map = new HashMap<String ,Object>();
			map.put("title","Fattura");
			for(int i=0;i<report.length;i++){
				GECOReportOrder rep = report[i];
				for (Iterator<GECOReportOrderProduct> irg = rep.getProducts().iterator();irg.hasNext();){
					GECOReportOrderProduct grop = irg.next();
					PrintReportOrder ro = new PrintReportOrder();
					ro.setFornitore(report[i].getSuppliername());
					ro.setCodice_prodotto(grop.getProductcode()+" "+grop.getProductdescription());
					for (Iterator<GECOReportOrderCustomerQuantity> irc = grop.getCustomers().iterator();irc.hasNext();){
						GECOReportOrderCustomerQuantity cust = irc.next();
						if (ro.getCustomers() == null)
						ro.setCustomers(new ArrayList<PrintReportOrderSubreport>());
						PrintReportOrderSubreport prs = new PrintReportOrderSubreport();
						prs.setCliente(cust.getCustomername());
						prs.setQuantita(String.valueOf(cust.getQuantity()));
						ro.getCustomers().add(prs);
					}
					headcoll.add(ro);
				}
			}
			/*PrintReportOrder ro = new PrintReportOrder();
			ro.setCodice_prodotto("AABBCC");
			ro.setFornitore("TEST FORNITORE");
			PrintReportOrderSubreport prs = new PrintReportOrderSubreport();
			prs.setCliente("Cliente 1");
			prs.setQuantita("10");
			PrintReportOrderSubreport prs1 = new PrintReportOrderSubreport();
			prs1.setCliente("Cliente 2");
			prs1.setQuantita("15");
			ro.setCustomers(new ArrayList<PrintReportOrderSubreport>());
			ro.getCustomers().add(prs1);
			ro.getCustomers().add(prs);
			
			
			PrintReportOrder ro1 = new PrintReportOrder();
			ro1.setCodice_prodotto("AABBCC");
			ro1.setFornitore("TEST FORNITORE");
			PrintReportOrderSubreport prs12 = new PrintReportOrderSubreport();
			prs12.setCliente("Cliente 11");
			prs12.setQuantita("101");
			PrintReportOrderSubreport prs11 = new PrintReportOrderSubreport();
			prs11.setCliente("Cliente 21");
			prs11.setQuantita("151");
			ro1.setCustomers(new ArrayList<PrintReportOrderSubreport>());
			ro1.getCustomers().add(prs11);
			ro1.getCustomers().add(prs12);
			headcoll.add(ro);
			headcoll.add(ro1);*/
			JRDataSource datasource = new JRBeanCollectionDataSource(headcoll);
			JasperPrint print = JasperFillManager.fillReport(context.getRealPath("report/reportOrder.jasper"),map,datasource );
			FileOutputStream fileOutputStream = new FileOutputStream(context.getRealPath("report/reportOrder.pdf"));
			JasperExportManager.exportReportToPdfStream(print, fileOutputStream);
		}catch(Exception ex){
			ex.printStackTrace();
			throw new ExceptionInInitializerError(ex);
		}
		
		return "/InvoiceCreator/report/reportOrder.pdf";
	}
	public String getSingleDocumentPath(ServletContext context,int id,User user){
		String documentType ="";
		String filename = "";
		try{
			//generateAshwinFriends();
			Company comp = user.getCompany();
			Head head = new DocumentDao().getSingleHead(id);
		    documentType = getReportName(head.getDocument());
			File f = new File(context.getRealPath("report/"+documentType+".jasper"));
			if(f.exists() == false){
				JasperCompileManager.compileReportToFile(context.getRealPath("report/"+documentType+".jrxml"), context.getRealPath("report/"+documentType+".jasper"));
			}
		    
			Collection<PrintSingleHead> headcoll = new ArrayList<PrintSingleHead>();
			Map<String, Object> map = new HashMap<String ,Object>();
			map.put("title","Fattura");
			double totqta = 0;
			double totnecks = 0;
			for (Iterator<Row> it = head.getRows().iterator();it.hasNext();){
				Row r = it.next();
				PrintSingleHead ph = new PrintSingleHead();
				ph.setFromObject(comp,head, r);
				headcoll.add(ph);
				totqta = totqta + r.getQuantity();
				totnecks = totnecks + r.getNecks();
			}
			if (head.getDocument().isOrder() && head.getDocument().getSupplier()){
				for (Iterator<PrintSingleHead> itp = headcoll.iterator();itp.hasNext();){
					PrintSingleHead p = itp.next();
					p.setTot_colli(String.valueOf(totnecks));
					p.setTot_qta(String.valueOf(totqta));
				}
			}
			JRDataSource datasource = new JRBeanCollectionDataSource(headcoll);
			JasperPrint print = JasperFillManager.fillReport(context.getRealPath("report/"+documentType+".jasper"),map,datasource );
			filename = head.getName();
			FileOutputStream fileOutputStream = new FileOutputStream(context.getRealPath("report/"+filename+".pdf"));
			JasperExportManager.exportReportToPdfStream(print, fileOutputStream);
		}catch(Exception ex){
			ex.printStackTrace();
			throw new ExceptionInInitializerError(ex);
		}
		
		return context.getRealPath("report/"+filename+".pdf");
	}
}
