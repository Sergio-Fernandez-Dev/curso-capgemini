package com.catalogo.domains.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import com.catalogo.domains.core.entities.EntityBase;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;


/**
 * The persistent class for the film database table.
 * 
 */
@Entity
@Table(name="film")
@NamedQuery(name="Film.findAll", query="SELECT f FROM Film f")
public class Film extends EntityBase<Film> implements Serializable {
	private static final long serialVersionUID = 1L;

	public static enum Rating {
		GENERAL_AUDIENCES("G"), PARENTAL_GUIDANCE_SUGGESTED("PG"), PARENTS_STRONGLY_CAUTIONED("PG-13"), RESTRICTED("R"),
		ADULTS_ONLY("NC-17");

		String value;

		Rating(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public static Rating getEnum(String value) {
			switch (value) {
			case "G":
				return Rating.GENERAL_AUDIENCES;
			case "PG":
				return Rating.PARENTAL_GUIDANCE_SUGGESTED;
			case "PG-13":
				return Rating.PARENTS_STRONGLY_CAUTIONED;
			case "R":
				return Rating.RESTRICTED;
			case "NC-17":
				return Rating.ADULTS_ONLY;
			case "":
				return null;
			default:
				throw new IllegalArgumentException("Unexpected value: " + value);
			}
		}

		public static final String[] VALUES = { "G", "PG", "PG-13", "R", "NC-17" };
	}

	@Converter
	private static class RatingConverter implements AttributeConverter<Rating, String> {
		@Override
		public String convertToDatabaseColumn(Rating rating) {
			return rating == null ? null : rating.getValue();
		}

		@Override
		public Rating convertToEntityAttribute(String value) {
			return value == null ? null : Rating.getEnum(value);
		}
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "film_id", unique = true, nullable = false)
	private int filmId;

	@Lob
	private String description;

	@Column(name = "last_update", insertable = false, updatable = false, nullable = false)
	private Timestamp lastUpdate;

	@Positive
	private Integer length;

	@Convert(converter = RatingConverter.class)
	private Rating rating;

	// @Temporal(TemporalType.DATE)
	// @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy")
	@Min(1901)
	@Max(2155)
	@Column(name = "release_year")
	private Short releaseYear;

	@NotNull
	@Positive
	@Column(name = "rental_duration", nullable = false)
	private byte rentalDuration;

	@NotNull
	@Digits(integer = 2, fraction = 2)
	@DecimalMin(value = "0.0", inclusive = false)
	@Column(name = "rental_rate", nullable = false, precision = 10, scale = 2)
	private BigDecimal rentalRate;

	@NotNull
	@Digits(integer = 3, fraction = 2)
	@DecimalMin(value = "0.0", inclusive = false)
	@Column(name = "replacement_cost", nullable = false, precision = 10, scale = 2)
	private BigDecimal replacementCost;

	@NotBlank
	@Size(max = 128)
	@Column(nullable = false, length = 128)
	private String title;

	// bi-directional many-to-one association to Language
	@ManyToOne
	@JoinColumn(name = "language_id")
	@NotNull
	@JsonManagedReference
	private Language language;

	// bi-directional many-to-one association to Language
	@ManyToOne
	@JoinColumn(name = "original_language_id")
	@JsonManagedReference
	private Language languageVO;

	// bi-directional many-to-one association to FilmActor
	@OneToMany(mappedBy = "film", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonBackReference
	private List<FilmActor> filmActors = new ArrayList<FilmActor>();

	// bi-directional many-to-one association to FilmCategory
	@OneToMany(mappedBy = "film", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonBackReference
	private List<FilmCategory> filmCategories = new ArrayList<FilmCategory>();

	public Film() {
	}

	public int getFilmId() {
		return this.filmId;
	}

	public void setFilmId(int filmId) {
		this.filmId = filmId;
		
		if (filmActors != null && filmActors.size() > 0)
			filmActors.forEach(item -> {
				if (item.getId().getFilmId() != filmId)
					item.getId().setFilmId(filmId);
			});
		if (filmCategories != null && filmCategories.size() > 0)
			filmCategories.forEach(item -> {
				if (item.getId().getFilmId() != filmId)
					item.getId().setFilmId(filmId);
			});
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

	public Rating getRating() {
		return this.rating;
	}

	public void setRating(Rating rating) {
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
	
	public void addActor(Actor actor) {
		FilmActor filmActor = new FilmActor(this, actor);
		filmActors.add(filmActor);	
	}
	
	public void removeActor(Actor actor) {
		filmActors.removeIf(filmActor -> filmActor.getActor().equals(actor));
	}
}