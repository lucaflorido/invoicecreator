package it.progess.invoicecreator.vo;

import it.progess.invoicecreator.pojo.Itbl;
import it.progess.invoicecreator.pojo.TblGenerateHeadRow;



public class GenerateHeadRow implements Ivo {
	private int idGenerateHeadRow;
	private Head headSource;
	private Row rowSource;
	private Head headGenerate;
	public int getIdGenerateHeadRow() {
		return idGenerateHeadRow;
	}
	public void setIdGenerateHeadRow(int idGenerateHeadRow) {
		this.idGenerateHeadRow = idGenerateHeadRow;
	}
	public Head getHeadSource() {
		return headSource;
	}
	public void setHeadSource(Head headSource) {
		this.headSource = headSource;
	}
	public Row getRowSource() {
		return rowSource;
	}
	public void setRowSource(Row rowSource) {
		this.rowSource = rowSource;
	}
	public Head getHeadGenerate() {
		return headGenerate;
	}
	public void setHeadGenerate(Head headGenerate) {
		this.headGenerate = headGenerate;
	}
	public void convertFromTable(Itbl obj){
		TblGenerateHeadRow ghr = (TblGenerateHeadRow)obj;
		this.idGenerateHeadRow = ghr.getIdGenerateHeadRow();
		if (ghr.getHeadGenerate() != null){
			this.headGenerate = new Head();
			this.headGenerate.convertFromTable(ghr.getHeadGenerate());
		}
		if (ghr.getHeadSource() != null){
			this.headSource = new Head();
			this.headSource.convertFromTable(ghr.getHeadSource());
		}
		if (ghr.getRowSource() != null){
			this.rowSource = new Row();
			this.rowSource.convertFromTable(ghr.getRowSource());
		}
	}
	public GECOObject control(){
		return new GECOSuccess();
	}
}
