package it.progess.invoicecreator.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import it.progess.invoicecreator.vo.DocumentFlow;
import it.progess.invoicecreator.vo.Ivo;
@Entity
@Table(name="tbldocumentflow")
public class TblDocumentFlow implements Itbl {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="iddocumentflow")
	private int iddocumentflow;
	@ManyToOne
	@JoinColumn(name = "iddocumentsource")
	private TblDocument documentSource;
	@ManyToOne
	@JoinColumn(name = "iddocumentresult")
	private TblDocument documentResult;
	@ManyToOne
	@JoinColumn(name = "idCompany")
	private TblCompany company;
	public int getIddocumentflow() {
		return iddocumentflow;
	}
	public void setIddocumentflow(int iddocumentflow) {
		this.iddocumentflow = iddocumentflow;
	}
	public TblDocument getDocumentSource() {
		return documentSource;
	}
	public void setDocumentSource(TblDocument documentSource) {
		this.documentSource = documentSource;
	}
	public TblDocument getDocumentResult() {
		return documentResult;
	}
	public void setDocumentResult(TblDocument documentResult) {
		this.documentResult = documentResult;
	}
	public TblCompany getCompany() {
		return company;
	}
	public void setCompany(TblCompany company) {
		this.company = company;
	}
	public void convertToTable(Ivo vo){
		DocumentFlow d = (DocumentFlow)vo;
		this.iddocumentflow = d.getIddocumentflow();
		if (d.getDocumentSource() != null){
			this.documentSource = new TblDocument();
			this.documentSource.convertToTable(d.getDocumentSource());
		}
		if (d.getDocumentResult() != null){
			this.documentResult = new TblDocument();
			this.documentResult.convertToTable(d.getDocumentResult());
		}
		if(d.getCompany() != null){
			this.company = new TblCompany();
			this.company.convertToTable(d.getCompany());
		}
	}
	
}
