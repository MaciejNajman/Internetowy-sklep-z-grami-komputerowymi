package jsf.project.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the gra_has_zamowienie database table.
 * 
 */
@Embeddable
public class GraHasZamowieniePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false)
	private int idGra;

	@Column(insertable=false, updatable=false)
	private int idZamowienie;

	public GraHasZamowieniePK() {
	}
	public int getIdGra() {
		return this.idGra;
	}
	public void setIdGra(int idGra) {
		this.idGra = idGra;
	}
	public int getIdZamowienie() {
		return this.idZamowienie;
	}
	public void setIdZamowienie(int idZamowienie) {
		this.idZamowienie = idZamowienie;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof GraHasZamowieniePK)) {
			return false;
		}
		GraHasZamowieniePK castOther = (GraHasZamowieniePK)other;
		return 
			(this.idGra == castOther.idGra)
			&& (this.idZamowienie == castOther.idZamowienie);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idGra;
		hash = hash * prime + this.idZamowienie;
		
		return hash;
	}
}