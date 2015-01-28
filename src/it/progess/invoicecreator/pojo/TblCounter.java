package it.progess.invoicecreator.pojo;


import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;





import it.progess.invoicecreator.vo.Counter;
import it.progess.invoicecreator.vo.CounterYear;
import it.progess.invoicecreator.vo.Ivo;

@Entity
@Table(name="tblcounter")
public class TblCounter implements Itbl {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idCounter")
	private int idCounter;
	@Column(name="code")
	private String code;
	@Column(name="name")
	private String name;
	@Column(name="description")
	private String description;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "counter", cascade=javax.persistence.CascadeType.ALL)
	private Set<TblCounterYear> yearsvalue;
	@ManyToOne
	@JoinColumn(name = "idCompany")
	private TblCompany company;
	
	public TblCompany getCompany() {
		return company;
	}
	public void setCompany(TblCompany company) {
		this.company = company;
	}
	public Set<TblCounterYear> getYearsvalue() {
		return yearsvalue;
	}
	public void setYearsvalue(Set<TblCounterYear> yearsvalue) {
		this.yearsvalue = yearsvalue;
	}
	public int getIdCounter() {
		return idCounter;
	}
	public void setIdCounter(int idCounter) {
		this.idCounter = idCounter;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void convertToTable(Ivo vo){
		Counter co = (Counter)vo;
		this.code = co.getCode();
		this.description = co.getDescription();
		this.idCounter = co.getIdCounter();
		this.name = co.getName();
		if (co.getCompany() != null){
			this.company = new TblCompany();
			this.company.convertToTable(co.getCompany());
		}
		if (co.getYearsvalue() != null && co.getYearsvalue().size() > 0 ){
			this.yearsvalue = new HashSet<TblCounterYear>();
			for (Iterator<CounterYear> iterator = co.getYearsvalue().iterator(); iterator.hasNext();){
				CounterYear cy = iterator.next();
				TblCounterYear tcy = new TblCounterYear();
				tcy.convertToTable(cy);
				this.yearsvalue.add(tcy);
			}
		}else{
			this.yearsvalue = new HashSet<TblCounterYear>();
			TblCounterYear tcyNew = new TblCounterYear();
			tcyNew.setYear(new GregorianCalendar().get(Calendar.YEAR));
			tcyNew.setValue(0);
			tcyNew.setCounter(this);
			this.yearsvalue.add(tcyNew);
		}
	}
	public void convertToTableForSaving(Ivo vo){
		Counter co = (Counter)vo;
		this.code = co.getCode();
		this.description = co.getDescription();
		this.idCounter = co.getIdCounter();
		this.name = co.getName();
		if (co.getYearsvalue() != null && co.getYearsvalue().size() > 0 ){
			this.yearsvalue = new HashSet<TblCounterYear>();
			for (Iterator<CounterYear> iterator = co.getYearsvalue().iterator(); iterator.hasNext();){
				CounterYear cy = iterator.next();
				TblCounterYear tcy = new TblCounterYear();
				tcy.convertToTableForSaving(cy,this);
				this.yearsvalue.add(tcy);
			}
		}else{
			this.yearsvalue = new HashSet<TblCounterYear>();
			TblCounterYear tcyNew = new TblCounterYear();
			tcyNew.setYear(new GregorianCalendar().get(Calendar.YEAR));
			tcyNew.setValue(0);
			tcyNew.setCounter(this);
			this.yearsvalue.add(tcyNew);
		}
		if (co.getCompany() != null){
			this.company = new TblCompany();
			this.company.convertToTable(co.getCompany());
		}
	}
	
}
