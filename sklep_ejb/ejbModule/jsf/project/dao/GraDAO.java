package jsf.project.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	
//	public List<Gra> getLazyList(int first, int pageSize) {
//		List<Gra> list = null;
//
//		Query query = em.createQuery("select g from Gra g");
//
//		try {
//			query.setFirstResult(first);
//			query.setMaxResults(pageSize);
//			list = query.getResultList();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return list;
//	}
	
	@SuppressWarnings("unchecked")
	public List<Gra> getFullList() {
		List<Gra> list = null;

		Query query = em.createQuery("select g from Gra g order by g.nazwaGry");

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Gra> getList(Map<String, Object> searchParams) {
		List<Gra> list = null;

		// 1. Build query string with parameters
		String select = "select g ";
		String from = "from Gra g ";
		String where = "";
		String orderby = "order by g.nazwaGry asc";

		// search for nazwa gry
		String nazwa_gry = (String) searchParams.get("nazwaGry");
		if (nazwa_gry != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "g.nazwaGry like :nazwa_gry ";
		}
		
		String gatunek = (String) searchParams.get("gatunek");
		if (gatunek != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "g.gatunek like :gatunek ";
		}
		
		// ... other parameters ... 

		// 2. Create query object
		Query query = em.createQuery(select + from + where + orderby);

		// 3. Set configured parameters
		if (nazwa_gry != null) {
			query.setParameter("nazwa_gry", "%"+nazwa_gry+"%");
		}
		
		if (gatunek != null) {
			query.setParameter("gatunek", "%"+gatunek+"%");
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
	
    public List<Gra> recommendGames(Integer userId) {
        // Find genres of games the user has purchased
        List<String> userGenres = em.createQuery("SELECT g.gatunek FROM Gra g "
        		+ "JOIN GraHasZamowienie gz ON g.idGra=gz.gra "
        		+ "JOIN Zamowienie z ON gz.zamowienie=z.idZamowienie "
        		+ "JOIN Uzytkownik u ON z.uzytkownik=u.idUzytkownik "
        		+ "WHERE u.idUzytkownik like :user_id "
        		+ "ORDER BY g.nazwaGry", String.class)
                .setParameter("user_id", userId)
                .getResultList();

        // Find games with the same genres but exclude games the user has already purchased
        List<Gra> recommendations = new ArrayList<>();
        for (String genre : userGenres) {
            recommendations.addAll(em.createQuery("SELECT g FROM Gra g "
            		+ "WHERE g.gatunek like :gatunek "
            		+ "AND g.idGra NOT IN "
            		+ "(SELECT g.idGra FROM Gra g "
            		+ "JOIN GraHasZamowienie gz ON g.idGra=gz.gra "
            		+ "JOIN Zamowienie z ON gz.zamowienie=z.idZamowienie "
            		+ "JOIN Uzytkownik u ON z.uzytkownik=u.idUzytkownik "
            		+ "WHERE u.idUzytkownik like :user_id) "
            		+ "ORDER BY g.nazwaGry ", Gra.class)
                    .setParameter("gatunek", genre)
                    .setParameter("user_id", userId)
                    .getResultList());
        }

        // Remove duplicates
        Set<Gra> uniqueRecommendations = new HashSet<>(recommendations);
        return new ArrayList<>(uniqueRecommendations);
    }
}
