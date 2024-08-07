package com.catalogo.application.resources;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.catalogo.domains.contracts.repositories.LanguageRepository;
import com.catalogo.domains.entities.Language;
import com.catalogo.domains.entities.models.FilmShortDTO;
import com.catalogo.exceptions.BadRequestException;
import com.catalogo.exceptions.InvalidDataException;
import com.catalogo.exceptions.NotFoundException;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
@RequestMapping(path = "/idiomas/v1")
public class LanguageResource {
	@Autowired
	private LanguageRepository dao;

	@GetMapping
	@JsonView(Language.Partial.class)
	public List<Language> getAll() {
		return dao.findAll(Sort.by("name"));
	}

	@GetMapping(path = "/{id}")
	@JsonView(Language.Complete.class)
	public Language getOne(@PathVariable int id) throws Exception {
		Optional<Language> rslt = dao.findById(id);
		if (!rslt.isPresent())
			throw new NotFoundException();
		return rslt.get();
	}

	@GetMapping(path = "/{id}/peliculas")
	@Transactional
	public List<FilmShortDTO> getFilms(@PathVariable int id) throws Exception {
		Optional<Language> rslt = dao.findById(id);
		if (!rslt.isPresent())
			throw new NotFoundException();
		return rslt.get().getFilms().stream().map(item -> FilmShortDTO.from(item))
				.collect(Collectors.toList());
	}
	@GetMapping(path = "/{id}/vo")
	@Transactional
	public List<FilmShortDTO> getFilmsVO(@PathVariable int id) throws Exception {
		Optional<Language> rslt = dao.findById(id);
		if (!rslt.isPresent())
			throw new NotFoundException();
		return rslt.get().getFilmsVO().stream().map(item -> FilmShortDTO.from(item))
				.collect(Collectors.toList());
	}

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	@JsonView(Language.Partial.class)
	public ResponseEntity<Object> add(@Valid @RequestBody Language item) throws Exception {
		if (item.isInvalid())
			throw new InvalidDataException(item.getErrorsMessage(), item.getErrorsFields());
		if (dao.findById(item.getLanguageId()).isPresent())
			throw new InvalidDataException("Duplicate key");
		dao.save(item);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(item.getLanguageId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping(path = "/{id}")
	@JsonView(Language.Partial.class)
	public Language modify(@PathVariable int id, @Valid @RequestBody Language item) throws Exception {
		if (item.getLanguageId() != id)
			throw new BadRequestException("No coinciden los ID");
		if (item.isInvalid())
			throw new InvalidDataException(item.getErrorsMessage(), item.getErrorsFields());
		if (!dao.findById(item.getLanguageId()).isPresent())
			throw new NotFoundException();
		dao.save(item);
		return item;
	}

	@DeleteMapping(path = "/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable int id) throws Exception {
		try {
			dao.deleteById(id);
		} catch (Exception e) {
			throw new NotFoundException("Missing item", e);
		}
	}
}
