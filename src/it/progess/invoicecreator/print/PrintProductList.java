package it.progess.invoicecreator.print;

import it.progess.invoicecreator.vo.Company;
import it.progess.invoicecreator.vo.Product;

public class PrintProductList extends PrintCompany {
	public PrintProductList(){
		super();
	}
	public String prodotto_aliquota;
	public String prodotto_codice= "";
	public String prodotto_desc = "";
	public String prodotto_um= "";
	public String prodotto_prezzo= "";
	
	public String getProdotto_aliquota() {
		return prodotto_aliquota;
	}
	public void setProdotto_aliquota(String prodotto_aliquota) {
		this.prodotto_aliquota = prodotto_aliquota;
	}
	public String getProdotto_codice() {
		return prodotto_codice;
	}
	public void setProdotto_codice(String prodotto_codice) {
		this.prodotto_codice = prodotto_codice;
	}
	public String getProdotto_desc() {
		return prodotto_desc;
	}
	public void setProdotto_desc(String prodotto_desc) {
		this.prodotto_desc = prodotto_desc;
	}
	public String getProdotto_um() {
		return prodotto_um;
	}
	public void setProdotto_um(String prodotto_um) {
		this.prodotto_um = prodotto_um;
	}
	public String getProdotto_prezzo() {
		return prodotto_prezzo;
	}
	public void setProdotto_prezzo(String prodotto_prezzo) {
		this.prodotto_prezzo = prodotto_prezzo;
	}
	public void setFromObject(Company comp,Product prod){
		super.setFromObject(comp);
		setFromObject(prod);
	}
	public void setFromObject(Product prod){
		this.prodotto_aliquota =String.valueOf(prod.getTaxrate().getValue());
		this.prodotto_codice = prod.getCode();
		this.prodotto_desc = prod.getDescription();
		this.prodotto_prezzo = String.valueOf(prod.getSellprice());
		this.prodotto_um = prod.getUmselected().getCode();
	}
}
