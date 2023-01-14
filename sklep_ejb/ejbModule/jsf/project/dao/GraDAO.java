package jsf.project.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import jsf.project.entities.Gra;

@Stateless
public class GraDAO {

	@PersistenceContext
	EntityManager em;
	
	public void create(Gra gra) {
		em.persist(gra);
	}
	
	public Gra merge(Gra gra) {
		return em.merge(gra);
	}
	
	public void remove(Gra gra) {
		em.remove(em.merge(gra));
	}
	
	public Gra find(Object id) {
		return em.find(Gra.class, id);
	}
}
