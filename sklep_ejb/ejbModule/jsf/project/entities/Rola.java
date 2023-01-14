package jsf.project.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the rola database table.
 * 
 */
@Entity
@NamedQuery(name="Rola.findAll", query="SELECT r FROM Rola r")
public class Rola implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idRola;

	@Column(name="czy_aktywna_w_systemie")
	private byte czyAktywnaWSystemie;

	@Column(name="nazwa_roli")
	private String nazwaRoli;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="od_kiedy_istnieje")
	private Date odKiedyIstnieje;

	//bi-directional many-to-one association to UzytkownikRola
	@OneToMany(mappedBy="rola")
	private List<UzytkownikRola> uzytkownikRolas;

	public Rola() {
	}

	public int getIdRola() {
		return this.idRola;
	}

	public void setIdRola(int idRola) {
		this.idRola = idRola;
	}

	public byte getCzyAktywnaWSystemie() {
		return this.czyAktywnaWSystemie;
	}

	public void setCzyAktywnaWSystemie(byte czyAktywnaWSystemie) {
		this.czyAktywnaWSystemie = czyAktywnaWSystemie;
	}

	public String getNazwaRoli() {
		return this.nazwaRoli;
	}

	public void setNazwaRoli(String nazwaRoli) {
		this.nazwaRoli = nazwaRoli;
	}

	public Date getOdKiedyIstnieje() {
		return this.odKiedyIstnieje;
	}

	public void setOdKiedyIstnieje(Date odKiedyIstnieje) {
		this.odKiedyIstnieje = odKiedyIstnieje;
	}

	public List<UzytkownikRola> getUzytkownikRolas() {
		return this.uzytkownikRolas;
	}

	public void setUzytkownikRolas(List<UzytkownikRola> uzytkownikRolas) {
		this.uzytkownikRolas = uzytkownikRolas;
	}

	public UzytkownikRola addUzytkownikRola(UzytkownikRola uzytkownikRola) {
		getUzytkownikRolas().add(uzytkownikRola);
		uzytkownikRola.setRola(this);

		return uzytkownikRola;
	}

	public UzytkownikRola removeUzytkownikRola(UzytkownikRola uzytkownikRola) {
		getUzytkownikRolas().remove(uzytkownikRola);
		uzytkownikRola.setRola(null);

		return uzytkownikRola;
	}

}