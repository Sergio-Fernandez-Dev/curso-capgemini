package com.catalogo.application.resources;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.catalogo.application.resources.ActorResource.Peli;
import com.catalogo.domains.contracts.services.ActorService;
import com.catalogo.domains.entities.Actor;
import com.catalogo.domains.entities.Film;
import com.catalogo.domains.entities.FilmActor;
import com.catalogo.domains.entities.models.ActorDTO;
import com.catalogo.domains.entities.models.ActorShort;
import com.catalogo.exceptions.DuplicateKeyException;
import com.catalogo.exceptions.InvalidDataException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Value;

@WebMvcTest(ActorResource.class)
class ActorResourceTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ActorService srv;

	@Autowired
	ObjectMapper objectMapper;

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Value
	static class ActorShortMock implements ActorShort {
		int id;
		String nombre;
	}

	@Test
	void testGetAllString() throws Exception {
		List<ActorShort> lista = new ArrayList<>(Arrays.asList(new ActorShortMock(1, "Pepito Grillo"),
				new ActorShortMock(2, "Carmelo Coton"), new ActorShortMock(3, "Capitan Tan")));
		when(srv.getByProjection(ActorShort.class)).thenReturn(lista);
		mockMvc.perform(get("/api/actores/v1?modo=short").accept(MediaType.APPLICATION_JSON)).andExpectAll(
				status().isOk(), content().contentType("application/json"), jsonPath("$.size()").value(3));
//		mvc.perform(get("/api/actores/v1").accept(MediaType.APPLICATION_XML))
//			.andExpectAll(
//					status().isOk(), 
//					content().contentType("application/json"),
//					jsonPath("$.size()").value(3)
//					);
	}

	@Test
	void testGetAllPageable() throws Exception {
		List<ActorShort> lista = new ArrayList<>(Arrays.asList(new ActorShortMock(1, "Pepito Grillo"),
				new ActorShortMock(2, "Carmelo Coton"), new ActorShortMock(3, "Capitan Tan")));

		when(srv.getByProjection(PageRequest.of(0, 20), ActorShort.class)).thenReturn(new PageImpl<>(lista));
		mockMvc.perform(get("/api/actores/v1").queryParam("page", "0")).andExpectAll(status().isOk(),
				content().contentType("application/json"), jsonPath("$.content.size()").value(3),
				jsonPath("$.size").value(3));
	}

	@Test
	void testGetOne() throws Exception {
		int id = 1;
		var ele = new Actor(id, "Pepito", "Grillo");
		when(srv.getOne(id)).thenReturn(Optional.of(ele));
		mockMvc.perform(get("/api/actores/v1/{id}", id)).andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(id)).andExpect(jsonPath("$.nombre").value(ele.getFirstName()))
				.andExpect(jsonPath("$.apellidos").value(ele.getLastName())).andDo(print());
	}

	@Test
	void testGetOne404() throws Exception {
		int id = 1;
		var ele = new Actor(id, "Pepito", "Grillo");
		when(srv.getOne(id)).thenReturn(Optional.empty());
		mockMvc.perform(get("/api/actores/v1/{id}", id)).andExpect(status().isNotFound())
				.andExpect(jsonPath("$.title").value("Not Found")).andDo(print());
	}

	@Test
	void testGetPelis() throws Exception {
		int actorId = 1;

		var film1 = mock(Film.class);
		var film2 = mock(Film.class);

		when(film1.getFilmId()).thenReturn(101);
		when(film1.getTitle()).thenReturn("Film One");

		when(film2.getFilmId()).thenReturn(102);
		when(film2.getTitle()).thenReturn("Film Two");

		var filmActor1 = mock(FilmActor.class);
		var filmActor2 = mock(FilmActor.class);

		when(filmActor1.getFilm()).thenReturn(film1);
		when(filmActor2.getFilm()).thenReturn(film2);

		var actor = mock(Actor.class);
		when(actor.getFilmActors()).thenReturn(Arrays.asList(filmActor1, filmActor2));
		when(srv.getOne(actorId)).thenReturn(Optional.of(actor));

		mockMvc.perform(get("/api/actores/v1/{id}/pelis", actorId)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.size()").value(2))
				.andExpect(jsonPath("$[0].id").value(101)).andExpect(jsonPath("$[0].titulo").value("Film One"))
				.andExpect(jsonPath("$[1].id").value(102)).andExpect(jsonPath("$[1].titulo").value("Film Two"))
				.andDo(print());
	}

	@Test
	void testCreate() throws JsonProcessingException, Exception {
		int id = 1;
		var ele = new Actor(id, "Pepito", "Grillo");
		when(srv.add(ele)).thenReturn(ele);
		mockMvc.perform(post("/api/actores/v1").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(ActorDTO.from(ele)))).andExpect(status().isCreated())
				.andExpect(header().string("Location", "http://localhost/api/actores/v1/1")).andDo(print());
	}

//	@Test
//	void testDarPremio() {
//		fail("Not yet implemented");
//	}
//
	@Test
	void testUpdate() throws Exception {
		int id = 1;
		ActorDTO actorDTO = new ActorDTO(id, "Pepito", "Grillo");
		Actor actor = ActorDTO.from(actorDTO);

		when(srv.modify(actor)).thenReturn(actor);

		mockMvc.perform(put("/api/actores/v1/{id}", id).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(actorDTO))).andExpect(status().isNoContent()).andDo(print());

		verify(srv, times(1)).modify(actor);
	}

	@Test
	void testDelete() throws Exception {
		int id = 1;
		doNothing().when(srv).deleteById(anyInt());

		mockMvc.perform(delete("/api/actores/v1/{id}", id).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent()) 
				.andDo(print());
	
		verify(srv, times(1)).deleteById(id);
	}

}
