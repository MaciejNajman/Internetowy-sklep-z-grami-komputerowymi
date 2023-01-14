package jsf.project.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the uzytkownik database table.
 * 
 */
@Entity
@NamedQuery(name="Uzytkownik.findAll", query="SELECT u FROM Uzytkownik u")
public class Uzytkownik implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idUzytkownik;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_zatrudnienia_pracownika")
	private Date dataZatrudnieniaPracownika;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_zwolnienia_pracownika")
	private Date dataZwolnieniaPracownika;

	private String email;

	private String haslo;

	private String imie;

	private String login;

	private String nazwisko;

	@Column(name="nr_tel")
	private String nrTel;

	//bi-directional many-to-one association to UzytkownikRola
	@OneToMany(mappedBy="uzytkownik")
	private List<UzytkownikRola> uzytkownikRolas;

	//bi-directional many-to-one association to Zamowienie
	@OneToMany(mappedBy="uzytkownik")
	private List<Zamowienie> zamowienies;

	public Uzytkownik() {
	}

	public int getIdUzytkownik() {
		return this.idUzytkownik;
	}

	public void setIdUzytkownik(int idUzytkownik) {
		this.idUzytkownik = idUzytkownik;
	}

	public Date getDataZatrudnieniaPracownika() {
		return this.dataZatrudnieniaPracownika;
	}

	public void setDataZatrudnieniaPracownika(Date dataZatrudnieniaPracownika) {
		this.dataZatrudnieniaPracownika = dataZatrudnieniaPracownika;
	}

	public Date getDataZwolnieniaPracownika() {
		return this.dataZwolnieniaPracownika;
	}

	public void setDataZwolnieniaPracownika(Date dataZwolnieniaPracownika) {
		this.dataZwolnieniaPracownika = dataZwolnieniaPracownika;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHaslo() {
		return this.haslo;
	}

	public void setHaslo(String haslo) {
		this.haslo = haslo;
	}

	public String getImie() {
		return this.imie;
	}

	public void setImie(String imie) {
		this.imie = imie;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getNazwisko() {
		return this.nazwisko;
	}

	public void setNazwisko(String nazwisko) {
		this.nazwisko = nazwisko;
	}

	public String getNrTel() {
		return this.nrTel;
	}

	public void setNrTel(String nrTel) {
		this.nrTel = nrTel;
	}

	public List<UzytkownikRola> getUzytkownikRolas() {
		return this.uzytkownikRolas;
	}

	public void setUzytkownikRolas(List<UzytkownikRola> uzytkownikRolas) {
		this.uzytkownikRolas = uzytkownikRolas;
	}

	public UzytkownikRola addUzytkownikRola(UzytkownikRola uzytkownikRola) {
		getUzytkownikRolas().add(uzytkownikRola);
		uzytkownikRola.setUzytkownik(this);

		return uzytkownikRola;
	}

	public UzytkownikRola removeUzytkownikRola(UzytkownikRola uzytkownikRola) {
		getUzytkownikRolas().remove(uzytkownikRola);
		uzytkownikRola.setUzytkownik(null);

		return uzytkownikRola;
	}

	public List<Zamowienie> getZamowienies() {
		return this.zamowienies;
	}

	public void setZamowienies(List<Zamowienie> zamowienies) {
		this.zamowienies = zamowienies;
	}

	public Zamowienie addZamowieny(Zamowienie zamowieny) {
		getZamowienies().add(zamowieny);
		zamowieny.setUzytkownik(this);

		return zamowieny;
	}

	public Zamowienie removeZamowieny(Zamowienie zamowieny) {
		getZamowienies().remove(zamowieny);
		zamowieny.setUzytkownik(null);

		return zamowieny;
	}

}