package jsf.project.dao;

import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
	
	public List<Gra> getFullList() {
		List<Gra> list = null;

		Query query = em.createQuery("select g from Gra g");

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	
	public List<Gra> getList(Map<String, Object> searchParams) {
		List<Gra> list = null;

		// 1. Build query string with parameters
		String select = "select g ";
		String from = "from Gra g ";
		String where = "";
		String orderby = "order by g.nazwa_gry asc";

		// search for nazwa gry
		String nazwa_gry = (String) searchParams.get("nazwa_gry");
		if (nazwa_gry != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "g.nazwa_gry like :nazwa_gry ";
		}
		
		// ... other parameters ... 

		// 2. Create query object
		Query query = em.createQuery(select + from + where + orderby);

		// 3. Set configured parameters
		if (nazwa_gry != null) {
			query.setParameter("nazwa_gry", nazwa_gry+"%");
		}

		// ... other parameters ... 

		// 4. Execute query and retrieve list of Gra objects
		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
}
