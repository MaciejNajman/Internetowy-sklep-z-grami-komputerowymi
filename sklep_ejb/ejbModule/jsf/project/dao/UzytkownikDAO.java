package jsf.project.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jsf.project.entities.Uzytkownik;
import jsf.project.entities.UzytkownikRola;
import jsf.project.entities.Rola;

@Stateless
public class UzytkownikDAO {

	@PersistenceContext
	EntityManager em;

	public void create(Uzytkownik uzytkownik) {
		em.persist(uzytkownik);
	}

	public Uzytkownik merge(Uzytkownik uzytkownik) {
		return em.merge(uzytkownik);
	}

	public void remove(Uzytkownik uzytkownik) {
		em.remove(em.merge(uzytkownik));
	}

	public Uzytkownik find(Object id) {
		return em.find(Uzytkownik.class, id);
	}

	// finding user in DB
	public Uzytkownik getUserFromDatabase(String login, String pass) {

		Uzytkownik u = null;

		Query query = em.createQuery("SELECT u FROM Uzytkownik u WHERE u.login=:login AND u.haslo=:pass");
		query.setParameter("login", login);
		query.setParameter("pass", pass);

		try {
			u = (Uzytkownik) query.getSingleResult();
			if(u != null) {
				u.getUzytkownikRolas().size(); //lepsze pobranie rol
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return u;
	}

	// retrieving roles of a User from DB, tak tez mozna
//	public List<String> getUserRolesFromDatabase(Uzytkownik uzytkownik) {
//
//		ArrayList<String> roles = new ArrayList<String>();
//		Object rola = new Rola();
//		Query query = em.createQuery("SELECT r FROM UzytkownikRola r JOIN r.uzytkownik u WHERE r.uzytkownik.idUzytkownik=:idU");
//		query.setParameter("idU", uzytkownik.getIdUzytkownik());
//
//		try {
//			rola = query.getSingleResult();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		System.out.println(uzytkownik.getIdUzytkownik());
//		System.out.println(rola.getIdRola());
//		
//		if (rola.getIdRola()==1) {
//			roles.add("user");
//		}
//		if (rola.getIdRola()==2) {
//			roles.add("admin");
//		}
//		if (rola.getIdRola()==3) {
//			roles.add("employee");
//		}
		/*
		 * ArrayList<String> roles = new ArrayList<String>();
		 * 
		 * if (uzytkownik.getLogin().equals("user")) { roles.add("user"); } if
		 * (uzytkownik.getLogin().equals("employee")) { roles.add("employee"); } if
		 * (uzytkownik.getLogin().equals("admin")) { roles.add("admin"); }
		 */

}