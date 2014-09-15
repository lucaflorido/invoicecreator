package it.progess.invoicecreator.util.store;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.hibernate.Session;

import it.progess.invoicecreator.dao.DocumentDao;
import it.progess.invoicecreator.dao.StoreDao;
import it.progess.invoicecreator.properties.GECOParameter;
import it.progess.invoicecreator.vo.GECOError;
import it.progess.invoicecreator.vo.GECOObject;
import it.progess.invoicecreator.vo.GECOSuccess;
import it.progess.invoicecreator.vo.Head;
import it.progess.invoicecreator.vo.Product;
import it.progess.invoicecreator.vo.Row;
import it.progess.invoicecreator.vo.Storage;
import it.progess.invoicecreator.vo.StorageSerialCode;
import it.progess.invoicecreator.vo.UnitMeasure;
import it.progess.invoicecreator.vo.UnitMeasureProduct;

public class StoreManager {
	public static void calculateHead(Head head,Session session){
		if (isLoad(head)){
			load(head,session);
		}
		if (isUnload(head)){
			unload(head,session);
		}
	}
	public static void deleteMovements(Head head,Session session){
		if (isLoad(head)){
			removeload(head,session);
		}
		if (isUnload(head)){
			removeunload(head,session);
		}
	}
	private static boolean isLoad(Head head){
		if (head.getDocument().getStoremovement().getComebackLoad() == true || head.getDocument().getStoremovement().getGenericLoad() == true || head.getDocument().getStoremovement().getInternalLoad() == true || head.getDocument().getStoremovement().getSupplierLoad() == true){
			return true;
		}else{
			return false;
		}
	}
	
	private  static void load(Head head,Session session){
		for (Iterator<Row> it = head.getRows().iterator();it.hasNext();){
			Row row = it.next();
			singleRowLoad(row,session);
		}
	}
	private  static void removeload(Head head,Session session){
		for (Iterator<Row> it = head.getRows().iterator();it.hasNext();){
			Row row = it.next();
			singleRowRemoveLoad(row,session);
		}
	}
	private static void singleRowRemoveLoad(Row row,Session session){
		StoreDao sd = new StoreDao();
		//Check tblstore id prodoct
		Storage st = sd.getStorageProduct(row.getProduct());
		st.removeloadRow(row);
		sd.saveStore(st,session); //***********
	}
	private static void singleRowLoad(Row row,Session session){
		StoreDao sd = new StoreDao();
		//Check tblstore id prodoct
		Storage st = sd.getStorageProduct(row.getProduct());
		//if exist not
		//
		if (st.getIdStorage() == 0){
			st.loadRow(row);
			sd.saveStore(st,session); //***********
		}else{
			if (row.getIdRow() == 0){	
				st.loadRow(row);
				sd.saveStore(st,session);
			}else{
				//get the old row
				Row oldrow = new DocumentDao().getSingleRow(row.getIdRow());
				if (oldrow.getProduct().getIdProduct() == row.getProduct().getIdProduct()){
					//remove the old quantity
					st.removeloadRow(oldrow);
					//	save the new quantity
					st.loadRow(row);
					sd.saveStore(st,session);
				//recalculate the stock
				}else{
					Storage stold = sd.getStorageProduct(row.getProduct());
					stold.removeloadRow(oldrow);
					sd.saveStore(stold,session);//***********
					st.loadRow(row);
					sd.saveStore(st,session);//***********
				}
			}
		}
		
	}
	private static boolean isUnload(Head head){
		if (head.getDocument().getStoremovement().getCustomerUnload() == true || head.getDocument().getStoremovement().getGenericUnload() == true || head.getDocument().getStoremovement().getInternalUnload() == true || head.getDocument().getStoremovement().getExpiredUnload() == true){
			return true;
		}else{
			return false;
		}
	}
	private  static void unload(Head head,Session session){
		for (Iterator<Row> it = head.getRows().iterator();it.hasNext();){
			Row row = it.next();
			singleRowUnload(row,session);
		}
	}
	private  static void removeunload(Head head,Session session){
		for (Iterator<Row> it = head.getRows().iterator();it.hasNext();){
			Row row = it.next();
			singleRowRemoveUnload(row,session);
		}
	}
	private static void singleRowRemoveUnload(Row row,Session session){
		StoreDao sd = new StoreDao();
		//Check tblstore id prodoct
		Storage st = sd.getStorageProduct(row.getProduct());
		st.removeunloadRow(row);
		sd.saveStore(st,session); //***********
	}
	private static void singleRowUnload(Row row,Session session){
		StoreDao sd = new StoreDao();
		//Check tblstore id prodoct
		Storage st = sd.getStorageProduct(row.getProduct());
		//if exist not
		//
		if (st.getIdStorage() == 0){
			st.unloadRow(row);
			sd.saveStore(st,session); //***********
		}else{
			if (row.getIdRow() == 0){	
				st.unloadRow(row);
				sd.saveStore(st,session);
			}else{
				//get the old row
				Row oldrow = new DocumentDao().getSingleRow(row.getIdRow());
				if (oldrow.getProduct().getIdProduct() == row.getProduct().getIdProduct()){
					//remove the old quantity
					st.removeunloadRow(oldrow);
					//	save the new quantity
					st.unloadRow(row);
					sd.saveStore(st,session);
				//recalculate the stock
				}else{
					Storage stold = sd.getStorageProduct(row.getProduct());
					stold.removeunloadRow(oldrow);
					sd.saveStore(stold,session);//***********
					st.unloadRow(row);
					sd.saveStore(st,session);//***********
				}
			}
		}
	}
	
