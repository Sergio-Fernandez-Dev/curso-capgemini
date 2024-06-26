package com.catalogo.domains.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import com.catalogo.domains.core.entities.EntityBase;


/**
 * The persistent class for the film database table.
 * 
 */
@Entity
@Table(name="film")
@NamedQuery(name="Film.findAll", query="SELECT f FROM Film f")
public class Film extends EntityBase<Film> implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@NotNull
	@Column(name="film_id", unique=true, nullable=false)
	private int filmId;

	@Lob
	private String description;

	@NotNull
	@Column(name="last_update", insertable=false, updatable=false, nullable=false)
	private Timestamp lastUpdate;

	@Min(0)
	private int length;

	@Size(max=1)
	@Column(length=1)
	private String rating;

	@Column(name="release_year")
	private Short releaseYear;

	@NotNull
	@Min(1)
    @Max(127)
	@Column(name="rental_duration", nullable=false)
	private byte rentalDuration;

	@NotNull
	@Column(name="rental_rate", nullable=false, precision=10, scale=2)
	private BigDecimal rentalRate;

	@NotNull
	@Column(name="replacement_cost", nullable=false, precision=10, scale=2)
	private BigDecimal replacementCost;

	@NotNull
	@Size(max = 128)
	@Column(nullable=false, length=128)
	private String title;

	//bi-directional many-to-one association to Language
	@ManyToOne
	@NotNull
	@JoinColumn(name="language_id", nullable=false)
	private Language language;

	//bi-directional many-to-one association to Language
	@ManyToOne
	@JoinColumn(name="original_language_id")
	private Language languageVO;

	//bi-directional many-to-one association to FilmActor
	@OneToMany(mappedBy="film", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<FilmActor> filmActors;

	//bi-directional many-to-one association to FilmCategory
	@OneToMany(mappedBy="film")
	private List<FilmCategory> filmCategories;

	public Film() {
	}

	public int getFilmId() {
		return this.filmId;
	}

	public void setFilmId(int filmId) {
		this.filmId = filmId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public int getLength() {
		return this.length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getRating() {
		return this.rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public Short getReleaseYear() {
		return this.releaseYear;
	}

	public void setReleaseYear(Short releaseYear) {
		this.releaseYear = releaseYear;
	}

	public byte getRentalDuration() {
		return this.rentalDuration;
	}

	public void setRentalDuration(byte rentalDuration) {
		this.rentalDuration = rentalDuration;
	}

	public BigDecimal getRentalRate() {
		return this.rentalRate;
	}

	public void setRentalRate(BigDecimal rentalRate) {
		this.rentalRate = rentalRate;
	}

	public BigDecimal getReplacementCost() {
		return this.replacementCost;
	}

	public void setReplacementCost(BigDecimal replacementCost) {
		this.replacementCost = replacementCost;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Language getLanguage() {
		return this.language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public Language getLanguageVO() {
		return this.languageVO;
	}

	public void setLanguageVO(Language languageVO) {
		this.languageVO = languageVO;
	}
	
	public List<FilmActor> getFilmActors() {
		return this.filmActors;
	}

	public void setFilmActors(List<FilmActor> filmActors) {
		this.filmActors = filmActors;
	}

	public FilmActor addFilmActor(FilmActor filmActor) {
		getFilmActors().add(filmActor);
		filmActor.setFilm(this);

		return filmActor;
	}

	public FilmActor removeFilmActor(FilmActor filmActor) {
		getFilmActors().remove(filmActor);
		filmActor.setFilm(null);

		return filmActor;
	}
	public List<Actor> getActors() {
		return this.filmActors.stream().map(item -> item.getActor()).toList();
	}
	
	public void addActor(Actor item) {
		FilmActor filmActor = new FilmActor(this, item);
		filmActors.add(filmActor);	
	}
	
	public void removeActor(Actor actor) {
		for (FilmActor filmActor : filmActors) {
			if (filmActor.getActor().equals(actor)) {
				filmActors.remove(filmActor);
			}
		}			
	}
}