package jsf.project.dto;

import java.math.BigDecimal;

public class ZamowienieDTO {
	private String nazwaGry;
	private BigDecimal cena;
	private String gatunek;
	private Integer klasyfikacjaWiekowa;
	private String producentGry;
	private String dataZlozeniaZamowienia;
	private String dataRealizacjiZamowienia;

	public ZamowienieDTO(String nazwaGry, BigDecimal cena, String gatunek, Integer klasyfikacjaWiekowa, String producentGry,
			String dataZlozeniaZamowienia, String dataRealizacjiZamowienia) {
		this.nazwaGry = nazwaGry;
		this.cena = cena;
		this.gatunek = gatunek;
		this.klasyfikacjaWiekowa = klasyfikacjaWiekowa;
		this.producentGry = producentGry;
		this.dataZlozeniaZamowienia = dataZlozeniaZamowienia;
		this.dataRealizacjiZamowienia = dataRealizacjiZamowienia;
	}

	public ZamowienieDTO() {

	}

	public String getNazwaGry() {
		return nazwaGry;
	}

	public void setNazwaGry(String nazwaGry) {
		this.nazwaGry = nazwaGry;
	}

	public BigDecimal getCena() {
		return cena;
	}

	public void setCena(BigDecimal cena) {
		this.cena = cena;
	}

	public String getGatunek() {
		return gatunek;
	}

	public void setGatunek(String gatunek) {
		this.gatunek = gatunek;
	}

	public Integer getKlasyfikacjaWiekowa() {
		return klasyfikacjaWiekowa;
	}

	public void setKlasyfikacjaWiekowa(Integer klasyfikacjaWiekowa) {
		this.klasyfikacjaWiekowa = klasyfikacjaWiekowa;
	}

	public String getProducentGry() {
		return producentGry;
	}

	public void setProducentGry(String producentGry) {
		this.producentGry = producentGry;
	}

	public String getDataZlozeniaZamowienia() {
		return dataZlozeniaZamowienia;
	}

	public void setDataZlozeniaZamowienia(String dataZlozeniaZamowienia) {
		this.dataZlozeniaZamowienia = dataZlozeniaZamowienia;
	}

	public String getDataRealizacjiZamowienia() {
		return dataRealizacjiZamowienia;
	}

	public void setDataRealizacjiZamowienia(String dataRealizacjiZamowienia) {
		this.dataRealizacjiZamowienia = dataRealizacjiZamowienia;
	}
}
