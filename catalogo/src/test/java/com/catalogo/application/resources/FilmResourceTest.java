package com.catalogo.application.resources;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.catalogo.domains.contracts.services.FilmService;
import com.catalogo.domains.entities.Actor;
import com.catalogo.domains.entities.Category;
import com.catalogo.domains.entities.Film;
import com.catalogo.domains.entities.models.ActorDTO;
import com.catalogo.domains.entities.models.FilmDetailsDTO;
import com.catalogo.domains.entities.models.FilmEditDTO;
import com.catalogo.domains.entities.models.FilmShortDTO;
import com.catalogo.exceptions.BadRequestException;
import com.catalogo.exceptions.NotFoundException;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Value;

@WebMvcTest(FilmResource.class)
class FilmResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FilmService srv;

    @Autowired
    private ObjectMapper objectMapper;

    private Film film;
    private FilmShortDTO filmShortDTO;
    private FilmDetailsDTO filmDetailsDTO;
    private FilmEditDTO filmEditDTO;

    @BeforeEach
    void setUp() {
        film = new Film();
        film.setFilmId(1);
        film.setTitle("Film Title");
        film.setDescription("Description");
        film.setReleaseYear((short) 2022);
        filmShortDTO = FilmShortDTO.from(film);
        filmDetailsDTO = FilmDetailsDTO.from(film);
        filmEditDTO = FilmEditDTO.from(film);
    }

    @AfterEach
    void tearDown() {
        film = null;
        filmShortDTO = null;
        filmDetailsDTO = null;
        filmEditDTO = null;
    }

    @Test
    void testGetAll() throws Exception {
        when(srv.getByProjection(FilmShortDTO.class)).thenReturn(Arrays.asList(filmShortDTO));
        
        mockMvc.perform(get("/api/peliculas/v1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andDo(print());
    }

    @Test
    void testGetAllDetails() throws Exception {
        when(srv.getAll()).thenReturn(Arrays.asList(film));
        
        mockMvc.perform(get("/api/peliculas/v1?mode=details")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].filmId").value(film.getFilmId()))
                .andDo(print());
    }

    @Test
    void testGetOneShort() throws Exception {
    	int id = 1;
        when(srv.getOne(1)).thenReturn(Optional.of(film));
        
        mockMvc.perform(get("/api/peliculas/v1/{id}?mode=short", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.filmId").value(film.getFilmId()))
                .andDo(print());
    }

    @Test
    void testGetOneDetails() throws Exception {
        when(srv.getOne(1)).thenReturn(Optional.of(film));
        
        mockMvc.perform(get("/api/peliculas/v1/1?mode=details")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.filmId").value(film.getFilmId()))
                .andDo(print());
    }

    @Test
    void testGetOneEdit() throws Exception {
        when(srv.getOne(1)).thenReturn(Optional.of(film));
        
        mockMvc.perform(get("/api/peliculas/v1/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.filmId").value(film.getFilmId()))
                .andDo(print());
    }

    @Test
    void testGetFilms() throws Exception {
        Actor actor = new Actor(1, "John", "Doe");
        film.addActor(actor);
        when(srv.getOne(1)).thenReturn(Optional.of(film));
        
        mockMvc.perform(get("/api/peliculas/v1/1/reparto")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").value(actor.getActorId()))
                .andDo(print());
    }

    @Test
    void testGetCategories() throws Exception {
        Category category = new Category(1, "Action");
        film.addCategory(category);
        when(srv.getOne(1)).thenReturn(Optional.of(film));
        
        mockMvc.perform(get("/api/peliculas/v1/1/categorias")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].id").value(category.getCategoryId()))
                .andDo(print());
    }

    @Test
    void testAdd() throws Exception {
        when(srv.add(any(Film.class))).thenReturn(film);
        
        mockMvc.perform(post("/api/peliculas/v1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filmEditDTO)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    void testModify() throws Exception {
        when(srv.modify(any(Film.class))).thenReturn(film);
        
        mockMvc.perform(put("/api/peliculas/v1/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(filmEditDTO)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void testDelete() throws Exception {
        doNothing().when(srv).deleteById(1);
        
        mockMvc.perform(delete("/api/peliculas/v1/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());
        
        verify(srv, times(1)).deleteById(1);
    }
}
