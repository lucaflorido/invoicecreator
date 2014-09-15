package it.progess.invoicecreator.print;


import it.progess.invoicecreator.vo.Company;
import it.progess.invoicecreator.vo.Head;
import it.progess.invoicecreator.vo.Row;

public class PrintSingleHead extends PrintCompany {
	public PrintSingleHead(){
		super();
	}
	public String cliente_codice;
	public String cliente_ragionesociale;
	public String cliente_indirizzo;
	public String cliente_indirizzo2;
	public String cliente_pi;
	public String cliente_cf;
	public String prodotto_codice= "";
	public String prodotto_desc = "";
	public String prodotto_quantita= "";
	public String prodotto_prezzo= "";
	public String documento_tipo ;
	public String documento_numero ;
	public String documento_data ;
	public String documento_pagamento ;
	public String documento_iban ;
	public String prodotto_um ;
	public String prodotto_aliquota;
	public String prodotto_imponibile;
	public String prodotto_iva;
	public String prodotto_totale;
	public String prodotto_causale;
	public String prodotto_colli;
	public String tot_imp4;
	public String tot_iva4;
	public String tot_tot4;
	public String tot_imp10;
	public String tot_iva10;
	public String tot_tot10;
	public String tot_imp20;
	public String tot_iva20;
	public String tot_tot20;
	public String imp;
	public String iva;
	public String tot;
	public String tot_colli;
	public String tot_qta;
	public String ritenuta;
	public String percritenuta;
	
	public String getRitenuta() {
		return ritenuta;
	}
	public void setRitenuta(String ritenuta) {
		this.ritenuta = ritenuta;
	}
	public String getPercritenuta() {
		return percritenuta;
	}
	public void setPercritenuta(String percritenuta) {
		this.percritenuta = percritenuta;
	}
	public String getTot_colli() {
		return tot_colli;
	}
	public void setTot_colli(String tot_colli) {
		this.tot_colli = tot_colli;
	}
	public String getTot_qta() {
		return tot_qta;
	}
	public void setTot_qta(String tot_qta) {
		this.tot_qta = tot_qta;
	}
	public String getProdotto_colli() {
		return prodotto_colli;
	}
	public void setProdotto_colli(String prodotto_colli) {
		this.prodotto_colli = prodotto_colli;
	}
	public void setFromObject(Company comp,Head head,Row row){
		if (comp != null){
			super.setFromObject(comp);
		}
		this.setFromObject(head, row);
	}
	public void setFromObject(Head head,Row row){
		if (head.getCustomer() != null ){
			this.cliente_codice = head.getCustomer().getCustomercode();
			this.cliente_ragionesociale = head.getCustomer().getCustomername();
			this.cliente_indirizzo = head.getCustomer().getAddress().getAddress()+" "+head.getCustomer().getAddress().getNumber();
			this.cliente_indirizzo2 = head.getCustomer().getAddress().getZipcode()+" "+head.getCustomer().getAddress().getCity()+" ("+head.getCustomer().getAddress().getZone()+")";
			this.cliente_pi = head.getCustomer().getSerialnumber();
			this.cliente_cf = head.getCustomer().getTaxcode();
		}else if (head.getSupplier() != null && head.getDocument().getSupplier() == true){
			this.cliente_codice = head.getSupplier().getSuppliercode();
			this.cliente_ragionesociale = head.getSupplier().getSuppliername();
			this.cliente_indirizzo = head.getSupplier().getAddress().getAddress()+" "+head.getSupplier().getAddress().getNumber();
			this.cliente_indirizzo2 = head.getSupplier().getAddress().getZipcode()+" "+head.getSupplier().getAddress().getCity()+" ("+head.getSupplier().getAddress().getZone()+")";
			this.cliente_pi = head.getSupplier().getSerialnumber();
			this.cliente_cf = head.getSupplier().getTaxcode();
		}
			this.documento_tipo  = head.getDocument().getDescription();
			this.documento_numero =String.valueOf( head.getNumber()) ;
			this.documento_data = head.getDate() ;
			if (head.getPayment() != null)
			this.documento_pagamento = head.getPayment().getDescription() ;
			else{
				this.documento_pagamento = "";
			}
			this.tot_imp4 = String.valueOf( head.getAmount4());
			this.tot_iva4 = String.valueOf( head.getTaxamount4());
			this.tot_tot4 = String.valueOf( head.getTotal4());
			this.tot_imp20 = String.valueOf( head.getAmount20());
			this.tot_iva20 = String.valueOf( head.getTaxamount20());
			this.tot_tot20 = String.valueOf( head.getTotal20());
			this.tot_imp10 = String.valueOf( head.getAmount10());
			this.tot_iva10 = String.valueOf( head.getTaxamount10());
			this.tot_tot10 = String.valueOf( head.getTotal10());
			this.imp = String.valueOf( head.getAmount());
			this.iva = String.valueOf( head.getTaxamount());
			this.tot = String.valueOf( head.getTotal());
			this.ritenuta = String.valueOf( head.getWithholdingtax());
			if (head.getTaxrate() != null)
			this.percritenuta = String.valueOf( head.getTaxrate().getValue());
		
		if (row != null){
			this.prodotto_codice = row.getProductcode();
			this.prodotto_desc = row.getProductdescription();
			this.prodotto_quantita = String.valueOf(row.getQuantity());
			this.prodotto_prezzo = String.valueOf(row.getPrice());
			this.prodotto_um = String.valueOf(row.getProductum());
			this.prodotto_aliquota = String.valueOf(row.getProduct().getTaxrate().getValue());
			this.prodotto_iva = String.valueOf(row.getTaxamount());
			this.prodotto_imponibile = String.valueOf(row.getAmount());
			this.prodotto_totale = String.valueOf(row.getTotal());
			this.prodotto_causale = row.getType();
			this.prodotto_colli = String.valueOf(row.getNecks());
		}
	}
	public String getCliente_codice() {
		return cliente_codice;
	}
	public void setCliente_codice(String cliente_codice) {
		this.cliente_codice = cliente_codice;
	}
	public String getCliente_ragionesociale() {
		return cliente_ragionesociale;
	}
	public void setCliente_ragionesociale(String cliente_ragionesociale) {
		this.cliente_ragionesociale = cliente_ragionesociale;
	}
	
