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
import jsf.project.entities.Zamowienie;
import jsf.project.entities.Gra;
import jsf.project.entities.Uzytkownik;

@Named
@ViewScoped
public class ZamowienieBB implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private static final String PAGE_MAIN = "pageMain?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private Zamowienie zamowienie = new Zamowienie();
	private Zamowienie loaded = null;

	@EJB
	ZamowienieDAO zamowienieDAO;

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

		return PAGE_MAIN;
	}
}