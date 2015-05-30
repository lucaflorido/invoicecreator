package it.progess.invoicecreator.dao;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletContext;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import it.progess.invoicecreator.hibernate.HibernateUtils;
import it.progess.invoicecreator.properties.GECOParameter;
import it.progess.invoicecreator.util.excel.ExcelUtil;
import it.progess.invoicecreator.vo.Address;
import it.progess.invoicecreator.vo.Contact;
import it.progess.invoicecreator.vo.Customer;
import it.progess.invoicecreator.vo.GECOError;
import it.progess.invoicecreator.vo.GECOObject;
import it.progess.invoicecreator.vo.GECOSuccess;
import it.progess.invoicecreator.vo.GroupProduct;
import it.progess.invoicecreator.vo.List;
import it.progess.invoicecreator.vo.ListCustomer;
import it.progess.invoicecreator.vo.ListProduct;
import it.progess.invoicecreator.vo.Product;
import it.progess.invoicecreator.vo.Promoter;
import it.progess.invoicecreator.vo.TaxRate;
import it.progess.invoicecreator.vo.UnitMeasure;
import it.progess.invoicecreator.vo.UnitMeasureProduct;
import it.progess.invoicecreator.vo.User;
import it.progess.invoicecreator.vo.importvo.ImportCustomer;
import it.progess.invoicecreator.vo.importvo.ImportProduct;
import it.progess.invoicecreator.vo.importvo.ImportProductList;

