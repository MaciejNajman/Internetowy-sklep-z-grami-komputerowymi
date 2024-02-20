package jsf.project.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.*;

/**
 * The primary key class for the uzytkownik_rola database table.
 * 
 */
@Embeddable
public class UzytkownikRolaPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private int idUzytkownik;
	private int idRola;

	public UzytkownikRolaPK() {
	}
	
	public UzytkownikRolaPK(int idUzytkownik, int idRola) {
        this.idUzytkownik = idUzytkownik;
        this.idRola = idRola;
    }

	public int getIdUzytkownik() {
		return idUzytkownik;
	}

	public void setIdUzytkownik(int idUzytkownik) {
		this.idUzytkownik = idUzytkownik;
	}

	public int getIdRola() {
		return idRola;
	}

	public void setIdRola(int idRola) {
		this.idRola = idRola;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idRola, idUzytkownik);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UzytkownikRolaPK other = (UzytkownikRolaPK) obj;
		return idRola == other.idRola && idUzytkownik == other.idUzytkownik;
	}
}
