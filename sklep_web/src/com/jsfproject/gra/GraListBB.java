package com.jsfproject.gra;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import org.primefaces.model.LazyDataModel;

import jsf.project.dao.GraDAO;
import jsf.project.entities.Gra;

@Named
@RequestScoped
public class GraListBB {
	private static final String PAGE_GRA_EDIT = "graEdit?faces-redirect=true";
	private static final String PAGE_GRA_BUY = "graBuy?faces-redirect=true";
	private static final String PAGE_STAY_AT_THE_SAME = null;

	private String nazwaGry;

	@Inject
	ExternalContext extcontext;

	@Inject
	Flash flash;

	@EJB
	GraDAO graDAO;
	//private LazyDataModel<Gra> model;

//	@PostConstruct
//	public void init() {
//		model = new LazyDataModel<Gra>() {
//
//			public List<Gra> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
//				model.setRowCount(graDAO.count(filters, myParameter));
//				return graDAO.getLazyList(first, pageSize, sortField, sortOrder, filters, myParameter);
//			}
//		};
//		model.setRowCount(graDAO.count(new HashMap<String, String>()));
//	}

//	public LazyDataModel<Gra> getLazyModel() {
//		return model;
//	}

	public String getNazwaGry() {
		return nazwaGry;
	}

	public void setNazwaGry(String nazwaGry) {
		this.nazwaGry = nazwaGry;
	}

	public List<Gra> getFullList(){
		return graDAO.getFullList();
	}

	public List<Gra> getList() {
		List<Gra> list = null;

		// 1. Prepare search params
		Map<String, Object> searchParams = new HashMap<String, Object>();

		if (nazwaGry != null && nazwaGry.length() > 0) {
			searchParams.put("nazwaGry", nazwaGry);
		}

		// 2. Get list
		list = graDAO.getList(searchParams);

		return list;
	}

	public String newGra() {
		Gra gra = new Gra();

		// 1. Pass object through session
		// HttpSession session = (HttpSession) extcontext.getSession(true);
		// session.setAttribute("gra", gra);

		// 2. Pass object through flash
		flash.put("gra", gra);

		return PAGE_GRA_EDIT;
	}

	public String editGra(Gra gra) {
		// 1. Pass object through session
		// HttpSession session = (HttpSession) extcontext.getSession(true);
		// session.setAttribute("gra", gra);

		// 2. Pass object through flash
		flash.put("gra", gra);

		return PAGE_GRA_EDIT;
	}

	public String deleteGra(Gra gra) {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuń grę", "Gra została usunięta");
		FacesContext.getCurrentInstance().addMessage(PAGE_GRA_BUY, msg);
		graDAO.remove(gra);
		return PAGE_STAY_AT_THE_SAME;
	}

	public String buyGra(Gra gra) {
		// Pass object through flash
		flash.put("gra", gra);

		return PAGE_GRA_BUY;
	}
}
