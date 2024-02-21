package com.jsfproject.gra;

import java.io.IOException;
import java.io.Serializable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.simplesecurity.RemoteClient;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import jsf.project.dao.GraDAO;
import jsf.project.entities.Gra;
import jsf.project.entities.GraHasZamowienie;
import jsf.project.entities.GraHasZamowieniePK;
import jsf.project.entities.Uzytkownik;
import jsf.project.entities.Zamowienie;

@Named
@ViewScoped
public class GraBuyBB implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private static final String PAGE_ORDER = "pageZamowienie?faces-redirect=true";

	private Gra gra = new Gra();
	private Gra loaded = null;

	@EJB
	GraDAO graDAO;

	@Inject
	FacesContext context;

	@Inject
	Flash flash;

	public Gra getGra() {
		return gra;
	}

	public void onLoad() throws IOException {
		// 1. load gra passed through session
		// HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
		// loaded = (Gra) session.getAttribute("gra");

		// 2. load gra passed through flash
		loaded = (Gra) flash.get("gra");

		// cleaning: attribute received => delete it from session
		if (loaded != null) {
			gra = loaded;
			// session.removeAttribute("gra");
		} else {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błędne użycie systemu", null));
			// if (!context.isPostback()) { //possible redirect
			// context.getExternalContext().redirect("graList.xhtml");
			// context.responseComplete();
			// }
		}
	}
	
	public String noweZamowienie() { //dodawanie zamowienia na dana gre
		FacesContext ctx = FacesContext.getCurrentInstance();
		
		//!!!pobranie obiektu uzytkownika do obslugi zamowien!!!
		HttpServletRequest req = (HttpServletRequest) ctx.getExternalContext().getRequest();
		RemoteClient<Uzytkownik> c = RemoteClient.load(req.getSession());
		Uzytkownik u = c.getDetails();
		
		//Tworzenie nowego zamowienia
		Zamowienie z = new Zamowienie();
		LocalDateTime localDateTime = LocalDateTime.now();
		LocalDateTime localDateTimePlus3Days = localDateTime.plusDays(3);
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String formattedDateTime = localDateTime.format(myFormatObj);
		String formattedLocalDateTimePlus3Days = localDateTimePlus3Days.format(myFormatObj);
		z.setDataZlozeniaZamowienia(formattedDateTime);
		z.setCzyPrzyjetoZamowienie((byte)1);
		z.setZaplacono(0);
		z.setCzyZamowienieZrealizowano((byte)0);
		z.setDataRealizacjiZamowienia(formattedLocalDateTimePlus3Days);
		z.setUzytkownik(u);
		
		//Dodawanie gry do zamowienia
//		Integer idGra = gra.getIdGra();
//		Integer idZamowienie = 0;
//		GraHasZamowieniePK key = new GraHasZamowieniePK(idGra, idZamowienie);
//		GraHasZamowienie graHasZamowienia = new GraHasZamowienie();
//		graHasZamowienia.setId(key);
//		graHasZamowienia.setIloscSztuk(1);
		
		//Pass object through flash	
		flash.put("zamowienie", z);
		flash.put("gra", gra);
		
		return PAGE_ORDER;
	}
}
