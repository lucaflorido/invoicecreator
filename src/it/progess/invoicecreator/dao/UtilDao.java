package it.progess.invoicecreator.dao;

import it.progess.invoicecreator.properties.GECOParameter;
import it.progess.invoicecreator.vo.GECOError;
import it.progess.invoicecreator.vo.GECOObject;
import it.progess.invoicecreator.vo.GECOSuccess;
import it.progess.invoicecreator.vo.helpobject.ProductBasicPricesCalculation;

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
}
