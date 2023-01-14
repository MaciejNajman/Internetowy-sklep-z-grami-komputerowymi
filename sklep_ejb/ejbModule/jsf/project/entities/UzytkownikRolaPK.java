package jsf.project.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the uzytkownik_rola database table.
 * 
 */
@Embeddable
public class UzytkownikRolaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false)
	private int idUzytkownik;

	@Column(insertable=false, updatable=false)
	private int idRola;

	public UzytkownikRolaPK() {
	}
	public int getIdUzytkownik() {
		return this.idUzytkownik;
	}
	public void setIdUzytkownik(int idUzytkownik) {
		this.idUzytkownik = idUzytkownik;
	}
	public int getIdRola() {
		return this.idRola;
	}
	public void setIdRola(int idRola) {
		this.idRola = idRola;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof UzytkownikRolaPK)) {
			return false;
		}
		UzytkownikRolaPK castOther = (UzytkownikRolaPK)other;
		return 
			(this.idUzytkownik == castOther.idUzytkownik)
			&& (this.idRola == castOther.idRola);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idUzytkownik;
		hash = hash * prime + this.idRola;
		
		return hash;
	}
}