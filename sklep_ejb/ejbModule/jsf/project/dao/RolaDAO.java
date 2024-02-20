package jsf.project.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import jsf.project.entities.Rola;

@Stateless
public class RolaDAO {

	@PersistenceContext
	EntityManager em;
	
	public void create(Rola rola) {
		em.persist(rola);
	}
	
	public Rola merge(Rola rola) {
		return em.merge(rola);
	}
	
	public void remove(Rola rola) {
		em.remove(em.merge(rola));
	}
	
	public Rola find(Object id) {
		return em.find(Rola.class, id);
	}
	
	public Rola getRoleByName(String roleName) {
		Rola rola = null;

		// 1. Build query string with parameters
		String select = "select r ";
		String from = "from Rola r ";
		String where = "";
		String orderby = "order by r.idRola desc";

		// 2. Search for nazwa_roli
		String nazwa_roli = roleName;
		if (nazwa_roli != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "r.nazwaRoli like :nazwa_roli ";
		}

		// 3. Create query object
		Query query = em.createQuery(select + from + where + orderby);

		// 3. Set configured parameters
		if (nazwa_roli != null) {
			query.setParameter("nazwa_roli", nazwa_roli+"%");
		}

		// 4. Execute query and retrieve Rola object
		try {
			rola = (Rola) query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rola;
	}
}
