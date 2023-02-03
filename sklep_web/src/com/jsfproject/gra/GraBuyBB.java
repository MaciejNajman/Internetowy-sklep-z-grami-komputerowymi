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
import javax.servlet.http.HttpSession;

import jsf.project.dao.GraDAO;
import jsf.project.entities.Gra;
import jsf.project.entities.GraHasZamowienie;
import jsf.project.entities.Uzytkownik;
import jsf.project.entities.Zamowienie;

@Named
@ViewScoped
public class GraBuyBB implements Serializable { //na razie to samo co w GraEditBB
	private static final long serialVersionUID = 1L;
	
	private static final String PAGE_ORDER = "pageZamowienie?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private Gra gra = new Gra();
	private GraHasZamowienie graHasZamowieny = new GraHasZamowienie();
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
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String formattedDateTime = localDateTime.format(myFormatObj);
		z.setDataZlozeniaZamowienia(formattedDateTime);
		z.setCzyPrzyjetoZamowienie((byte)0);
		z.setUzytkownik(u);
		
		//Pass object through flash	
		flash.put("zamowienie", z);
		
		return PAGE_ORDER;
	}
}
