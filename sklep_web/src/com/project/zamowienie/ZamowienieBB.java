package com.project.zamowienie;

import java.io.IOException;
import java.io.Serializable;

import javax.ejb.EJB;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.faces.simplesecurity.RemoteClient;
import javax.enterprise.context.RequestScoped;
import javax.faces.annotation.ManagedProperty;

import jsf.project.dao.ZamowienieDAO;
import jsf.project.dao.GraHasZamowienieDAO;
import jsf.project.entities.Zamowienie;
import jsf.project.entities.Gra;
import jsf.project.entities.Uzytkownik;
import jsf.project.entities.GraHasZamowienie;

@Named
@ViewScoped
public class ZamowienieBB implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_MAIN = "pageMain?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private Zamowienie zamowienie = new Zamowienie();
	private GraHasZamowienie graZam = new GraHasZamowienie();
	private Zamowienie loaded = null;
	private GraHasZamowienie loaded1 = null;

	@EJB
	ZamowienieDAO zamowienieDAO;
	GraHasZamowienieDAO graHasZamowienieDAO;

	@Inject
	FacesContext context;

	@Inject
	Flash flash;

	public Zamowienie getZamowienie() {
		return zamowienie;
	}

	public void onLoad() throws IOException {

		// 1. load zamowienie passed through flash
		loaded = (Zamowienie) flash.get("zamowienie");
		//loaded1 = (GraHasZamowienie) flash.get("graHasZamowienie");

		// cleaning: attribute received => delete it from session
		if (loaded != null) {
			zamowienie = loaded;
			// session.removeAttribute("gra");
		} else {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błędne użycie systemu", null));
			// if (!context.isPostback()) { //possible redirect
			// context.getExternalContext().redirect("graList.xhtml");
			// context.responseComplete();
			// }
		}

//		if (loaded1 != null) {
//			graZam = loaded1;
//		} else {
//			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błędne użycie systemu", null));
//		}

	}

	public String saveOrder() {
		// no Zamowienie object passed
		if (loaded == null) {
			return PAGE_STAY_AT_THE_SAME;
		}

		try {
			if (zamowienie.getIdZamowienie() == null) {
				// new record
				zamowienieDAO.create(zamowienie);
			} else {
				// existing record
				zamowienieDAO.merge(zamowienie);
			}
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wystąpił błąd podczas zapisu", null));
			return PAGE_STAY_AT_THE_SAME;
		}
//		// no GraHasZamowienie object passed
//		if (loaded1 == null) {
//			return PAGE_STAY_AT_THE_SAME;
//		}
//
//		try {
//			if (graZam.getId() == null) {
//				// new record
//				graHasZamowienieDAO.create(graZam);
//			} else {
//				// existing record
//				graHasZamowienieDAO.merge(graZam);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			context.addMessage(null,
//					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wystąpił błąd podczas zapisu", null));
//			return PAGE_STAY_AT_THE_SAME;
//		}

		return PAGE_MAIN;
	}
}