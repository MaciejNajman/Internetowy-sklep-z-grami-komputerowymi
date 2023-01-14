package jsf.project.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the faktura_sprzedazy database table.
 * 
 */
@Entity
@Table(name="faktura_sprzedazy")
@NamedQuery(name="FakturaSprzedazy.findAll", query="SELECT f FROM FakturaSprzedazy f")
public class FakturaSprzedazy implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idFaktura_sprzedazy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_sprzedazy")
	private Date dataSprzedazy;

	@Column(name="forma_platnosci")
	private String formaPlatnosci;

	@Column(name="nazwa_banku")
	private String nazwaBanku;

	@Column(name="nr_faktury_sprzedazy")
	private String nrFakturySprzedazy;

	private int wartosc;

	//bi-directional many-to-one association to Zamowienie
	@OneToMany(mappedBy="fakturaSprzedazy")
	private List<Zamowienie> zamowienies;

	public FakturaSprzedazy() {
	}

	public int getIdFaktura_sprzedazy() {
		return this.idFaktura_sprzedazy;
	}

	public void setIdFaktura_sprzedazy(int idFaktura_sprzedazy) {
		this.idFaktura_sprzedazy = idFaktura_sprzedazy;
	}

	public Date getDataSprzedazy() {
		return this.dataSprzedazy;
	}

	public void setDataSprzedazy(Date dataSprzedazy) {
		this.dataSprzedazy = dataSprzedazy;
	}

	public String getFormaPlatnosci() {
		return this.formaPlatnosci;
	}

	public void setFormaPlatnosci(String formaPlatnosci) {
		this.formaPlatnosci = formaPlatnosci;
	}

	public String getNazwaBanku() {
		return this.nazwaBanku;
	}

	public void setNazwaBanku(String nazwaBanku) {
		this.nazwaBanku = nazwaBanku;
	}

	public String getNrFakturySprzedazy() {
		return this.nrFakturySprzedazy;
	}

	public void setNrFakturySprzedazy(String nrFakturySprzedazy) {
		this.nrFakturySprzedazy = nrFakturySprzedazy;
	}

	public int getWartosc() {
		return this.wartosc;
	}

	public void setWartosc(int wartosc) {
		this.wartosc = wartosc;
	}

	public List<Zamowienie> getZamowienies() {
		return this.zamowienies;
	}

	public void setZamowienies(List<Zamowienie> zamowienies) {
		this.zamowienies = zamowienies;
	}

	public Zamowienie addZamowieny(Zamowienie zamowieny) {
		getZamowienies().add(zamowieny);
		zamowieny.setFakturaSprzedazy(this);

		return zamowieny;
	}

	public Zamowienie removeZamowieny(Zamowienie zamowieny) {
		getZamowienies().remove(zamowieny);
		zamowieny.setFakturaSprzedazy(null);

		return zamowieny;
	}

}