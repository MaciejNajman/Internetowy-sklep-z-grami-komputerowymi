package jsf.project.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
	
	public List<Zamowienie> getFullOrderListForUser(Integer userId) {
		List<Zamowienie> list = null;
		Integer user_id = userId;

		Query query = em.createQuery("SELECT g.nazwa_gry, g.cena, g.gatunek, g.klasyfikacja_wiekowa, g.producent_gry, gz.ilosc_sztuk, z.data_zlozenia_zamowienia, z.data_realizacji_zamowienia FROM `gra` g JOIN gra_has_zamowienie gz ON g.idGra=gz.idGra JOIN zamowienie z ON gz.idZamowienie=z.idZamowienie JOIN uzytkownik u ON z.Uzytkownik_idUzytkownik=u.idUzytkownik WHERE u.idUzytkownik like :user_id");

		try {
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}
}
