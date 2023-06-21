package jsf.project.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import jsf.project.entities.GraHasZamowienie;

@Stateless
public class GraHasZamowienieDAO {

	@PersistenceContext
	EntityManager em;
	
	public GraHasZamowienie create(GraHasZamowienie graHasZamowienie) {
		em.persist(graHasZamowienie); //zmiany z zamowienieDAO
		em.flush();
		return graHasZamowienie;
	}
	
	public GraHasZamowienie merge(GraHasZamowienie graHasZamowienie) {
		return em.merge(graHasZamowienie);
	}
	
	public void remove(GraHasZamowienie graHasZamowienie) {
		em.remove(em.merge(graHasZamowienie));
	}
	
	public GraHasZamowienie find(Object id) {
		return em.find(GraHasZamowienie.class, id);
	}
}
