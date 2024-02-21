package jsf.project.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import jsf.project.entities.UzytkownikRola;

@Stateless
public class UzytkownikRolaDAO {
	
	@PersistenceContext
	EntityManager em;
	
	public void create(UzytkownikRola uzytkownikRola) {
		em.persist(uzytkownikRola);
	}
	
	public UzytkownikRola merge(UzytkownikRola uzytkownikRola) {
		return em.merge(uzytkownikRola);
	}
	
	public void remove(UzytkownikRola uzytkownikRola) {
		em.remove(em.merge(uzytkownikRola));
	}
	
	public UzytkownikRola find(Object id) {
		return em.find(UzytkownikRola.class, id);
	}
}
