package com.jsfproject.gra;

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

import jsf.project.dao.GraDAO;
import jsf.project.entities.Gra;

@Named
@ViewScoped
public class GraBuyBB implements Serializable { //na razie to samo co w GraEditBB
	private static final long serialVersionUID = 1L;

	private static final String PAGE_GRA_ORDER = "graOrder?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

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
}
