package com.jsfproject.gra;

import java.io.IOException;
import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import jsf.project.dao.GraDAO;
import jsf.project.entities.Gra;

@Named
@ViewScoped
public class GraEditGETBB implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_GRA_LIST = "graList?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private Gra gra = new Gra();
	private Gra loaded = null;

	@Inject
	FacesContext context;

	@EJB
	GraDAO graDAO;

	public Gra getGra() {
		return gra;
	}

	public void onLoad() throws IOException {
		if (!context.isPostback()) {
			if (!context.isValidationFailed() && gra.getIdGra() != null) {
				loaded = graDAO.find(gra.getIdGra());
			}
			if (loaded != null) {
				gra = loaded;
			} else {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błędne użycie systemu", null));
				// if (!context.isPostback()) { // possible redirect
				// context.getExternalContext().redirect("personList.xhtml");
				// context.responseComplete();
				// }
			}
		}

	}

	public String saveData() {
		// no Person object passed
		if (loaded == null) {
			return PAGE_STAY_AT_THE_SAME;
		}

		try {
			if (gra.getIdGra() == null) {
				// new record
				graDAO.create(gra);
			} else {
				// existing record
				graDAO.merge(gra);
			}
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wystąpił błąd podczas zapisu", null));
			return PAGE_STAY_AT_THE_SAME;
		}

		return PAGE_GRA_LIST;
	}
}
