package jsf.project.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the gra_has_zamowienie database table.
 * 
 */
@Entity
@Table(name="gra_has_zamowienie")
@NamedQuery(name="GraHasZamowienie.findAll", query="SELECT g FROM GraHasZamowienie g")
public class GraHasZamowienie implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private GraHasZamowieniePK id;

	@Column(name="ilosc_sztuk")
	private int iloscSztuk;

	//bi-directional many-to-one association to Gra
	@ManyToOne
	@JoinColumn(name="idGra", insertable=false, updatable=false)
	private Gra gra;

	//bi-directional many-to-one association to Zamowienie
	@ManyToOne
	@JoinColumn(name="idZamowienie", insertable=false, updatable=false)
	private Zamowienie zamowienie;

	public GraHasZamowienie() {
	}

	public GraHasZamowieniePK getId() {
		return this.id;
	}

	public void setId(GraHasZamowieniePK id) {
		this.id = id;
	}

	public int getIloscSztuk() {
		return this.iloscSztuk;
	}

	public void setIloscSztuk(int iloscSztuk) {
		this.iloscSztuk = iloscSztuk;
	}

	public Gra getGra() {
		return this.gra;
	}

	public void setGra(Gra gra) {
		this.gra = gra;
	}

	public Zamowienie getZamowienie() {
		return this.zamowienie;
	}

	public void setZamowienie(Zamowienie zamowienie) {
		this.zamowienie = zamowienie;
	}

}