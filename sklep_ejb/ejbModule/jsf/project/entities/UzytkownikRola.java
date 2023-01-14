package jsf.project.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the uzytkownik_rola database table.
 * 
 */
@Entity
@Table(name="uzytkownik_rola")
@NamedQuery(name="UzytkownikRola.findAll", query="SELECT u FROM UzytkownikRola u")
public class UzytkownikRola implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private UzytkownikRolaPK id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="kiedy_nadano_role")
	private Date kiedyNadanoRole;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="kiedy_odebrano_role")
	private Date kiedyOdebranoRole;

	//bi-directional many-to-one association to Rola
	@ManyToOne
	@JoinColumn(name="idRola")
	private Rola rola;

	//bi-directional many-to-one association to Uzytkownik
	@ManyToOne
	@JoinColumn(name="idUzytkownik")
	private Uzytkownik uzytkownik;

	public UzytkownikRola() {
	}

	public UzytkownikRolaPK getId() {
		return this.id;
	}

	public void setId(UzytkownikRolaPK id) {
		this.id = id;
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