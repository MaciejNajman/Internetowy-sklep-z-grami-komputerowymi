package com.jsfproject.gra;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.simplesecurity.RemoteClient;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import jsf.project.dao.GraDAO;
import jsf.project.entities.Gra;
import jsf.project.entities.Uzytkownik;

@Named
@ViewScoped
public class GraRecommenderBB implements Serializable {
	private static final long serialVersionUID = 1L;
	
    @EJB
    private GraDAO graDAO;
    
    private List<Gra> recommendedGames;

	@PostConstruct
    public void init() {
		FacesContext ctx = FacesContext.getCurrentInstance();

		//get user for whom games are recommended
		HttpServletRequest req = (HttpServletRequest) ctx.getExternalContext().getRequest();
		RemoteClient<Uzytkownik> c = RemoteClient.load(req.getSession());
		Uzytkownik u = c.getDetails();
		
        Integer currentUserId = u.getIdUzytkownik();
        recommendedGames = graDAO.recommendGames(currentUserId);
    }
	
    public List<Gra> getRecommendedGames() {
		return recommendedGames;
	}

	public void setRecommendedGames(List<Gra> recommendedGames) {
		this.recommendedGames = recommendedGames;
	}
}
