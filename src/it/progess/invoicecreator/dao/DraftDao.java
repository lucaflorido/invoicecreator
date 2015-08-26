package it.progess.invoicecreator.dao;

import java.util.ArrayList;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import it.progess.invoicecreator.vo.Draft;
import it.progess.invoicecreator.vo.GECOObject;
import it.progess.invoicecreator.vo.GECOSuccess;
import it.progess.invoicecreator.vo.Product;

public class DraftDao {
	/*gestisce id draft ed un oggetto draft in sessione*/
	public GECOObject setupDraft(String draftid,HttpSession session){
		Draft draft = null;
		if (draftid != null && draftid != ""){
			draft = (Draft)session.getAttribute(draftid);
			if (draft == null){
				UUID ui = UUID.randomUUID();
				draftid = ui.toString();
				draft = new Draft();
				draft.setId(draftid);
				draft.setProducts(new ArrayList<Product>());
				session.setAttribute(draftid,draft);
			}
		}else{
			UUID ui = UUID.randomUUID();
			draftid = ui.toString();
			draft = new Draft();
			draft.setId(draftid);
			draft.setProducts(new ArrayList<Product>());
			session.setAttribute(draftid,draft);
		}
		return new GECOSuccess(draft);
	}
	/*aggiunge un oggetto al draft*/
	public GECOObject addToDraft(String draftid,HttpSession session,Product product){
		Draft draft = (Draft)session.getAttribute(draftid);
		draft.getProducts().add(product);
		return new GECOSuccess(draft);
	}
	/*rimuove un oggetto dal draft*/
	/*modifica oggetto dal draft*/
	/*calcola totale*/
	/*chiude il carrello*/
}