	public static void rowToBasicUMconverter(Row r){
		Product p = r.getProduct();
		UnitMeasure um = r.getUm();
		UnitMeasureProduct umFavorite = r.getProduct().getPreferenceUMP();
		if (um.getIdUnitMeasure() != umFavorite.getUm().getIdUnitMeasure() ){
			r.setNecks(r.getQuantity());
			double qta = r.getQuantity() * r.getProduct().getConversion(um);
			r.setQuantity(qta);
			
			r.setUm(umFavorite.getUm());
			r.setProductum(umFavorite.getUm().getCode());
			r.setProductcode(umFavorite.getCode());
		}else{
			//r.setQuantity(0);
			r.setNecks(0);
		}
	}
	public static GECOObject getProductListWithSerialNumbers(HashSet<Row> rows){
		HashSet<Row> support = new HashSet<Row>();
		for (Iterator<Row> ir = rows.iterator();ir.hasNext();){
			Row row = ir.next();
			double qta = 0;
			
			if (row.getProduct().getManageserialnumber() == true && (row.getSerialnumber() == "" || row.getSerialnumber() == null)){
				Storage store = new  StoreDao().getStorageProduct(row.getProduct());
				HashSet<StorageSerialCode> ssc = (HashSet)store.getStoragesc();
				Iterator<StorageSerialCode> issc = ssc.iterator();
				support.add(row);
				if (issc.hasNext() == true){
					qta = fillSCQuantity(issc.next(), row);
					while (qta > 0 && issc.hasNext()==true ){
						StorageSerialCode sc = issc.next();
						Row newRow = new Row();
						newRow.setProduct(row.getProduct());
						newRow.setProductcode(row.getProductcode());
						newRow.setProductdescription(row.getProductdescription());
						newRow.setProductum(row.getProductum());
						newRow.setUm(row.getUm());
						newRow.setPrice(row.getPrice());
						newRow.setQuantity(qta);
						support.add(newRow);
						qta = fillSCQuantity(sc, newRow);
					}
					if (qta > 0){
						return new GECOError(GECOParameter.ERROR_QUANTITY,"Quantità nel documento superiore alla quantità disponibile"); 
					}
				}else{
					return new GECOError(GECOParameter.ERROR_QUANTITY,"Quantità nel documento superiore alla quantità disponibile");
				}
				//}
			}
		}
		//rows.addAll(support);
		return new GECOSuccess(support);
	}
	private static double fillSCQuantity(StorageSerialCode s,Row r){
		if (s.getStock() >= r.getQuantity() ){
			r.setSerialnumber(s.getSerialcode());
			r.setExpiredate(s.getExpiredate());
			return 0;
		}else{
			r.setSerialnumber(s.getSerialcode());
			r.setExpiredate(s.getExpiredate());
			double gap = r.getQuantity() - s.getStock();
			r.setQuantity(s.getStock());
			return gap;
		}
	}
	public static GECOObject getProductListWithoutSerialNumbers(HashSet<Row> rows){
		HashSet<Row> support = new HashSet<Row>();
		for (Iterator<Row> ir = rows.iterator();ir.hasNext();){
			Row row = ir.next();
			if (row.getProduct().getManageserialnumber() == true && (row.getSerialnumber() == "" || row.getSerialnumber() == null)){
				support.add(row);
			}
		}
		return new GECOSuccess(support);
	}
	public static void removeSingleRow(Row r,Session session){
		if (isLoad(r.getHead()) == true){
			singleRowRemoveLoad(r, session);
		}else if (isUnload(r.getHead())){
			singleRowRemoveUnload(r, session);
		}
	}
	
}
