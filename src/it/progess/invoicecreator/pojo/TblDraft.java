package it.progess.invoicecreator.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tbldraft")
public class TblDraft  {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="iddraft")
	private int iddraft;
	@Column(name="code")
	private String code;
	@Column(name="value")
	private String value;
	@Column(name="creationdate")
	private Date creationdate;
	public int getIddraft() {
		return iddraft;
	}
	public void setIddraft(int iddraft) {
		this.iddraft = iddraft;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Date getCreationdate() {
		return creationdate;
	}
	public void setCreationdate(Date creationdate) {
		this.creationdate = creationdate;
	}
	
}
