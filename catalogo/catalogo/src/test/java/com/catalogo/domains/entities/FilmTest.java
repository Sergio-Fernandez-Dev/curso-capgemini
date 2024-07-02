package com.catalogo.domains.entities;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.catalogo.domains.entities.Film.Rating;



class FilmTest {

    @InjectMocks
    private Film film;

    @Mock
    private Language language;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        film = new Film(1, "Test Title", language, (byte) 5, new BigDecimal("4.99"), new BigDecimal("19.99"));
    }

    @Test
    void testAddActor() {
        Actor actor = new Actor(1, "John", "Doe");
        film.addActor(actor);

        List<Actor> actors = film.getActors();
        assertEquals(1, actors.size());
        assertEquals(actor, actors.get(0));
    }

    @Test
    void testRemoveActor() {
        Actor actor = new Actor(1, "John", "Doe");
        film.addActor(actor);
        film.removeActor(actor);

        List<Actor> actors = film.getActors();
        assertTrue(actors.isEmpty());
    }

    @Test
    void testSetActors() {
        List<Actor> actors = new ArrayList<>();
        actors.add(new Actor(1, "John", "Doe"));
        actors.add(new Actor(2, "Jane", "Doe"));

        film.setActors(actors);

        List<Actor> filmActors = film.getActors();
        assertEquals(2, filmActors.size());
        assertTrue(filmActors.containsAll(actors));
    }

    @Test
    void testClearActors() {
        Actor actor = new Actor(1, "John", "Doe");
        film.addActor(actor);
        film.clearActors();

        List<Actor> actors = film.getActors();
        assertTrue(actors.isEmpty());
    }
    
    @Test
    void testAddCategory() {
        Category category = new Category(1, "Action");
        film.addCategory(category);

        List<Category> categories = film.getCategories();
        assertEquals(1, categories.size());
        assertEquals(category, categories.get(0));
    }

    @Test
    void testRemoveCategory() {
        Category category = new Category(1, "Action");
        film.addCategory(category);
        film.removeCategory(category);

        List<Category> categories = film.getCategories();
        assertTrue(categories.isEmpty());
    }

    @Test
    void testSetCategories() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1, "Action"));
        categories.add(new Category(2, "Comedy"));

        film.setCategories(categories);

        List<Category> filmCategories = film.getCategories();
        assertEquals(2, filmCategories.size());
        assertTrue(filmCategories.containsAll(categories));
    }

    @Test
    void testClearCategories() {
        Category category = new Category(1, "Action");
        film.addCategory(category);
        film.clearCategories();

        List<Category> categories = film.getCategories();
        assertTrue(categories.isEmpty());
    }

	


}
