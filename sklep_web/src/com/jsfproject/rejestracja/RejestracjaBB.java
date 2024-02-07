package com.jsfproject.rejestracja;

import java.io.IOException;
import java.util.Date;
import java.util.ResourceBundle;

import javax.ejb.EJB;

import javax.enterprise.context.RequestScoped;
import javax.faces.annotation.ManagedProperty;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Inject;
import javax.inject.Named;

import jsf.project.dao.UzytkownikDAO;
import jsf.project.entities.Uzytkownik;
import jsf.project.entities.UzytkownikRola;

@Named
@RequestScoped
public class RejestracjaBB {
	private static final String PAGE_LOGIN = "/pages/login?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private Uzytkownik uzytkownik = new Uzytkownik();
	private Uzytkownik loaded = null;

	@EJB
	UzytkownikDAO uzytkownikDAO;

	@Inject
	FacesContext context;

	@Inject
	Flash flash;

	// Resource injected
	@Inject
	@ManagedProperty("#{txtMain}")
	private ResourceBundle txtMain;

	public Uzytkownik getUzytkownik() {
		return uzytkownik;
	}

	public void onLoad() throws IOException {
		// 1. load uzytkownik passed through session
		// HttpSession session = (HttpSession)
		// context.getExternalContext().getSession(true);
		// loaded = (Uzytkownik) session.getAttribute("uzytkownik");

		// 2. load uzytkownik passed through flash
		loaded = (Uzytkownik) flash.get("uzytkownik");

		//cleaning: attribute received => delete it from session
		if (loaded != null) {
			uzytkownik = loaded;
		} else {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błędne użycie systemu", null));
		}
	}
	
	public void addDefaultUserRoleForNewCustomer() {
		UzytkownikRola r = new UzytkownikRola();
		Date now = new Date();
		r.setKiedyNadanoRole(now);
		r.setUzytkownik(uzytkownik);
		uzytkownik.addUzytkownikRola(r);
	}

	public String saveRegisteredUser() {
		// no Uzytkownik object passed
		if (loaded == null) {
			return PAGE_STAY_AT_THE_SAME;
		}
		
		addDefaultUserRoleForNewCustomer();
		
		try {
			if (uzytkownik.getIdUzytkownik() == null) {
				// new record
				uzytkownikDAO.create(uzytkownik);
			} else {
				// existing record
				uzytkownikDAO.merge(uzytkownik);
			}
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wystąpił błąd podczas zapisu", null));
			return PAGE_STAY_AT_THE_SAME;
		}
		context.addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Utwórz konto", "Sukces, podaj login i hasło"));
		return PAGE_LOGIN;
	}
}
