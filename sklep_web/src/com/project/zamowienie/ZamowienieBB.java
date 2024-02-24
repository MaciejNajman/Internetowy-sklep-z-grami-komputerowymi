package com.project.zamowienie;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.simplesecurity.RemoteClient;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import jsf.project.dao.ZamowienieDAO;
import jsf.project.dto.ZamowienieDTO;
import jsf.project.dao.GraDAO;
import jsf.project.dao.GraHasZamowienieDAO;
import jsf.project.entities.Zamowienie;
import jsf.project.entities.Gra;
import jsf.project.entities.GraHasZamowienie;
import jsf.project.entities.GraHasZamowieniePK;
import jsf.project.entities.Uzytkownik;

@Named
@ViewScoped
public class ZamowienieBB implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String PAGE_MAIN = "pageMain?faces-redirect=true";
	private static final String PAGE_USER_ORDERS = "pageUserOrders?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private Zamowienie zamowienie = new Zamowienie();
	private GraHasZamowienie graZam = new GraHasZamowienie();
	private Gra gra = new Gra();
	private Zamowienie loaded = null;
	private Gra loaded1 = null;
	private List<ZamowienieDTO> ordersList = new ArrayList<>();
	private ZamowienieDTO selectedOrder;

	// placeholder values until payment gateway is implemented
	private String console;
	private String imie;
	private String nazwisko;
	private String miasto;
	private String adresRozliczeniowy;
	private String kodPocztowy;
	private String kraj;

	@EJB
	ZamowienieDAO zamowienieDAO;
	@EJB
	GraHasZamowienieDAO graHasZamowienieDAO;
	@EJB
	GraDAO graDAO;

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

	public String getConsole() {
		return console;
	}

	public void setConsole(String console) {
		this.console = console;
	}

	public String getImie() {
		return imie;
	}

	public void setImie(String imie) {
		this.imie = imie;
	}

	public String getNazwisko() {
		return nazwisko;
	}

	public void setNazwisko(String nazwisko) {
		this.nazwisko = nazwisko;
	}

	public String getMiasto() {
		return miasto;
	}

	public void setMiasto(String miasto) {
		this.miasto = miasto;
	}

	public String getAdresRozliczeniowy() {
		return adresRozliczeniowy;
	}

	public void setAdresRozliczeniowy(String adresRozliczeniowy) {
		this.adresRozliczeniowy = adresRozliczeniowy;
	}

	public String getKodPocztowy() {
		return kodPocztowy;
	}

	public void setKodPocztowy(String kodPocztowy) {
		this.kodPocztowy = kodPocztowy;
	}

	public String getKraj() {
		return kraj;
	}

	public void setKraj(String kraj) {
		this.kraj = kraj;
	}

	public ZamowienieDTO getSelectedOrder() {
		return selectedOrder;
	}

	public void setSelectedOrder(ZamowienieDTO selectedOrder) {
		this.selectedOrder = selectedOrder;
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
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sukces", "Złożono zamówienie"));
		extcontext.getFlash().setKeepMessages(true);

		return PAGE_MAIN;
	}

	public void createGraHasZamowienieRecord() {
		// Dodawanie gry do zamowienia
		Integer idGra = gra.getIdGra();
		// Integer idZamowienie = zamowienie.getIdZamowienie();
		Integer idZamowienie = zamowienieDAO.getIdOfNewlyCreatedZamowienieFromDatabase();
		GraHasZamowieniePK key = new GraHasZamowieniePK(idGra, idZamowienie);
		GraHasZamowienie graHasZamowienia = new GraHasZamowienie();
		graHasZamowienia.setId(key);
		graHasZamowienia.setIloscSztuk(1);

		try {
			if (graHasZamowienia.getId() != null) {
				// new record
				graHasZamowienieDAO.create(graHasZamowienia);
			} else {
				// existing record
				graHasZamowienieDAO.merge(graHasZamowienia);
			}
		} catch (Exception e) {
			e.printStackTrace();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Wystąpił błąd podczas zapisu gry do zamówienia", null));
		}
	}

	public String showUserOrders() {
		ordersList = getOrdersList();
		return PAGE_USER_ORDERS;
	}

	public void setOrdersList(List<ZamowienieDTO> ordersList) {
		this.ordersList = ordersList;
	}

	public List<ZamowienieDTO> getOrdersList() {
		List<ZamowienieDTO> list = null;
		FacesContext ctx = FacesContext.getCurrentInstance();

		// pobranie obiektu uzytkownika, ktorego zamowienia pobieram
		HttpServletRequest req = (HttpServletRequest) ctx.getExternalContext().getRequest();
		RemoteClient<Uzytkownik> c = RemoteClient.load(req.getSession());
		Uzytkownik u = c.getDetails();
		Integer userId = u.getIdUzytkownik();
		list = zamowienieDAO.getOrdersListForUser(userId);

		return list;
	}

	public boolean hideElementForEmployee() {
		boolean showElement = true;
		FacesContext ctx = FacesContext.getCurrentInstance();
		HttpServletRequest req = (HttpServletRequest) ctx.getExternalContext().getRequest();
		RemoteClient<Uzytkownik> c = RemoteClient.load(req.getSession());
		
		if (c.isInRole("employee")) {
			showElement = false;
		}
		
		return showElement;
	}
	
	public boolean hideElementForUser() {
		boolean showElement = true;
		FacesContext ctx = FacesContext.getCurrentInstance();
		HttpServletRequest req = (HttpServletRequest) ctx.getExternalContext().getRequest();
		RemoteClient<Uzytkownik> c = RemoteClient.load(req.getSession());
		
		if (c.isInRole("user")) {
			showElement = false;
		}
		
		return showElement;
	}
}