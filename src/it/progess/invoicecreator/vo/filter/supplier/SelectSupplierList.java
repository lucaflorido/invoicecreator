package it.progess.invoicecreator.vo.filter.supplier;


import it.progess.invoicecreator.vo.CategorySupplier;
import it.progess.invoicecreator.vo.GroupSupplier;
import it.progess.invoicecreator.vo.filter.PagesFilter;

public class SelectSupplierList {
	private PagesFilter pagefilter;
	private String search;
	private GroupSupplier group;
	private CategorySupplier category;
	public PagesFilter getPagefilter() {
		return pagefilter;
	}
	public void setPagefilter(PagesFilter pagefilter) {
		this.pagefilter = pagefilter;
	}
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public GroupSupplier getGroup() {
		return group;
	}
	public void setGroup(GroupSupplier group) {
		this.group = group;
	}
	public CategorySupplier getCategory() {
		return category;
	}
	public void setCategory(CategorySupplier category) {
		this.category = category;
	}
	
}
