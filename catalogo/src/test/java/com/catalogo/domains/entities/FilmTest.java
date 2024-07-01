package com.catalogo.domains.entities;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.catalogo.domains.entities.Film.Rating;


public class FilmTest {

    private Film film;
    private Actor actor1;
    private Actor actor2;
    private Language language;

    @BeforeEach
    public void setUp() {
        film = new Film();
        language = new Language();
        
        
        film.setFilmId(1);
        film.setDescription("A great movie.");
        film.setLastUpdate(new Timestamp(System.currentTimeMillis()));
        film.setLength(120);
        film.Rat
        film.setReleaseYear((short) 2023);
        film.setRentalDuration((byte) 5);
        film.setRentalRate(new BigDecimal("2.99"));
        film.setReplacementCost(new BigDecimal("19.99"));
        film.setTitle("A Movie Title");
        film.setLanguage(language);
        film.setFilmActors(new ArrayList<>());

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
	@DisplayName("Test if two objects with same ID are not equals")
	void testObectsAreDifferent() {
		var film1 = new Film();
		var film2 = new Film();
		
		film1.setFilmId(1);
		film2.setFilmId(1);
		
		assertFalse(film1.equals(film2));
	}

    @Test
    public void testGetActors() {
        film.addActor(actor1);
        film.addActor(actor2);

        List<Actor> actors = film.getActors();
        assertEquals(2, actors.size());
        assertTrue(actors.contains(actor1));
        assertTrue(actors.contains(actor2));
    }

    @Test
    public void testAddActor() {
        film.addActor(actor1);
        assertEquals(1, film.getActors().size());
        assertTrue(film.getActors().contains(actor1));

        film.addActor(actor2);
        assertEquals(2, film.getActors().size());
        assertTrue(film.getActors().contains(actor2));
    }

    @Test
    public void testRemoveActor() {
        film.addActor(actor1);
        film.addActor(actor2);
        assertEquals(2, film.getActors().size());

        film.removeActor(actor1);
        assertEquals(1, film.getActors().size());
        assertFalse(film.getActors().contains(actor1));
        assertTrue(film.getActors().contains(actor2));

        film.removeActor(actor2);
        assertEquals(0, film.getActors().size());
        assertFalse(film.getActors().contains(actor2));
    }


	


}
