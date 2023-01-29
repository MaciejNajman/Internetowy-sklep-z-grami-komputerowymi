package jsf.project.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the gra database table.
 * 
 */
@Entity
@NamedQuery(name="Gra.findAll", query="SELECT g FROM Gra g")
public class Gra implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idGra;

	private BigDecimal cena;

	private String gatunek;

	@Column(name="klasyfikacja_wiekowa")
	private int klasyfikacjaWiekowa;

	@Column(name="nazwa_gry")
	private String nazwaGry;

	@Column(name="producent_gry")
	private String producentGry;

	//bi-directional many-to-one association to GraHasZamowienie
	@OneToMany(mappedBy="gra")
	private List<GraHasZamowienie> graHasZamowienies;

	public Gra() {
	}

	public Integer getIdGra() {
		return this.idGra;
	}

	public void setIdGra(Integer idGra) {
		this.idGra = idGra;
	}

	public BigDecimal getCena() {
		return this.cena;
	}

	public void setCena(BigDecimal cena) {
		this.cena = cena;
	}

	public String getGatunek() {
		return this.gatunek;
	}

	public void setGatunek(String gatunek) {
		this.gatunek = gatunek;
	}

	public int getKlasyfikacjaWiekowa() {
		return this.klasyfikacjaWiekowa;
	}

	public void setKlasyfikacjaWiekowa(int klasyfikacjaWiekowa) {
		this.klasyfikacjaWiekowa = klasyfikacjaWiekowa;
	}

	public String getNazwaGry() {
		return this.nazwaGry;
	}

	public void setNazwaGry(String nazwaGry) {
		this.nazwaGry = nazwaGry;
	}

	public String getProducentGry() {
		return this.producentGry;
	}

	public void setProducentGry(String producentGry) {
		this.producentGry = producentGry;
	}

	public List<GraHasZamowienie> getGraHasZamowienies() {
		return this.graHasZamowienies;
	}

	public void setGraHasZamowienies(List<GraHasZamowienie> graHasZamowienies) {
		this.graHasZamowienies = graHasZamowienies;
	}

	public GraHasZamowienie addGraHasZamowieny(GraHasZamowienie graHasZamowieny) {
		getGraHasZamowienies().add(graHasZamowieny);
		graHasZamowieny.setGra(this);

		return graHasZamowieny;
	}

	public GraHasZamowienie removeGraHasZamowieny(GraHasZamowienie graHasZamowieny) {
		getGraHasZamowienies().remove(graHasZamowieny);
		graHasZamowieny.setGra(null);

		return graHasZamowieny;
	}

}