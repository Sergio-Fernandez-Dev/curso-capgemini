package com.catalogo.domains.entities.models;

import java.io.Serializable;
import java.math.BigDecimal;
import com.catalogo.domains.entities.Film;

import com.catalogo.domains.entities.Language;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FilmDTO implements Serializable {
	private int filmId;
	private String title;
	private String description;
	private Short releaseYear;
	private Language language;
	private Language languageVO;
	private byte rentalDuration;
	private BigDecimal rentalRate;
	private Integer length;
	private BigDecimal replacementCost;

	public static FilmDTO from(Film source) {
		return new FilmDTO(source.getFilmId(), source.getTitle(), source.getDescription(), source.getReleaseYear(),
				source.getLanguage(), source.getLanguageVO(), source.getRentalDuration(), source.getRentalRate(),
				source.getLength(), source.getReplacementCost());
	}

	public static Film from(FilmDTO source) {
		return new Film(source.getFilmId(), source.getTitle(), source.getDescription(), source.getReleaseYear(),
				source.getLanguage(), source.getLanguageVO(), source.getRentalDuration(), source.getRentalRate(),
				source.getLength(), source.getReplacementCost());
	}

}
