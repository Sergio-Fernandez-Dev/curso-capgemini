package com.catalogo.domains.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.catalogo.domains.contracts.repositories.FilmRepository;
import com.catalogo.domains.contracts.services.FilmService;
import com.catalogo.domains.entities.Actor;
import com.catalogo.domains.entities.Category;
import com.catalogo.domains.entities.Film;
import com.catalogo.domains.entities.Language;
import com.catalogo.domains.services.FilmServiceImpl;
import com.catalogo.exceptions.DuplicateKeyException;
import com.catalogo.exceptions.InvalidDataException;
import com.catalogo.exceptions.NotFoundException;


@DataJpaTest
@ComponentScan(basePackages = "com.catalogo")
class FilmServiceImplTest {

    @Mock
    private FilmRepository filmRepository;

    @InjectMocks
    private FilmServiceImpl filmService;
    private Film film;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        film = mock(Film.class);
        when(film.getFilmId()).thenReturn(1);
        when(film.getTitle()).thenReturn("Valid Title");
        when(film.getRentalRate()).thenReturn(BigDecimal.valueOf(4.99));
        when(film.getRentalDuration()).thenReturn((byte) 5);
        when(film.getReplacementCost()).thenReturn(BigDecimal.valueOf(19.99));
        when(film.getLanguage()).thenReturn(new Language(1, "English"));
        when(film.isInvalid()).thenReturn(false);
    }

    @Test
    void testGetByProjection() {
        List<Film> films = new ArrayList<>();
        when(filmRepository.findAllBy(any(Class.class))).thenReturn(films);

        List<Object> result = filmService.getByProjection(Object.class);

        assertEquals(films, result);
    }

    @Test
    void testGetByProjectionWithSort() {
        List<Film> films = new ArrayList<>();
        when(filmRepository.findAllBy(any(Sort.class), any(Class.class))).thenReturn(films);

        List<Object> result = filmService.getByProjection(Sort.by("title"), Object.class);

        assertEquals(films, result);
    }

    @Test
    void testGetByProjectionWithPageable() {
        Page<Film> films = new PageImpl<>(new ArrayList<>());
        when(filmRepository.findAllBy(any(Pageable.class), any(Class.class))).thenReturn(films);

        Page<Object> result = filmService.getByProjection(Pageable.unpaged(), Object.class);

        assertEquals(films, result);
    }

    @Test
    void testGetAllWithSort() {
        List<Film> films = new ArrayList<>();
        when(filmRepository.findAll(any(Sort.class))).thenReturn(films);

        List<Film> result = filmService.getAll(Sort.by("title"));

        assertEquals(films, result);
    }

    @Test
    void testGetAllWithPageable() {
        Page<Film> films = new PageImpl<>(new ArrayList<>());
        when(filmRepository.findAll(any(Pageable.class))).thenReturn(films);

        Page<Film> result = filmService.getAll(Pageable.unpaged());

        assertEquals(films, result);
    }

    @Test
    void testGetAll() {
        List<Film> films = new ArrayList<>();
        when(filmRepository.findAll()).thenReturn(films);

        List<Film> result = filmService.getAll();

        assertEquals(films, result);
    }

    @Test
    void testGetOne() {
        Film film = new Film();
        when(filmRepository.findById(anyInt())).thenReturn(Optional.of(film));

        Optional<Film> result = filmService.getOne(1);

        assertTrue(result.isPresent());
        assertEquals(film, result.get());
    }

    @Test
    void testAdd() throws DuplicateKeyException, InvalidDataException {
        when(filmRepository.save(any(Film.class))).thenReturn(film);

        Film result = filmService.add(film);

        assertEquals(film, result);
    }

    @Test
    void testModify() throws NotFoundException, InvalidDataException { 
        when(filmRepository.findById(anyInt())).thenReturn(Optional.of(film));
        when(filmRepository.save(any(Film.class))).thenReturn(film);

        Film result = filmService.modify(film);

        assertEquals(film, result);
    }

    @Test
    void testDelete() throws InvalidDataException {
        doNothing().when(filmRepository).deleteById(anyInt());

        filmService.delete(film);

        verify(filmRepository, times(1)).deleteById(anyInt());
    }

    @Test
    void testDeleteById() {
        doNothing().when(filmRepository).deleteById(anyInt());

        filmService.deleteById(1);

        verify(filmRepository, times(1)).deleteById(anyInt());
    }

    @Test
    void testAddActorToFilm() throws InvalidDataException, NotFoundException {
    	var actor = new Actor(1,"Silvester","Stalone");
        when(filmRepository.findById(anyInt())).thenReturn(Optional.of(film));
        when(filmRepository.save(any(Film.class))).thenReturn(film);

        Film result = filmService.addActorToFilm(1, actor);

        assertEquals(film, result);
    }

    @Test
    void testGetActorsInFilm() throws NotFoundException {
        Film film = new Film();
        when(filmRepository.findById(anyInt())).thenReturn(Optional.of(film));

        List<Actor> result = filmService.getActorsInFilm(1);

        assertNotNull(result);
    }

    @Test
    void testDeleteActorFromFilm() throws NotFoundException {
        Film film = new Film();
        when(filmRepository.findById(anyInt())).thenReturn(Optional.of(film));

        filmService.deleteActorFromFilm(1, new Actor());

        verify(filmRepository, times(1)).findById(anyInt());
    }

    @Test
    void testAddCategory() throws NotFoundException, InvalidDataException {
    	var category = new Category();
    	category.setName("Aventuras");
        when(filmRepository.findById(anyInt())).thenReturn(Optional.of(film));
        when(filmRepository.save(any(Film.class))).thenReturn(film);

        Film result = filmService.addCategory(1, category);

        assertEquals(film, result);
    }

    @Test
    void testGetCategory() throws NotFoundException {

        when(filmRepository.findById(anyInt())).thenReturn(Optional.of(film));

        List<Category> result = filmService.getCategory(1);

        assertNotNull(result);
    }

    @Test
    void testRemoveCategory() throws NotFoundException {

        when(filmRepository.findById(anyInt())).thenReturn(Optional.of(film));

        filmService.removeCategory(1, new Category());

        verify(filmRepository, times(1)).findById(anyInt());
    }
}