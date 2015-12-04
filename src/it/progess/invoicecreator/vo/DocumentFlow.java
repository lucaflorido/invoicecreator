package it.progess.invoicecreator.vo;

import it.progess.invoicecreator.pojo.Itbl;

import it.progess.invoicecreator.pojo.TblDocumentFlow;




public class DocumentFlow implements Ivo {
	private int iddocumentflow;
	private Document documentSource;
	private Document documentResult;
	private Company company;
	public int getIddocumentflow() {
		return iddocumentflow;
	}
	public void setIddocumentflow(int iddocumentflow) {
		this.iddocumentflow = iddocumentflow;
	}
	public Document getDocumentSource() {
		return documentSource;
	}
	public void setDocumentSource(Document documentSource) {
		this.documentSource = documentSource;
	}
	public Document getDocumentResult() {
		return documentResult;
	}
	public void setDocumentResult(Document documentResult) {
		this.documentResult = documentResult;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public void convertFromTable(Itbl tb){
		TblDocumentFlow d = (TblDocumentFlow)tb;
		this.iddocumentflow = d.getIddocumentflow();
		if (d.getDocumentSource() != null){
			this.documentSource = new Document();
			this.documentSource.convertFromTable(d.getDocumentSource());
		}
		if (d.getDocumentResult() != null){
			this.documentResult = new Document();
			this.documentResult.convertFromTable(d.getDocumentResult());
		}
		if(d.getCompany() != null){
			this.company = new Company();
			this.company.convertFromTable(d.getCompany());
		}
	}
	public GECOObject control(){
		return null;
	}
}
