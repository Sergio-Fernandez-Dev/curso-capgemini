package com.catalogo.application.resources;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.catalogo.application.proxies.MeGustaProxy;
import com.catalogo.domains.contracts.services.FilmService;
import com.catalogo.domains.entities.Category;
import com.catalogo.domains.entities.Film;
import com.catalogo.domains.entities.models.ActorDTO;
import com.catalogo.domains.entities.models.ActorShort;
import com.catalogo.domains.entities.models.FilmDetailsDTO;
import com.catalogo.domains.entities.models.FilmEditDTO;
import com.catalogo.domains.entities.models.FilmShortDTO;
import com.catalogo.exceptions.BadRequestException;
import com.catalogo.exceptions.NotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "api/peliculas/v1")
public class FilmResource {
	@Autowired
	private FilmService srv;
	
	@GetMapping
	public List<?> getAll() {
		return srv.getByProjection(FilmShortDTO.class);
	}

	@GetMapping(params = "page")
	public Page<FilmShortDTO> getAll(Pageable pageable,
			@RequestParam(defaultValue = "short") String mode) {
		return srv.getByProjection(pageable, FilmShortDTO.class);
	}

	
	@GetMapping(params = { "page", "mode=details" })
	@Transactional
	public Page<FilmDetailsDTO> getAllDetailsPage(Pageable pageable,
			@RequestParam(defaultValue = "short") String mode) {
		var content = srv.getAll(pageable);
		return new PageImpl<>(content.getContent().stream().map(item -> FilmDetailsDTO.from(item)).toList(), pageable,
				content.getTotalElements());
	}

	@GetMapping(params = "mode=details")
	public List<FilmDetailsDTO> getAllDetails(
			@RequestParam(defaultValue = "short") String mode) {
		return srv.getAll().stream().map(item -> FilmDetailsDTO.from(item)).toList();
	}

	@GetMapping(path = "/{id}", params = "mode=short")
	public FilmShortDTO getOneShort(
			@PathVariable int id,
			@RequestParam(required = false, defaultValue = "edit") String mode)
			throws Exception {
		Optional<Film> rslt = srv.getOne(id);
		if (rslt.isEmpty())
			throw new NotFoundException();
		return FilmShortDTO.from(rslt.get());
	}

	@GetMapping(path = "/{id}", params = "mode=details")
	public FilmDetailsDTO getOneDetails(
			@PathVariable int id,
			@RequestParam(required = false, defaultValue = "edit") String mode)
			throws Exception {
		Optional<Film> rslt = srv.getOne(id);
		if (rslt.isEmpty())
			throw new NotFoundException();
		return FilmDetailsDTO.from(rslt.get());
	}

	@GetMapping(path = "/{id}")
	
	public FilmEditDTO getOneEdit(
			@PathVariable int id,
			@RequestParam(required = false, defaultValue = "edit") String mode)
			throws Exception {
		Optional<Film> rslt = srv.getOne(id);
		if (rslt.isEmpty())
			throw new NotFoundException();
		return FilmEditDTO.from(rslt.get());
	}


	@GetMapping(path = "/{id}/reparto")
	@Transactional
	public List<ActorDTO> getFilms(@PathVariable int id)
			throws Exception {
		Optional<Film> rslt = srv.getOne(id);
		if (rslt.isEmpty())
			throw new NotFoundException();
		return rslt.get().getActors().stream().map(item -> ActorDTO.from(item)).toList();
	}

	@GetMapping(path = "/{id}/categorias")
	@Transactional
	public List<Category> getCategories(
			@PathVariable int id)
			throws Exception {
		Optional<Film> rslt = srv.getOne(id);
		if (rslt.isEmpty())
			throw new NotFoundException();
		return rslt.get().getCategories();
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	@Transactional
	public ResponseEntity<Object> add(@RequestBody FilmEditDTO item) throws Exception {
		Film newItem = srv.add(FilmEditDTO.from(item));
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newItem.getFilmId())
				.toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping(path = "/{id}")
	public FilmEditDTO modify(
		@PathVariable int id,
			@Valid @RequestBody FilmEditDTO item) throws Exception {
		if (item.getFilmId() != id)
			throw new BadRequestException("No coinciden los identificadores");
		return FilmEditDTO.from(srv.modify(FilmEditDTO.from(item)));
	}


	@DeleteMapping(path = "/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void delete( @PathVariable int id)
			throws Exception {
		srv.deleteById(id);
	}
	

	@Autowired
	MeGustaProxy proxy;
	
	@Operation(summary = "Enviar un me gusta")
	@ApiResponse(responseCode = "200", description = "Like enviado")
	@SecurityRequirement(name = "bearerAuth")
	@PostMapping(path = "{id}/like")
	public String like(@Parameter(description = "Identificador de la pelicula", required = true) @PathVariable int id,
			@Parameter(hidden = true) @RequestHeader(required = false) String authorization) throws Exception {
		if (authorization == null)
			return proxy.sendLike(id);
		return proxy.sendLike(id, authorization);
	}
	

}
