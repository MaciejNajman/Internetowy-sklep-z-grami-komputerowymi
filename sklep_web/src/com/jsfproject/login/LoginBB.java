package com.jsfproject.login;

import java.util.ResourceBundle;

import javax.ejb.EJB;

import javax.enterprise.context.RequestScoped;
import javax.faces.annotation.ManagedProperty;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.simplesecurity.RemoteClient;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jsf.project.dao.UzytkownikDAO;
import jsf.project.entities.Uzytkownik;
import jsf.project.entities.UzytkownikRola;

@Named
@RequestScoped
public class LoginBB {

	private static final String PAGE_MAIN = "/pages/user/pageMain?faces-redirect=true";
	private static final String PAGE_LOGIN = "/pages/login?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;
	private static final String PAGE_REGISTER = "/pages/register?faces-redirect=true";

	private String pass;
	private String login;

	// Resource injected
	@Inject
	@ManagedProperty("#{txtMain}")
	private ResourceBundle txtMain;

	@Inject
	Flash flash;
	@Inject
	ExternalContext extcontext;
	@EJB
	UzytkownikDAO uzytkownikDAO;

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String doLogin() {
		FacesContext ctx = FacesContext.getCurrentInstance();

		// 1. verify login and password - get User from "database"
		Uzytkownik uzytkownik = uzytkownikDAO.getUserFromDatabase(login, pass);

		// 2. if bad login or password - stay with error info
		if (uzytkownik == null) {
			ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Niepoprawny login lub hasło", null));
			return PAGE_STAY_AT_THE_SAME;
		}

		// 3. if logged in: get User roles, save in RemoteClient and store it in session

		RemoteClient<Uzytkownik> client = new RemoteClient<Uzytkownik>(); // create new RemoteClient
		client.setDetails(uzytkownik);

		// List<String> roles = uzytkownikDAO.getUserRolesFromDatabase(uzytkownik);
		// //get User roles

		if (uzytkownik.getUzytkownikRolas() != null) { // save role names in RemoteClient od prof
			for (UzytkownikRola ur : uzytkownik.getUzytkownikRolas()) {
				client.getRoles().add(ur.getRola().getNazwaRoli());
			}
		}

		// store RemoteClient with request info in session (needed for SecurityFilter)
		HttpServletRequest request = (HttpServletRequest) ctx.getExternalContext().getRequest();
		client.store(request);

		// !!!pobranie obiektu uzytkownika do obslugi zamowien i wszystkiego!!!
		// HttpServletRequest req = (HttpServletRequest)
		// ctx.getExternalContext().getRequest();
		// RemoteClient<Uzytkownik> c = RemoteClient.load(req.getSession());
		// Uzytkownik u = c.getDetails();

		// and enter the system (now SecurityFilter will pass the request)
		return PAGE_MAIN;
	}

	public String doLogout() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		// Invalidate session
		// - all objects within session will be destroyed
		// - new session will be created (with new ID)
		session.invalidate();
		return PAGE_LOGIN;
	}

	public String registerNewUser() {
		//Tworzenie nowego uzytkownika
		//Uzytkownik uzytkownik = new Uzytkownik();
		// 1. Pass object through session
		// HttpSession session = (HttpSession) extcontext.getSession(true);
		// session.setAttribute("uzytkownik", uzytkownik);

		// 2. Pass object through flash
		//flash.put("uzytkownik", uzytkownik);

		return PAGE_REGISTER;
	}
}