package rppstart.jpa;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;


/**
 * The persistent class for the preduzece database table.
 * 
 */
//svaki objekat koji vrati backend a koje je tipa preduzece nece imati u sebi obelezja hibernate i hendler
@JsonIgnoreProperties({"hibernateLazyInitializer", "hendler"}) //izbacuje json objekat koji se pojavljuje u kolonama

@Entity //jedno preduzece je jedan entitet
@Table(name="preduzece")
@NamedQuery(name="Preduzece.findAll", query="SELECT p FROM Preduzece p")
public class Preduzece implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id // ovo povlaci od constraint primary key
	@SequenceGenerator(name="PREDUZECE_ID_GENERATOR", sequenceName="PREDUZECE_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PREDUZECE_ID_GENERATOR")
	private Integer id;

	private String naziv;

	private String opis;

	private Integer pib;

	private String sediste;

	//bi-directional many-to-one association to Sektor
	@JsonIgnore //sprecava beskonacnu petlju artikla 1 jer referenciraju jedna drugu beskonacno Sektor i preduzece
	@OneToMany(mappedBy="preduzece") //veza kardinalitet
	private List<Sektor> sektors;

	public Preduzece() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNaziv() {
		return this.naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getOpis() {
		return this.opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public Integer getPib() {
		return this.pib;
	}

	public void setPib(Integer pib) {
		this.pib = pib;
	}

	public String getSediste() {
		return this.sediste;
	}

	public void setSediste(String sediste) {
		this.sediste = sediste;
	}

	public List<Sektor> getSektors() {
		return this.sektors;
	}

	public void setSektors(List<Sektor> sektors) {
		this.sektors = sektors;
	}

	public Sektor addSektor(Sektor sektor) {
		getSektors().add(sektor);
		sektor.setPreduzece(this);

		return sektor;
	}

	public Sektor removeSektor(Sektor sektor) {
		getSektors().remove(sektor);
		sektor.setPreduzece(null);

		return sektor;
	}

}