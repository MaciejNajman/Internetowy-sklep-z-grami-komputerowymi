package jsf.project.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import jsf.project.entities.FakturaSprzedazy;

@Stateless
public class FakturaSprzedazyDAO {

	@PersistenceContext
	EntityManager em;
	
	public void create(FakturaSprzedazy fakturaSprzedazy) {
		em.persist(fakturaSprzedazy);
	}

	public FakturaSprzedazy merge(FakturaSprzedazy fakturaSprzedazy) {
		return em.merge(fakturaSprzedazy);
	}

	public void remove(FakturaSprzedazy fakturaSprzedazy) {
		em.remove(em.merge(fakturaSprzedazy));
	}

	public FakturaSprzedazy find(Object id) {
		return em.find(FakturaSprzedazy.class, id);
	}
}
