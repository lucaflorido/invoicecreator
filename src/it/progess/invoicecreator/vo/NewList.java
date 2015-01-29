package it.progess.invoicecreator.vo;

public class NewList {
	private List list;
	private boolean isPercentage;
	private float value;
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
	public boolean isPercentage() {
		return isPercentage;
	}
	public void setPercentage(boolean isPercentage) {
		this.isPercentage = isPercentage;
	}
	public float getValue() {
		return value;
	}
	public void setValue(float value) {
		this.value = value;
	}
	
}
