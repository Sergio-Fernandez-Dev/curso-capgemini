package com.catalogo.application.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.catalogo.domains.contracts.services.ActorService;
import com.catalogo.domains.entities.models.ActorDTO;
import com.catalogo.domains.entities.models.ActorShort;
import com.catalogo.exceptions.BadRequestException;
import com.catalogo.exceptions.DuplicateKeyException;
import com.catalogo.exceptions.InvalidDataException;
import com.catalogo.exceptions.NotFoundException;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@Tag(name = "actors-service", description = "Mantenimiento de actores")
@RequestMapping("/api/actores/v1")
public class ActorResource {
	@Autowired
	private ActorService srv;

	@Operation(summary = "Listado de actores", description = "Recupera la lista de actores en formato corto o detallado.", 
			parameters = {
					@Parameter(
							in = ParameterIn.QUERY, 
							name = "mode", 
							required = false, 
							description = "Formato de los actores", 
							schema = @Schema(
										type = "string", 
										allowableValues = {"long", "short" }, 
										defaultValue = "long"
			))}, 
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "OK", 
							content = @Content(
											mediaType = "application/json", 
											schema = @Schema(
														anyOf = { ActorShort.class,ActorDTO.class }
			)))})
	@GetMapping
	public List<?> getAll(@RequestParam(required = false, defaultValue = "long") String mode) {
		if ("short".equals(mode))
			return srv.getByProjection(ActorShort.class);
		return srv.getByProjection(ActorDTO.class);
	}
	
	@Hidden
	@GetMapping(params = "page")
	public Page<ActorShort> getAll(Pageable page) {
		return srv.getByProjection(page, ActorShort.class);
	}
	
	record Peli(
			@Schema(description = "Identificador de la película")
			int id, 
			@Schema(description = "título de la película")
			String titulo
			) {}
	
	@Operation(summary = "Listado de películas en las que aparece el actor", description = "Recupera la lista de películas en las que aparece el actor.")
	@GetMapping(path = "/{id}/pelis")
	@Transactional
	public List<Peli> getPelis(@PathVariable int id) throws NotFoundException {
		var item = srv.getOne(id);
		if (item.isEmpty())
			throw new NotFoundException();
		return item.get()
				.getFilmActors()
				.stream()
				.map(o -> new Peli(o.getFilm().getFilmId(), o.getFilm().getTitle()))
				.toList();
	}
	
	@Operation(summary = "Jubila a un actor", description = "Cambia el estado de un actor a 'jubilado'")
	@ApiResponse(responseCode = "202", description = "El actor ha sido jubilado correctamente")
	@ApiResponse(responseCode = "404", description = "Actor no encontrado")
	@DeleteMapping(path = "/{id}/jubilacion")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void jubilar(@PathVariable int id) throws NotFoundException {
		var item = srv.getOne(id);
		if (item.isEmpty())
			throw new NotFoundException();
		item.get().jubilate();
	}
	
	@Operation(summary = "recuperar un actor")
	@ApiResponse(responseCode = "200", description = "Actor encontrado")
	@ApiResponse(responseCode = "404", description = "Actor no encontrado")
	@GetMapping(path = "/{id}")
	public ActorDTO getOne(@PathVariable int id) throws NotFoundException {
		var item = srv.getOne(id);
		if (item.isEmpty())
			throw new NotFoundException();
		return ActorDTO.from(item.get());
	}
	
	@Operation(summary = "Añadir un nuevo actor")
	@ApiResponse(responseCode = "201", description = "Actor añadido")
	@ApiResponse(responseCode = "404", description = "Actor no encontrado")
	@PostMapping
	public ResponseEntity<Object> create(@Valid @RequestBody ActorDTO item) throws BadRequestException, InvalidDataException, DuplicateKeyException {
		var newItem = srv.add(ActorDTO.from(item));
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(newItem.getActorId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@Operation(summary = "Modificar un actor existente", description = "Los identificadores deben coincidir")
	@ApiResponse(responseCode = "200", description = "Actor encontrado")
	@ApiResponse(responseCode = "404", description = "Actor no encontrado")
	@ApiResponse(responseCode = "400", description = "Los datos no son correctos")
	@PutMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@PathVariable int id, @Valid @RequestBody ActorDTO item) throws BadRequestException, NotFoundException, InvalidDataException {
		if (id != item.getActorId())
			throw new BadRequestException("No coinciden los identificadores");
		srv.modify(ActorDTO.from(item));
	}
	
	@Operation(summary = "Borrar un actor existente")
	@ApiResponse(responseCode = "204", description = "Actor borrado")
	@DeleteMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable int id) {
		srv.deleteById(id);
	}
}
