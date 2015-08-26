package it.progess.invoicecreator.vo.helpobject;

import it.progess.invoicecreator.vo.ListProduct;

import java.util.List;

public class ProductListIncrementVo {
	private float increment;
	private boolean isPercentage;
	private boolean isEndPrice;
	private List<ListProduct> listproducts;
	public float getIncrement() {
		return increment;
	}
	public void setIncrement(float increment) {
		this.increment = increment;
	}
	public boolean isPercentage() {
		return isPercentage;
	}
	public void setPercentage(boolean isPercentage) {
		this.isPercentage = isPercentage;
	}
	public boolean isEndPrice() {
		return isEndPrice;
	}
	public void setEndPrice(boolean isEndPrice) {
		this.isEndPrice = isEndPrice;
	}
	public List<ListProduct> getListproducts() {
		return listproducts;
	}
	public void setListproducts(List<ListProduct> listproducts) {
		this.listproducts = listproducts;
	}
	
}
