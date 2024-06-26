package com.catalogo.domains.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;

import com.catalogo.domains.contracts.repositories.FilmRepository;
import com.catalogo.domains.contracts.services.FilmService;
import com.catalogo.domains.entities.Actor;
import com.catalogo.domains.entities.Film;
import com.catalogo.domains.entities.Language;
import com.catalogo.exceptions.DuplicateKeyException;
import com.catalogo.exceptions.InvalidDataException;

@DataJpaTest
@ComponentScan(basePackages = "com.catalogo")
class FilmServiceImplTest {

	@MockBean
	FilmRepository dao;

	@Autowired
	FilmService srv;
	
	private Film film1;
	private Film film2;
	private Film film3;
	private Actor actor1;
	private Actor actor2;
	private Language language;

	@BeforeEach
	public void setUp() {
		film1 = new Film();
		film2 = new Film();
		film3 = new Film();
		language = new Language();

		film1.setFilmId(1);
		film1.setDescription("A great movie.");
		film1.setLastUpdate(new Timestamp(System.currentTimeMillis()));
		film1.setLength(120);
		film1.setRating("PG");
		film1.setReleaseYear((short) 2023);
		film1.setRentalDuration((byte) 5);
		film1.setRentalRate(new BigDecimal("2.99"));
		film1.setReplacementCost(new BigDecimal("19.99"));
		film1.setTitle("A Movie Title");
		film1.setLanguage(language);
		film1.setFilmActors(new ArrayList<>());
		
		film1.setFilmId(2);
		film1.setDescription("Another great movie.");
		film1.setLastUpdate(new Timestamp(System.currentTimeMillis()));
		film1.setLength(120);
		film1.setRating("PG");
		film1.setReleaseYear((short) 2023);
		film1.setRentalDuration((byte) 5);
		film1.setRentalRate(new BigDecimal("2.99"));
		film1.setReplacementCost(new BigDecimal("19.99"));
		film1.setTitle("A Movie Title");
		film1.setLanguage(language);
		film1.setFilmActors(new ArrayList<>());
		
		film1.setFilmId(3);
		film1.setDescription("Best movie at all.");
		film1.setLastUpdate(new Timestamp(System.currentTimeMillis()));
		film1.setLength(120);
		film1.setRating("PG");
		film1.setReleaseYear((short) 2023);
		film1.setRentalDuration((byte) 5);
		film1.setRentalRate(new BigDecimal("2.99"));
		film1.setReplacementCost(new BigDecimal("19.99"));
		film1.setTitle("A Movie Title");
		film1.setLanguage(language);
		film1.setFilmActors(new ArrayList<>());

		actor1 = new Actor();
		actor1.setActorId(1);
		actor1.setFirstName("John");
		actor1.setLastName("Doe");

		actor2 = new Actor();
		actor2.setActorId(2);
		actor2.setFirstName("Jane");
		actor2.setLastName("Smith");
	}
	

	@Test
	void testGetAll_isNotEmpty() {
		List<Film> lista = new ArrayList<>();
		lista.add(film1);
		lista.add(film2);
		lista.add(film3);

		when(dao.findAll()).thenReturn(lista);
		var result = srv.getAll();
		assertThat(result.size()).isEqualTo(3);
		verify(dao, times(1)).findAll();
	}
	
	
}