public class ImportDao {
	public GECOObject importProducts(ServletContext context,ImportProduct ip,User user){
		try{
			if (ip.control() != null){
				return ip.control();
			}
			FileInputStream fileInputStream = new FileInputStream(context.getRealPath(ip.getFilename()));
			HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
			HSSFSheet worksheet = workbook.getSheetAt(0);
			int startIndex = ip.getStartIndex();
			int endIndex = 0;
			if (ip.getEndIndex() > 0){
				endIndex = ip.getEndIndex();
			}else{		
				endIndex = worksheet.getLastRowNum();
			}
			for (int i = startIndex;i < endIndex;i++){
				HSSFRow row1 = worksheet.getRow(i);
				//GROUP
				int colgroup = CellReference.convertColStringToIndex(ip.getGroup());
				HSSFCell cellGroup = row1.getCell(colgroup);
				String groupcode = cellGroup.getStringCellValue();
				GroupProduct gp = getGroupProduct(groupcode,user);
				
				//TAXRATE
				int coltr= CellReference.convertColStringToIndex(ip.getTaxrate());
				HSSFCell celltr = row1.getCell(coltr);
				String valueStr = celltr.getStringCellValue();
				double value = Double.parseDouble(valueStr);
				TaxRate tr = getTaxrate(value, user);
				
				//PRODUCT
				int colprod = CellReference.convertColStringToIndex(ip.getCode());
				HSSFCell cellProd = row1.getCell(colprod);
				String code = cellProd.getStringCellValue();
				Product p = getProduct(code,user);
				
				int coldesc = CellReference.convertColStringToIndex(ip.getDescription());
				HSSFCell celldesc = row1.getCell(coldesc);
				String desc = celldesc.getStringCellValue();
				p.setDescription(desc);
				p.setCode(code);
				p.setSellprice(1);
				
				int colpurchase = CellReference.convertColStringToIndex(ip.getPurchaseprice());
				HSSFCell cellPPrice = row1.getCell(colpurchase);
				String ppriceStr =String.valueOf(cellPPrice.getNumericCellValue());
				ppriceStr = ppriceStr.replace(",", ".");
				float cost = Float.parseFloat(ppriceStr);
				p.setPurchaseprice(cost);
				
				
				p.setTaxrate(tr);
				p.setGroup(gp);
				p.setProduct(true);
				if (p.getUms() == null || p.getUms().size() == 0){
					Set<UnitMeasureProduct> umps = new HashSet<UnitMeasureProduct>();
					p.setUms(umps);
					UnitMeasureProduct ump = new UnitMeasureProduct();
					ump.setCode(code);
					ump.setConversion(1);
					ump.setPreference(true);
					int colum = CellReference.convertColStringToIndex(ip.getUmcode());
					HSSFCell cellum = row1.getCell(colum);
					String um = cellum.getStringCellValue();
					UnitMeasure umobj = getUM(um, user);
					ump.setUm(umobj);
					umps.add(ump);
				}
				calculateListPrices(row1,ip,p);
				new RegistryDao().saveUpdatesProduct(p, user);
			}
			/*XSSFRow row1 = worksheet.getRow(0);
			XSSFCell cellA1 = row1.getCell((short) 0);
			String a1Val = cellA1.getStringCellValue();*/
		}catch(Exception e){
			e.printStackTrace();
			return new GECOError();
		}
		return new GECOSuccess();
	}
	private GroupProduct getGroupProduct(String code,User user){
		if (code.equals("")){
			return null;
		}
		ArrayList<GroupProduct> groups = new BasicDao().getGroupProductList(user);
		boolean found = false;
		for (Iterator<GroupProduct> it = groups.iterator(); it.hasNext();){
			GroupProduct gp = it.next();
			if(gp.getCode().equals(code)){
				return gp;
			}				
		}
		if (found == false){
			GroupProduct gpn = new GroupProduct();
			gpn.setCode(code);
			gpn.setCompany(user.getCompany());
			gpn.setDescription(code);
			gpn.setName(code);
			groups.add(gpn);
			GroupProduct[] gpa = {} ;
			gpa = groups.toArray(gpa);
			new BasicDao().saveUpdatesGroupProduct(gpa, user);
			groups = new BasicDao().getGroupProductList(user);
			for (Iterator<GroupProduct> itnew = groups.iterator(); itnew.hasNext();){
				GroupProduct gp = itnew.next();
				if(gp.getCode().equals(code)){
					return gp;
				}				
			}
		}
		return null;
	}
	private Product getProduct(String code,User user){
		Product product = new RegistryDao().getSingleCodeProductFull(code,user);
		return product;
	}
	private TaxRate getTaxrate(double value,User user){
		ArrayList<TaxRate> taxrates = new BasicDao().getTaxRateList(user);
		for (Iterator<TaxRate> it = taxrates.iterator(); it.hasNext();){
			TaxRate tr = it.next();
			if(tr.getValue() == value){
				return tr;
			}				
		}
		
			TaxRate tr = new TaxRate();
			tr.setValue(value);
			tr.setCompany(user.getCompany());
			tr.setDescription(String.valueOf(value));
			taxrates.add(tr);
			TaxRate[] tra = {} ;
			tra = taxrates.toArray(tra);
			new BasicDao().saveUpdatesTaxrate(tra, user);
			taxrates = new BasicDao().getTaxRateList(user);
			for (Iterator<TaxRate> itnew = taxrates.iterator(); itnew.hasNext();){
				TaxRate trn = itnew.next();
				if(trn.getValue() == value){
					return trn;
				}				
			}
		
		return null;
	}
	private UnitMeasure getUM(String code,User user){
		if (code.equals("")){
			return null;
		}
		ArrayList<UnitMeasure> ums = new BasicDao().getUnitMeasureList(user);
		boolean found = false;
		for (Iterator<UnitMeasure> it = ums.iterator(); it.hasNext();){
			UnitMeasure gp = it.next();
			if(gp.getCode().equals(code)){
				return gp;
			}				
		}
		if (found == false){
			UnitMeasure gpn = new UnitMeasure();
			gpn.setCode(code);
			gpn.setCompany(user.getCompany());
			gpn.setDescription(code);
			gpn.setName(code);
			ums.add(gpn);
			UnitMeasure[] gpa = {};
			gpa = ums.toArray(gpa);
			new BasicDao().saveUpdatesUnitMeasure(gpa, user);
			ums = new BasicDao().getUnitMeasureList(user);
			for (Iterator<UnitMeasure> itnew = ums.iterator(); itnew.hasNext();){
				UnitMeasure gp = itnew.next();
				if(gp.getCode().equals(code)){
					return gp;
				}				
			}
		}
		return null;
	}
	private void calculateListPrices(HSSFRow row1,ImportProduct ip,Product p){
		if (p.getListproduct() == null){
			Set<ListProduct> lp = new HashSet<ListProduct>();
			p.setListproduct(lp);
		}
		for (Iterator<ImportProductList> iterator = ip.getLists().iterator();iterator.hasNext();){
			ImportProductList ipl = iterator.next();
			ListProduct lp = checkProductinList(p,ipl.getList());
			
			int colprice = CellReference.convertColStringToIndex(ipl.getPricecol());
			HSSFCell cellPPrice = row1.getCell(colprice);
			String ppriceStr =String.valueOf(cellPPrice.getNumericCellValue());
			ppriceStr = ppriceStr.replace(",", ".");
			float pricelist = Float.parseFloat(ppriceStr);
			lp.setPrice(pricelist);
			
			lp.setPercentage(HibernateUtils.calculatePercentageFromPrices(p.getPurchaseprice(), pricelist));
			lp.calculatePrices((float)p.getTaxrate().getValue());
			lp.setProduct(null);
			p.getListproduct().add(lp);
		}
	}
	private ListProduct checkProductinList(Product p, List l){
		ListProduct lp = new ListProduct();
		for (Iterator<ListProduct> it = p.getListproduct().iterator();it.hasNext();){
			ListProduct existinglp = it.next();
			if (existinglp.getList().getIdList() == l.getIdList()){
				return existinglp;
			}
		}
		lp.setActive(true);
		lp.setProduct(p);
		lp.setList(l);
		return lp;
	}
	public GECOObject importCustomers(ServletContext context,ImportCustomer ic,User user){
		ArrayList<String> message = new ArrayList<String>();
		try{
			if (ic.control() != null){
				return ic.control();
			}
			/*FileInputStream fileInputStream = new FileInputStream(context.getRealPath(ic.getFilename()));
			HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
			HSSFSheet worksheet = workbook.getSheetAt(0);
			*/
			
			Sheet worksheet = ExcelUtil.initialize(context, ic.getFilename());
			int startIndex = ic.getStartIndex();
			int endIndex = 0;
			if (ic.getEndIndex() > 0){
				endIndex = ic.getEndIndex();
			}else{		
				endIndex = worksheet.getLastRowNum();
			}
			for (int i = startIndex;i <= endIndex;i++){
				try{
					
				
				Row row1 = worksheet.getRow(i);
				//GROUP
				/*int colgroup = CellReference.convertColStringToIndex(ip.getGroup());
				HSSFCell cellGroup = row1.getCell(colgroup);
				String groupcode = cellGroup.getStringCellValue();
				GroupProduct gp = getGroupProduct(groupcode,user);
				*/
				//TAXRATE
				/*int coltr= CellReference.convertColStringToIndex(ip.getTaxrate());
				HSSFCell celltr = row1.getCell(coltr);
				String valueStr = celltr.getStringCellValue();
				double value = Double.parseDouble(valueStr);
				TaxRate tr = getTaxrate(value, user);
				*/
				//PRODUCT
				/*int colcust = CellReference.convertColStringToIndex(ic.getColCode());
				HSSFCell cellCust = row1.getCell(colcust);*/
				String code = ExcelUtil.getColValue(row1, ic.getColCode(),true);
				
				Customer c = getCustomer(code,user);
				c.setCustomercode(code);
				c.setCustomername(ExcelUtil.getColValue(row1, ic.getColName(),true));
				c.setTaxcode(ExcelUtil.getColValue(row1, ic.getColTaxcode(),true));
				c.setAlternativecode1(ExcelUtil.getColValue(row1, ic.getColAlternativecode1(),true));
				c.setAlternativecode2(ExcelUtil.getColValue(row1, ic.getColAlternativecode2(),true));
				c.setSerialnumber(ExcelUtil.getColValue(row1, ic.getColSerialnumber(),true));
				Contact ct = new Contact();
				ct.setEmail1(ExcelUtil.getColValue(row1, ic.getColEmail(),true));
				ct.setPhone1(ExcelUtil.getColValue(row1, ic.getColPhone(),true));
				ct.setMobile1(ExcelUtil.getColValue(row1, ic.getColMobile(),true));
				c.setCommission(Double.parseDouble(ExcelUtil.getColValue(row1, ic.getColCommission(),false)));
				//getList(ExcelUtil.getColValue(row1, ic.getColList()), user)
				setList(c, getList(ExcelUtil.getColValue(row1, ic.getColList(),true), user));
				c.setPromoter(getPromoter(ExcelUtil.getColValue(row1, ic.getColPromoter(),true), user));
				c.setActive(true);
				Address a = new Address();
				a.setAddress(ExcelUtil.getColValue(row1, ic.getColStreet(),true));
				a.setCity(ExcelUtil.getColValue(row1, ic.getColCity(),true));
				a.setZipcode(ExcelUtil.getColValue(row1, ic.getColZipcode(),true));
				a.setZone(ExcelUtil.getColValue(row1, ic.getColZone(),true));
				c.setAddress(a);
				
				GECOObject go = new RegistryDao().saveUpdatesCustomer(c, user);
				if (go.type == GECOParameter.ERROR_TYPE){
					int index = i+1;
					message.add("Riga:"+ index+ " " +((GECOError)go).getErrorMessage());
				}
				}catch(Exception e){
					int index = i+1;
					message.add("Riga:"+ index+ " " +e.getMessage());
				}
			}
			/*XSSFRow row1 = worksheet.getRow(0);
			XSSFCell cellA1 = row1.getCell((short) 0);
			String a1Val = cellA1.getStringCellValue();*/
		}catch(Exception e){
			e.printStackTrace();
			return new GECOError();
		}
		return new GECOSuccess(message);
	}
	private Customer getCustomer(String code,User user){
		Customer customer = new RegistryDao().getSingleCustomer(code,user);
		return customer;
	}
	private List getList(String code,User user){
		List l = new RegistryDao().getSingleListByCode(code,user);
		if (l.getIdList() == 0){
			return null;
		}else{
			return l;
		}
	}
	private Promoter getPromoter(String code,User user){
		Promoter p = new RegistryDao().getSinglePromoter(code,user);
		if (p.getIdPromoter() == 0){
			return null;
		}else{
			return p;
		}
	}
	private void setList(Customer c,List l){
		if (l == null){
			return;
		}
		Set<ListCustomer> list = c.getLists();
		if (c.getLists() == null){
			list = new HashSet<ListCustomer>();
			c.setLists(list);
		}
		boolean found = false;
		for(Iterator<ListCustomer> it = list.iterator();it.hasNext();){
			ListCustomer lc = it.next();
			
			if (lc.getList().getIdList() == l.getIdList()){
				found = true;
			}
		}
		if (found == false){
			ListCustomer lcnew = new ListCustomer();
			lcnew.setList(l);
			list.add(lcnew);
		}
		c.setLists(list);
	}
}
