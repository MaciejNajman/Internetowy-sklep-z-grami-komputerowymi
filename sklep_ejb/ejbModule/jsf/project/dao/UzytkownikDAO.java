package jsf.project.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jsf.project.entities.Gra;
import jsf.project.entities.Uzytkownik;

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
		
		//List<Uzytkownik> list = null;
		Uzytkownik u = null;
		
		Query query = em.createQuery("SELECT u FROM uzytkownik u WHERE u.login=:login AND u.haslo=:pass");
		query.setParameter("login", login);
		query.setParameter("password", pass);
		
		try {
			//list = query.getResultList();
			u = (Uzytkownik) query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return u;
	}

	// retrieving roles of a User from DB
	public List<String> getUserRolesFromDatabase(Uzytkownik uzytkownik) {

		ArrayList<String> roles = new ArrayList<String>();
		int roleId;
		Query query = em.createQuery("SELECT OBJECT(idRola) FROM uzytkownik_rola AS ur WHERE ur.idUzytkownik=:idU");
		query.setParameter("idU", uzytkownik.getIdUzytkownik());
		
		try {
			roleId = query.getFirstResult();
			if (roleId==1) {
				roles.add("user");
			}
			if (roleId==2) {
				roles.add("admin");
			}
			if (roleId==3) {
				roles.add("employee");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return roles;
	}
}