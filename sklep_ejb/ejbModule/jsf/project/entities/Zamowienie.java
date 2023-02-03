package jsf.project.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.time.LocalDateTime;
import java.util.List;


/**
 * The persistent class for the zamowienie database table.
 * 
 */
@Entity
@NamedQuery(name="Zamowienie.findAll", query="SELECT z FROM Zamowienie z")
public class Zamowienie implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idZamowienie;

	@Column(name="czy_przyjeto_zamowienie")
	private byte czyPrzyjetoZamowienie;

	@Column(name="czy_zamowienie_zrealizowano")
	private byte czyZamowienieZrealizowano;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_realizacji_zamowienia")
	private Date dataRealizacjiZamowienia;

	@Column(name="data_zlozenia_zamowienia")
	private String dataZlozeniaZamowienia; //typ String ponieważ formatuję datę i zwracam String

	private int zaplacono;

	//bi-directional many-to-one association to GraHasZamowienie
	@OneToMany(mappedBy="zamowienie")
	private List<GraHasZamowienie> graHasZamowienies;

	//bi-directional many-to-one association to FakturaSprzedazy
	@ManyToOne
	@JoinColumn(name="Faktura_sprzedazy_idFaktura_sprzedazy")
	private FakturaSprzedazy fakturaSprzedazy;

	//bi-directional many-to-one association to Uzytkownik
	@ManyToOne
	private Uzytkownik uzytkownik;

	public Zamowienie() {
	}

	public Integer getIdZamowienie() {
		return this.idZamowienie;
	}

	public void setIdZamowienie(int idZamowienie) {
		this.idZamowienie = idZamowienie;
	}

	public byte getCzyPrzyjetoZamowienie() {
		return this.czyPrzyjetoZamowienie;
	}

	public void setCzyPrzyjetoZamowienie(byte czyPrzyjetoZamowienie) {
		this.czyPrzyjetoZamowienie = czyPrzyjetoZamowienie;
	}

	public byte getCzyZamowienieZrealizowano() {
		return this.czyZamowienieZrealizowano;
	}

	public void setCzyZamowienieZrealizowano(byte czyZamowienieZrealizowano) {
		this.czyZamowienieZrealizowano = czyZamowienieZrealizowano;
	}

	public Date getDataRealizacjiZamowienia() {
		return this.dataRealizacjiZamowienia;
	}

	public void setDataRealizacjiZamowienia(Date dataRealizacjiZamowienia) {
		this.dataRealizacjiZamowienia = dataRealizacjiZamowienia;
	}

	public String getDataZlozeniaZamowienia() {
		return this.dataZlozeniaZamowienia;
	}

	public void setDataZlozeniaZamowienia(String dataZlozeniaZamowienia) {
		this.dataZlozeniaZamowienia = dataZlozeniaZamowienia;
	}

	public int getZaplacono() {
		return this.zaplacono;
	}

	public void setZaplacono(int zaplacono) {
		this.zaplacono = zaplacono;
	}

	public List<GraHasZamowienie> getGraHasZamowienies() {
		return this.graHasZamowienies;
	}

	public void setGraHasZamowienies(List<GraHasZamowienie> graHasZamowienies) {
		this.graHasZamowienies = graHasZamowienies;
	}

	public GraHasZamowienie addGraHasZamowieny(GraHasZamowienie graHasZamowieny) {
		getGraHasZamowienies().add(graHasZamowieny);
		graHasZamowieny.setZamowienie(this);

		return graHasZamowieny;
	}

	public GraHasZamowienie removeGraHasZamowieny(GraHasZamowienie graHasZamowieny) {
		getGraHasZamowienies().remove(graHasZamowieny);
		graHasZamowieny.setZamowienie(null);

		return graHasZamowieny;
	}

	public FakturaSprzedazy getFakturaSprzedazy() {
		return this.fakturaSprzedazy;
	}

	public void setFakturaSprzedazy(FakturaSprzedazy fakturaSprzedazy) {
		this.fakturaSprzedazy = fakturaSprzedazy;
	}

	public Uzytkownik getUzytkownik() {
		return this.uzytkownik;
	}

	public void setUzytkownik(Uzytkownik uzytkownik) {
		this.uzytkownik = uzytkownik;
	}

}