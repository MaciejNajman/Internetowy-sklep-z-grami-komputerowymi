package com.jsfproject.rejestracja;

import java.util.Date;
import java.util.ResourceBundle;

import javax.ejb.EJB;

import javax.enterprise.context.RequestScoped;
import javax.faces.annotation.ManagedProperty;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Inject;
import javax.inject.Named;

import jsf.project.dao.RolaDAO;
import jsf.project.dao.UzytkownikDAO;
import jsf.project.dao.UzytkownikRolaDAO;
import jsf.project.entities.Rola;
import jsf.project.entities.Uzytkownik;
import jsf.project.entities.UzytkownikRola;
import jsf.project.entities.UzytkownikRolaPK;

@Named
@RequestScoped
public class RejestracjaBB {
	private static final String PAGE_LOGIN = "/pages/login?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private Uzytkownik uzytkownik = new Uzytkownik();

	@EJB
	UzytkownikDAO uzytkownikDAO;
	@EJB
	UzytkownikRolaDAO uzytkownikRolaDAO;
	@EJB
	RolaDAO rolaDAO;

	@Inject
	FacesContext context;

	@Inject
	ExternalContext extcontext;

	@Inject
	Flash flash;

	// Resource injected
	@Inject
	@ManagedProperty("#{txtMain}")
	private ResourceBundle txtMain;

	public Uzytkownik getUzytkownik() {
		return uzytkownik;
	}

	public String saveRegisteredUser() {
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

		addDefaultUserRoleForNewCustomer(uzytkownikDAO.getIdOfNewlyCreatedUserFromDatabase());
		
		context.addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Rejestracja udana, podaj login i hasło", null));
		extcontext.getFlash().setKeepMessages(true);
		
		return PAGE_LOGIN;
	}

	public Rola getRoleByNameFromDB(String roleName) {
		Rola rola = null;
		// 1. Prepare search params
		String roleNameSearchParam = new String();

		if (roleName != null && roleName.length() > 0) {
			roleNameSearchParam = roleName;
		}
		// 2. Get list
		rola = rolaDAO.getRoleByName(roleNameSearchParam);

		return rola;
	}

	public void addDefaultUserRoleForNewCustomer(int userId) {
		Rola r = getRoleByNameFromDB("user");
		UzytkownikRolaPK key = new UzytkownikRolaPK(userId, r.getIdRola());
		UzytkownikRola entity = new UzytkownikRola();
		entity.setCompositeKey(key);

		Date now = new Date();
		entity.setKiedyNadanoRole(now);

		try {
			if (entity.getCompositeKey() != null) {
				// new record
				uzytkownikRolaDAO.create(entity);
			} else {
				// existing record
				uzytkownikRolaDAO.merge(entity);
			}
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wystąpił błąd podczas zapisu roli", null));
		}
	}
}
