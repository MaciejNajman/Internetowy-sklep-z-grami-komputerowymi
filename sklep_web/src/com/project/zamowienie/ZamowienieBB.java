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

import jsf.project.dao.ZamowienieDAO;
import jsf.project.entities.Zamowienie;

@Named
@ViewScoped
public class ZamowienieBB implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_GRA_ORDER = "pageZamowienie?faces-redirect=true";
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

	public String confirmOrder() { //dodawanie zamowienia na dana gre
		// no zamowienie object passed
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
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "wystąpił błąd podczas zapisu", null));
			return PAGE_STAY_AT_THE_SAME;
		}

		return PAGE_GRA_ORDER;
	}
}