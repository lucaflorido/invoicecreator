package it.progess.invoicecreator.util.excel;

import java.io.FileInputStream;

import javax.servlet.ServletContext;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {
	public static String getColValue(Row r,String column,boolean forceString){
		int columnindex = CellReference.convertColStringToIndex(column);
		String value = "";
		if (r instanceof HSSFRow){
			HSSFCell cell97 = (HSSFCell)r.getCell(columnindex);
			value = ExcelUtil.getValue(cell97,forceString);
		}else if (r instanceof XSSFRow){
			XSSFCell cell10 = (XSSFCell)r.getCell(columnindex);
			value = ExcelUtil.getValue(cell10,forceString);
		}
		return value;
	}
	public static String getValue(Cell column,boolean forceString){
		String value = "";
		if (column == null){
			return value;
		}
		switch(column.getCellType()){
			case Cell.CELL_TYPE_STRING:
				value = column.getStringCellValue();
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				boolean bval = column.getBooleanCellValue();
				value = String.valueOf(bval);
				break;
			case Cell.CELL_TYPE_ERROR:
				value = "";
				break;
			case Cell.CELL_TYPE_FORMULA:
				value = "";
				break;
			case Cell.CELL_TYPE_NUMERIC:
				double dval = column.getNumericCellValue();
				value = String.valueOf(dval);
				if (forceString){
					value = value.replace(".0", "");
				}
				break;
			default:
				value = "";
		}
		return value;
	}
	public static Sheet initialize(ServletContext context,String filename) throws Exception{
		Workbook wb = null;
		FileInputStream fileInputStream = new FileInputStream(context.getRealPath(filename));
		Sheet worksheet = null;
		String type = filename.split("\\.")[1];
		if (type.equals("xls")){
			HSSFWorkbook workbook97 = new HSSFWorkbook(fileInputStream);
			worksheet = workbook97.getSheetAt(0);
		}else if (type.equals("xlsx")){
			XSSFWorkbook workbook10 = new XSSFWorkbook(fileInputStream);
			worksheet = workbook10.getSheetAt(0);
		}
		return worksheet;
	}
}
