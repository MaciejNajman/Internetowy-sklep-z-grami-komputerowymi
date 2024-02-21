package jsf.project.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.*;

/**
 * The primary key class for the gra_has_zamowienie database table.
 * 
 */
@Embeddable
public class GraHasZamowieniePK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private int idGra;
	private int idZamowienie;

	public GraHasZamowieniePK() {
	}

	public GraHasZamowieniePK(int idGra, int idZamowienie) {
		this.idZamowienie = idZamowienie;
		this.idGra = idGra;
	}

	public int getIdGra() {
		return idGra;
	}

	public void setIdGra(int idGra) {
		this.idGra = idGra;
	}

	public int getIdZamowienie() {
		return idZamowienie;
	}

	public void setIdZamowienie(int idZamowienie) {
		this.idZamowienie = idZamowienie;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idGra, idZamowienie);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GraHasZamowieniePK other = (GraHasZamowieniePK) obj;
		return idGra == other.idGra && idZamowienie == other.idZamowienie;
	}
}