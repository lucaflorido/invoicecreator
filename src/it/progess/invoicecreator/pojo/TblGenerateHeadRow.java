package it.progess.invoicecreator.pojo;

import it.progess.invoicecreator.vo.GenerateHeadRow;
import it.progess.invoicecreator.vo.Ivo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table(name="tblgenerateheadrow")
public class TblGenerateHeadRow implements Itbl {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idGenerateHeadRow")
	private int idGenerateHeadRow;
	@ManyToOne
	@JoinColumn(name = "idHeadSource")
	private TblHead headSource;
	@ManyToOne
	@JoinColumn(name = "idRowSource")
	private TblRow rowSource;
	@ManyToOne
	@JoinColumn(name = "idHeadGenerate")
	private TblHead headGenerate;
	public int getIdGenerateHeadRow() {
		return idGenerateHeadRow;
	}
	public void setIdGenerateHeadRow(int idGenerateHeadRow) {
		this.idGenerateHeadRow = idGenerateHeadRow;
	}
	public TblHead getHeadSource() {
		return headSource;
	}
	public void setHeadSource(TblHead headSource) {
		this.headSource = headSource;
	}
	public TblRow getRowSource() {
		return rowSource;
	}
	public void setRowSource(TblRow rowSource) {
		this.rowSource = rowSource;
	}
	public TblHead getHeadGenerate() {
		return headGenerate;
	}
	public void setHeadGenerate(TblHead headGenerate) {
		this.headGenerate = headGenerate;
	}
	public void convertToTable(Ivo obj){
		GenerateHeadRow ghr = (GenerateHeadRow)obj;
		this.idGenerateHeadRow = ghr.getIdGenerateHeadRow();
		if (ghr.getHeadGenerate() != null){
			this.headGenerate = new TblHead();
			this.headGenerate.convertToTable(ghr.getHeadGenerate());
		}
		if (ghr.getHeadSource() != null){
			this.headSource = new TblHead();
			this.headSource.convertToTable(ghr.getHeadSource());
		}
		if (ghr.getRowSource() != null){
			this.rowSource = new TblRow();
			this.rowSource.convertToTable(ghr.getRowSource());
		}
	}
}
