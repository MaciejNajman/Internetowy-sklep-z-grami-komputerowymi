package jsf.project.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the uzytkownik_rola database table.
 * 
 */
@Entity
@Table(name = "uzytkownik_rola")
@NamedQuery(name = "UzytkownikRola.findAll", query = "SELECT u FROM UzytkownikRola u")
public class UzytkownikRola implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private UzytkownikRolaPK compositeKeyId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "kiedy_nadano_role")
	private Date kiedyNadanoRole;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "kiedy_odebrano_role")
	private Date kiedyOdebranoRole;

	// bi-directional many-to-one association to Rola
	@ManyToOne
	@JoinColumn(name = "idRola", nullable = false, insertable = false, updatable = false)
	private Rola rola;

	// bi-directional many-to-one association to Uzytkownik
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "idUzytkownik", referencedColumnName = "idUzytkownik", nullable = false, insertable = false, updatable = false)
	private Uzytkownik uzytkownik;

	public UzytkownikRola() {
	}

	public UzytkownikRolaPK getCompositeKey() {
		return this.compositeKeyId;
	}

	public void setCompositeKey(UzytkownikRolaPK id) {
		this.compositeKeyId = id;
	}

	public Date getKiedyNadanoRole() {
		return this.kiedyNadanoRole;
	}

	public void setKiedyNadanoRole(Date kiedyNadanoRole) {
		this.kiedyNadanoRole = kiedyNadanoRole;
	}

	public Date getKiedyOdebranoRole() {
		return this.kiedyOdebranoRole;
	}

	public void setKiedyOdebranoRole(Date kiedyOdebranoRole) {
		this.kiedyOdebranoRole = kiedyOdebranoRole;
	}

	public Rola getRola() {
		return this.rola;
	}

	public void setRola(Rola rola) {
		this.rola = rola;
	}

	public Uzytkownik getUzytkownik() {
		return this.uzytkownik;
	}

	public void setUzytkownik(Uzytkownik uzytkownik) {
		this.uzytkownik = uzytkownik;
	}

}