	public String getProdotto_codice() {
		return prodotto_codice;
	}
	public void setProdotto_codice(String prodotto_codice) {
		this.prodotto_codice = prodotto_codice;
	}
	
	public String getProdotto_quantita() {
		return prodotto_quantita;
	}
	public void setProdotto_quantita(String prodotto_quantita) {
		this.prodotto_quantita = prodotto_quantita;
	}
	public String getProdotto_prezzo() {
		return prodotto_prezzo;
	}
	public void setProdotto_prezzo(String prodotto_prezzo) {
		this.prodotto_prezzo = prodotto_prezzo;
	}
	public String getProdotto_desc() {
		return prodotto_desc;
	}
	public void setProdotto_desc(String prodotto_desc) {
		this.prodotto_desc = prodotto_desc;
	}
	public String getCliente_indirizzo() {
		return cliente_indirizzo;
	}
	public void setCliente_indirizzo(String cliente_indirizzo) {
		this.cliente_indirizzo = cliente_indirizzo;
	}
	public String getCliente_indirizzo2() {
		return cliente_indirizzo2;
	}
	public void setCliente_indirizzo2(String cliente_indirizzo2) {
		this.cliente_indirizzo2 = cliente_indirizzo2;
	}
	public String getCliente_pi() {
		return cliente_pi;
	}
	public void setCliente_pi(String cliente_pi) {
		this.cliente_pi = cliente_pi;
	}
	public String getCliente_cf() {
		return cliente_cf;
	}
	public void setCliente_cf(String cliente_cf) {
		this.cliente_cf = cliente_cf;
	}
	public String getDocumento_tipo() {
		return documento_tipo;
	}
	public void setDocumento_tipo(String documento_tipo) {
		this.documento_tipo = documento_tipo;
	}
	public String getDocumento_numero() {
		return documento_numero;
	}
	public void setDocumento_numero(String documento_numero) {
		this.documento_numero = documento_numero;
	}
	public String getDocumento_data() {
		return documento_data;
	}
	public void setDocumento_data(String documento_data) {
		this.documento_data = documento_data;
	}
	public String getDocumento_pagamento() {
		return documento_pagamento;
	}
	public void setDocumento_pagamento(String documento_pagamento) {
		this.documento_pagamento = documento_pagamento;
	}
	public String getDocumento_iban() {
		return documento_iban;
	}
	public void setDocumento_iban(String documento_iban) {
		this.documento_iban = documento_iban;
	}
	public String getProdotto_um() {
		return prodotto_um;
	}
	public void setProdotto_um(String prodotto_um) {
		this.prodotto_um = prodotto_um;
	}
	public String getProdotto_aliquota() {
		return prodotto_aliquota;
	}
	public void setProdotto_aliquota(String prodotto_aliquota) {
		this.prodotto_aliquota = prodotto_aliquota;
	}
	public String getProdotto_imponibile() {
		return prodotto_imponibile;
	}
	public void setProdotto_imponibile(String prodotto_imponibile) {
		this.prodotto_imponibile = prodotto_imponibile;
	}
	public String getProdotto_iva() {
		return prodotto_iva;
	}
	public void setProdotto_iva(String prodotto_iva) {
		this.prodotto_iva = prodotto_iva;
	}
	public String getProdotto_totale() {
		return prodotto_totale;
	}
	public void setProdotto_totale(String prodotto_totale) {
		this.prodotto_totale = prodotto_totale;
	}
	public String getProdotto_causale() {
		return prodotto_causale;
	}
	public void setProdotto_causale(String prodotto_causale) {
		this.prodotto_causale = prodotto_causale;
	}
	public String getTot_imp4() {
		return tot_imp4;
	}
	public void setTot_imp4(String tot_imp4) {
		this.tot_imp4 = tot_imp4;
	}
	public String getTot_iva4() {
		return tot_iva4;
	}
	public void setTot_iva4(String tot_iva4) {
		this.tot_iva4 = tot_iva4;
	}
	public String getTot_tot4() {
		return tot_tot4;
	}
	public void setTot_tot4(String tot_tot4) {
		this.tot_tot4 = tot_tot4;
	}
	public String getTot_imp10() {
		return tot_imp10;
	}
	public void setTot_imp10(String tot_imp10) {
		this.tot_imp10 = tot_imp10;
	}
	public String getTot_iva10() {
		return tot_iva10;
	}
	public void setTot_iva10(String tot_iva10) {
		this.tot_iva10 = tot_iva10;
	}
	public String getTot_tot10() {
		return tot_tot10;
	}
	public void setTot_tot10(String tot_tot10) {
		this.tot_tot10 = tot_tot10;
	}
	public String getTot_imp20() {
		return tot_imp20;
	}
	public void setTot_imp20(String tot_imp20) {
		this.tot_imp20 = tot_imp20;
	}
	public String getTot_iva20() {
		return tot_iva20;
	}
	public void setTot_iva20(String tot_iva20) {
		this.tot_iva20 = tot_iva20;
	}
	public String getTot_tot20() {
		return tot_tot20;
	}
	public void setTot_tot20(String tot_tot20) {
		this.tot_tot20 = tot_tot20;
	}
	public String getImp() {
		return imp;
	}
	public void setImp(String imp) {
		this.imp = imp;
	}
	public String getIva() {
		return iva;
	}
	public void setIva(String iva) {
		this.iva = iva;
	}
	public String getTot() {
		return tot;
	}
	public void setTot(String tot) {
		this.tot = tot;
	}
	
	
}
