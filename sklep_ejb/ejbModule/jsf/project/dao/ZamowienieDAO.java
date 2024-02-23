package jsf.project.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import jsf.project.dto.ZamowienieDTO;
import jsf.project.entities.Zamowienie;

@Stateless
public class ZamowienieDAO {

	@PersistenceContext
	EntityManager em;

	public Zamowienie create(Zamowienie zamowienie) {
		em.persist(zamowienie); // zmiany od prof
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

	public Integer getIdOfNewlyCreatedZamowienieFromDatabase() {
		int userId = 0;
		int limit = 1;

		Query query = em.createQuery("SELECT idZamowienie FROM Zamowienie ORDER BY idZamowienie DESC")
				.setMaxResults(limit);

		try {
			userId = (Integer) query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return userId;
	}
	
	public List<ZamowienieDTO> getOrdersListForUser(Integer userId) {
		List<ZamowienieDTO> list = null;
		
		String select = "SELECT new jsf.project.dto.ZamowienieDTO(g.nazwaGry, g.cena, g.gatunek, g.klasyfikacjaWiekowa, g.producentGry, z.dataZlozeniaZamowienia, z.dataRealizacjiZamowienia) ";
		String from = "FROM Gra g JOIN GraHasZamowienie gz ON g.idGra=gz.gra JOIN Zamowienie z ON gz.zamowienie=z.idZamowienie JOIN Uzytkownik u ON z.uzytkownik=u.idUzytkownik ";
		String where = "";
		String orderby = "ORDER BY z.dataZlozeniaZamowienia DESC";
		Integer user_id = userId;
		
		if (user_id != null) {
			if (where.isEmpty()) {
				where = "where ";
			} else {
				where += "and ";
			}
			where += "u.idUzytkownik like :user_id ";
		}

		//Query query = em.createQuery("SELECT g.nazwaGry, g.cena, g.gatunek, g.klasyfikacjaWiekowa, g.producentGry, gz.iloscSztuk, z.dataZlozeniaZamowienia, z.dataRealizacjiZamowienia FROM Gra g JOIN GraHasZamowienie gz ON g.idGra=gz.gra JOIN Zamowienie z ON gz.zamowienie=z.idZamowienie JOIN Uzytkownik u ON z.uzytkownik=u.idUzytkownik WHERE u.idUzytkownik like :user_id");
		//Query query = em.createQuery(select + from + where + orderby);
		TypedQuery<ZamowienieDTO> query = em.createQuery(select + from + where + orderby, ZamowienieDTO.class);
		
		if (user_id != null) {
			query.setParameter("user_id", user_id);
		}

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
}
