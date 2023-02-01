package jsf.project.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import jsf.project.entities.Zamowienie;

@Stateless
public class ZamowienieDAO {

	@PersistenceContext
	EntityManager em;
	
	public Zamowienie create(Zamowienie zamowienie) {
		em.persist(zamowienie); //zmiany od prof
		em.flush();
		return zamowienie;
	}
	
	public Zamowienie merge(Zamowienie zamowienie) {
		return em.merge(zamowienie);
	}
	
	public void remove(Zamowienie zamowienie) {
		em.remove(em.merge(zamowienie));
	}
	
	public Zamowienie find(Object id) {
		return em.find(Zamowienie.class, id);
	}

}
