package it.progess.invoicecreator.dao;

import java.util.Iterator;

import it.progess.invoicecreator.hibernate.HibernateUtils;
import it.progess.invoicecreator.properties.GECOParameter;
import it.progess.invoicecreator.vo.GECOError;
import it.progess.invoicecreator.vo.GECOObject;
import it.progess.invoicecreator.vo.GECOSuccess;
import it.progess.invoicecreator.vo.ListProduct;
import it.progess.invoicecreator.vo.Product;
import it.progess.invoicecreator.vo.helpobject.ProductBasicPricesCalculation;
import it.progess.invoicecreator.vo.helpobject.ProductListIncrementVo;
import it.progess.invoicecreator.vo.helpobject.ProductListPricesCalculation;

public class UtilDao {
	public GECOObject calculateBasicPricesProduct(ProductBasicPricesCalculation basic,boolean isSellPrice){
		try{
			if (isSellPrice == false){
				basic.calculatePercentage();
			}else{
				basic.calculateSellPrice();
			}
		}catch(Exception e){
			new GECOError(GECOParameter.ERROR_CALCULATION, e.getMessage());
		}
		return new GECOSuccess(basic);
	}
	public GECOObject calculateEndPriceProduct(ProductBasicPricesCalculation basic){
		try{
			basic.calculateFromTotalPrice();
		}catch(Exception e){
			new GECOError(GECOParameter.ERROR_CALCULATION, e.getMessage());
		}

		return new GECOSuccess(basic);
	}
	public GECOObject calculateProductListPrices(Product p){
		float tr =(float) p.getTaxrate().getValue();
		for (Iterator<ListProduct> it = p.getListproduct().iterator();it.hasNext();){
			ListProduct lp = it.next();
			lp.getProduct().setPurchaseprice(p.getPurchaseprice());
			lp.calculatePrices(tr);
		}
		double increment = HibernateUtils.calculatePercentage(p.getPurchaseprice(),p.getPercentage());
		p.setSellprice((float)increment+(float)p.getPurchaseprice());
		return new GECOSuccess(p);
	}
	public GECOObject calculateIncrementProductListPrice(ProductListIncrementVo plivo){
		for (Iterator<ListProduct> it = plivo.getListproducts().iterator();it.hasNext();){
			ListProduct lp = it.next();
			new ProductListPricesCalculation().calculateIncrement(plivo.isEndPrice(),plivo.isPercentage(), plivo.getIncrement(), lp);
		}
			
		return new GECOSuccess(plivo.getListproducts());
	}
}
