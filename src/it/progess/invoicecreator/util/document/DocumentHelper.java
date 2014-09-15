package it.progess.invoicecreator.util.document;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import it.progess.invoicecreator.dao.StoreDao;
import it.progess.invoicecreator.util.store.StoreManager;
import it.progess.invoicecreator.vo.Head;
import it.progess.invoicecreator.vo.HeadTotalCalculation;
import it.progess.invoicecreator.vo.Row;
import it.progess.invoicecreator.vo.RowTotalCalculator;
import it.progess.invoicecreator.vo.Storage;

public class DocumentHelper {
	public static HashMap<Integer, Set<Head>> groupByCustomer(Set<Head> heads){
		
		HashMap<Integer, Set<Head>> map = new HashMap<Integer, Set<Head>>();
		for(Iterator<Head> iterator = heads.iterator();iterator.hasNext();){
			Head head = iterator.next();
			if(map.containsKey(head.getCustomer().getIdCustomer()) == true){
				map.get(head.getCustomer().getIdCustomer()).add(head);
			}else{
				Set<Head> headToInsert = new HashSet<Head>();
				headToInsert.add(head);
				map.put(head.getCustomer().getIdCustomer(), headToInsert);
			}
		}
		return map;
	}
	public static HashMap<Integer, Set<Head>> groupBySupplier(Set<Head> heads){
		HashMap<Integer, Set<Head>> map = new HashMap<Integer, Set<Head>>();
		for(Iterator<Head> iterator = heads.iterator();iterator.hasNext();){
			Head head = iterator.next();
			if(map.containsKey(head.getSupplier().getIdSupplier()) == true){
				map.get(head.getSupplier().getIdSupplier()).add(head);
			}else{
				Set<Head> headToInsert = new HashSet<Head>();
				headToInsert.add(head);
				map.put(head.getSupplier().getIdSupplier(), headToInsert);
			}
		}
		return map;
	}
	public static HashMap<Integer, Set<Row>> groupRowBySupplierNeededCalculation(Set<Head> heads){
		HashMap<Integer, Set<Row>> map = new HashMap<Integer, Set<Row>>();
		for(Iterator<Head> iterator = heads.iterator();iterator.hasNext();){
			Head head = iterator.next();
			for(Iterator<Row> iteratorRow = head.getRows().iterator();iteratorRow.hasNext();){
				Row row = iteratorRow.next();
				StoreManager.rowToBasicUMconverter(row);
				if(map.containsKey(row.getProduct().getSupplier().getIdSupplier()) == true){
					//STORE UM CONVERTER
					
					//row = setQtaRowNeededCalculation(row);
					if (row.getQuantity() > 0){
						Row newRow = new Row();
						newRow.copy(row);
						newRow.setIdRow(0);
						//CONTROL PRODUCTS ALREADY IN THE SYSTEM
						Set<Row> rs= map.get(row.getProduct().getSupplier().getIdSupplier());
						boolean found = false;
						for (Iterator<Row> irs = rs.iterator();irs.hasNext();){
						   Row rexisting = irs.next();
						   if (rexisting.getProduct().getIdProduct() == newRow.getProduct().getIdProduct()){
							   rexisting.setQuantity(rexisting.getQuantity() + newRow.getQuantity());
							   rexisting.setNecks(rexisting.getNecks()+ newRow.getNecks());
							   rexisting.setPrice(rexisting.getProduct().getPurchaseprice());
							   found = true;
							   break;
						   }
						}
						if (found == false){
							rs.add(newRow);
						}
					}
				}else{
					Set<Row> rowToInsert = new HashSet<Row>();
					//row = setQtaRowNeededCalculation(row);
					if (row.getQuantity() > 0){
						Row newRow = new Row();
						newRow.copy(row);
						newRow.setIdRow(0);
						newRow.setPrice(newRow.getProduct().getPurchaseprice());
						rowToInsert.add(newRow);
						map.put(row.getProduct().getSupplier().getIdSupplier(), rowToInsert);
					}
				}
			}
		}
		return map;
	}
	public static Row setQtaRowNeededCalculation(Row row){
		
		StoreDao sd = new StoreDao();
		Storage st = sd.getStorageProduct(row.getProduct());
	    if (st.getStock() < row.getQuantity() ){
	    	row.setQuantity(row.getQuantity() - st.getStock());
	    	row.setPrice(row.getProduct().getPurchaseprice());
	    }else{
	    	row.setQuantity(0);
	    }
		//row.setQuantity()
		return row;
	}
	public static void totalHeadCalculation(Head head){
		if (head.getRows() != null && head.getRows().size()> 0){
			for (Iterator<Row> it = head.getRows().iterator();it.hasNext();){
				Row row = it.next();
				RowTotalCalculator rc = new RowTotalCalculator();
				rc.rowCalculation(row);
			}
			HeadTotalCalculation hc = new HeadTotalCalculation();
			hc.calculation(head);
		}
	}
}
