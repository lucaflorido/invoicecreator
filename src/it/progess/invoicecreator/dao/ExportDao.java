package it.progess.invoicecreator.dao;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletContext;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import it.progess.invoicecreator.hibernate.DataUtilConverter;
import it.progess.invoicecreator.vo.GECOError;
import it.progess.invoicecreator.vo.GECOObject;
import it.progess.invoicecreator.vo.GECOSuccess;
import it.progess.invoicecreator.vo.Head;
import it.progess.invoicecreator.vo.User;

public class ExportDao {
	public GECOObject exportHeads(ServletContext context,Head[] heads,User user){
		try{
			SXSSFWorkbook wb = new SXSSFWorkbook(-1); // turn off auto-flushing and accumulate all rows in memory
	        Sheet sh = wb.createSheet();
	        /*for(int rownum = 0; rownum < 1000; rownum++){
	            Row row = sh.createRow(rownum);
	            for(int cellnum = 0; cellnum < 10; cellnum++){
	                Cell cell = row.createCell(cellnum);
	                String address = new CellReference(cell).formatAsString();
	                cell.setCellValue(address);
	            }
	           // manually control how rows are flushed to disk 
	           if(rownum % 100 == 0) {
	                ((SXSSFSheet)sh).flushRows(100); // retain 100 last rows and flush all others
	el
	                // ((SXSSFSheet)sh).flushRows() is a shortcut for ((SXSSFSheet)sh).flushRows(0),
	                // this method flushes all rows
	           }
	        }*/
	        int rownum = 1;
	        Row header = sh.createRow(1);
	        Cell productCode = header.createCell(0);
	        productCode.setCellValue("Codice Prodotto");
	        Cell customercode1 = header.createCell(1);
	        customercode1.setCellValue("Codice Cliente SSSS");
	        Cell customercode2 = header.createCell(2);
	        customercode2.setCellValue("Codice Cliente MMCC");
	        Cell quantity = header.createCell(3);
	        quantity.setCellValue("Quantita");
	        Cell date = header.createCell(4);
	        date.setCellValue("Data Ordine");
	        Cell number = header.createCell(5);
	        number.setCellValue("Numero Ordine");
	        Cell note = header.createCell(6);
	        note.setCellValue("Note");
	        for (int i=0;i<heads.length;i++){
	        	Head h = heads[i];
	        	h = new DocumentDao().getSingleHead(h.getIdHead());
	        	if (h.getRows().size() > 0){
	        		rownum++;
	        	}
	        	Row row = sh.createRow(rownum);
	        	for (Iterator<it.progess.invoicecreator.vo.Row> ir = h.getRows().iterator();ir.hasNext();){
	        		it.progess.invoicecreator.vo.Row r = ir.next();
		        	Cell productCol = row.createCell(0);
		        	productCol.setCellValue(r.getProductcode());
		        	Cell ssss = row.createCell(1);
		        	ssss.setCellValue(h.getCustomer().getAlternativecode1());
		        	Cell mmcc = row.createCell(2);
		        	mmcc.setCellValue(h.getCustomer().getAlternativecode2());
		        	Cell qta = row.createCell(3);
		        	qta.setCellValue(r.getQuantity());
		        	Cell dateCell = row.createCell(4);
		        	dateCell.setCellValue(h.getDate());
		        	Cell numberCell = row.createCell(5);
		        	numberCell.setCellValue(calculateNumber(h));
		        	Cell noteCell = row.createCell(6);
		        	noteCell.setCellValue(h.getNote());
		        }
	        }
	        FileOutputStream out = new FileOutputStream(context.getRealPath("export/export.xlsx"));
	        wb.write(out);
	        out.close();
	        // dispose of temporary files backing this workbook on disk
	        wb.dispose();
		}catch(Exception e){
			e.printStackTrace();
			return new GECOError("error",e.getMessage());
		}
		return new GECOSuccess("/InvoiceCreator/export/export.xlsx");
	}
	private String calculateNumber(Head h){
		String num = "";
		int number = h.getNumber();
		Date d = DataUtilConverter.convertDateFromString(h.getDate());
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(d);
		String year =String.valueOf(gc.get(GregorianCalendar.YEAR));
		String yearshort = year.substring(2, 4);
		if (number <10){
			num = yearshort+"000"+String.valueOf(number);
		}else if (number < 100){
			num = yearshort+"00"+String.valueOf(number);
		}else if (number < 1000){
			num = yearshort+"0"+String.valueOf(number);
		}else{
			num = yearshort+String.valueOf(number);
		}
		
		return num;
	}
}
