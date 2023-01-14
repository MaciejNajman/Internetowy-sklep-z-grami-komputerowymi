package jsf.project.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
}
