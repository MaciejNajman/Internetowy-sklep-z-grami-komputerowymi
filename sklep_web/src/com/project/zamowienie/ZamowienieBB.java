package com.project.zamowienie;

import java.io.IOException;
import java.io.Serializable;

import javax.ejb.EJB;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import jsf.project.dao.ZamowienieDAO;
import jsf.project.dao.GraHasZamowienieDAO;
import jsf.project.entities.Zamowienie;
import jsf.project.entities.Gra;
import jsf.project.entities.GraHasZamowienie;
import jsf.project.entities.GraHasZamowieniePK;

@Named
@ViewScoped
public class ZamowienieBB implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_MAIN = "pageMain?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private Zamowienie zamowienie = new Zamowienie();
	private GraHasZamowienie graZam = new GraHasZamowienie();
	private Gra gra = new Gra();
	private Zamowienie loaded = null;
	private Gra loaded1 = null;

	@EJB
	ZamowienieDAO zamowienieDAO;
	@EJB
	GraHasZamowienieDAO graHasZamowienieDAO;

	@Inject
	FacesContext context;

	@Inject
	ExternalContext extcontext;

	@Inject
	Flash flash;

	public Zamowienie getZamowienie() {
		return zamowienie;
	}

	public GraHasZamowienie getGraHasZamowienia() {
		return graZam;
	}

	public void onLoad() throws IOException {

		// 1. load zamowienie passed through flash
		loaded = (Zamowienie) flash.get("zamowienie");
		loaded1 = (Gra) flash.get("gra");

		// cleaning: attribute received => delete it from session
		if (loaded != null && loaded1 != null) {
			zamowienie = loaded;
			gra = loaded1;
			// session.removeAttribute("gra");
		} else {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błędne użycie systemu", null));
			// if (!context.isPostback()) { //possible redirect
			// context.getExternalContext().redirect("graList.xhtml");
			// context.responseComplete();
			// }
		}
	}

	public String saveOrder() {
		// no Zamowienie object passed
		if (loaded == null || loaded1 == null) {
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

		createGraHasZamowienieRecord();
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
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sukces, złożono zamówienie", null));
		extcontext.getFlash().setKeepMessages(true);

		return PAGE_MAIN;
	}

	public void createGraHasZamowienieRecord() {
		// Dodawanie gry do zamowienia
		Integer idGra = gra.getIdGra();
		//Integer idZamowienie = zamowienie.getIdZamowienie();
		Integer idZamowienie = zamowienieDAO.getIdOfNewlyCreatedZamowienieFromDatabase();
		GraHasZamowieniePK key = new GraHasZamowieniePK(idGra, idZamowienie);
		GraHasZamowienie graHasZamowienia = new GraHasZamowienie();
		graHasZamowienia.setId(key);
		graHasZamowienia.setIloscSztuk(1);

		try {
			if (graZam.getId() != null) {
				// new record
				graHasZamowienieDAO.create(graZam);
			} else {
				// existing record
				graHasZamowienieDAO.merge(graZam);
			}
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Wystąpił błąd podczas zapisu gry do zamówienia", null));
		}
	}
}