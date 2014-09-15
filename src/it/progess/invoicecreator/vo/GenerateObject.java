package it.progess.invoicecreator.vo;

public class GenerateObject {
	private Document document;
	private String date;
	private GroupCustomer group;
	private String historyDate;
	public GroupCustomer getGroup() {
		return group;
	}
	public void setGroup(GroupCustomer group) {
		this.group = group;
	}
	public Document getDocument() {
		return document;
	}
	public void setDocument(Document document) {
		this.document = document;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getHistoryDate() {
		return historyDate;
	}
	public void setHistoryDate(String historyDate) {
		this.historyDate = historyDate;
	}
	
}
