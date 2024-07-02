package com.catalogo.domains.services;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.catalogo.domains.contracts.repositories.FilmRepository;
import com.catalogo.domains.contracts.services.FilmService;
import com.catalogo.domains.entities.Actor;
import com.catalogo.domains.entities.Category;
import com.catalogo.domains.entities.Language;
import com.catalogo.domains.entities.Film;
import com.catalogo.exceptions.DuplicateKeyException;
import com.catalogo.exceptions.InvalidDataException;
import com.catalogo.exceptions.NotFoundException;

import jakarta.transaction.Transactional;
import lombok.NonNull;

@Service
public class FilmServiceImpl implements FilmService {
	private FilmRepository dao;

	public FilmServiceImpl(FilmRepository dao) {
		this.dao = dao;
	}

	@Override
	public <T> List<T> getByProjection(@NonNull Class<T> type) {
		return dao.findAllBy(type);
	}

	@Override
	public <T> List<T> getByProjection(@NonNull Sort sort, @NonNull Class<T> type) {
		return dao.findAllBy(sort, type);
	}

	@Override
	public <T> Page<T> getByProjection(@NonNull Pageable pageable, @NonNull Class<T> type) {
		return dao.findAllBy(pageable, type);
	}

	@Override
	public List<Film> getAll(@NonNull Sort sort) {
		return dao.findAll(sort);
	}

	@Override
	public Page<Film> getAll(@NonNull Pageable pageable) {
		return dao.findAll(pageable);
	}

	@Override
	public List<Film> getAll() {
		return dao.findAll();
	}

	@Override
	public Optional<Film> getOne(Integer id) {
		return dao.findById(id);
	}

	@Override
	@Transactional
	public Film add(Film item) throws DuplicateKeyException, InvalidDataException {
		if(item == null)
			throw new InvalidDataException("No puede ser nulo");
		if(item.isInvalid())
			throw new InvalidDataException(item.getErrorsMessage(), item.getErrorsFields());
		if(dao.existsById(item.getFilmId()))
			throw new DuplicateKeyException(item.getErrorsMessage());
		return dao.save(item);
	}

	@Override
	@Transactional
	public Film modify(Film item) throws NotFoundException, InvalidDataException {
		if(item == null)
			throw new InvalidDataException("No puede ser nulo");
		if(item.isInvalid())
			throw new InvalidDataException(item.getErrorsMessage(), item.getErrorsFields());
		var leido = dao.findById(item.getFilmId()).orElseThrow(() -> new NotFoundException());
		return dao.save(item.merge(leido));
	}

	@Override
	public void delete(Film item) throws InvalidDataException {
		if(item == null)
			throw new InvalidDataException("No puede ser nulo");
		deleteById(item.getFilmId());
	}

	@Override
	public void deleteById(Integer id) {
		dao.deleteById(id);
	}

	@Override
	public Film addActorToFilm(Integer filmId, Actor actor) throws InvalidDataException, NotFoundException {
		Optional<Film> filmOptional = dao.findById(filmId);
		if (filmOptional.isEmpty()) {
			throw new NotFoundException("Film not found");
		}
		Film film = filmOptional.get();

		if (actor == null)
			throw new InvalidDataException("No puede ser nulo");
		if (actor.isInvalid())
			throw new InvalidDataException(actor.getErrorsMessage(), actor.getErrorsFields());

		film.addActor(actor);
		dao.save(film);
		
		return film;
	}

	@Override
	public List<Actor> getActorsInFilm(Integer filmId) throws NotFoundException {
		Optional<Film> filmOptional = dao.findById(filmId);
		if (filmOptional.isEmpty()) {
			throw new NotFoundException("Film not found");
		}
		return filmOptional.get().getActors();
	}

	@Override
	public void deleteActorFromFilm(Integer filmId, Actor actor) throws NotFoundException {
		Optional<Film> filmOptional = dao.findById(filmId);
		if (filmOptional.isEmpty()) {
			throw new NotFoundException("Film not found");
		}
		
		filmOptional.get().removeActor(actor);
	}
	
	
	public Film addCategory(int filmId, Category category) throws NotFoundException, InvalidDataException {
		Optional<Film> filmOptional = dao.findById(filmId);
		if (filmOptional.isEmpty()) {
			throw new NotFoundException("Film not found");
		}
		Film film = filmOptional.get();

		if (category == null)
			throw new InvalidDataException("No puede ser nulo");
		if (category.isInvalid())
			throw new InvalidDataException(category.getErrorsMessage(), category.getErrorsFields());

		film.addCategory(category);
		dao.save(film);
		
		return film;
    }
	
	public List<Category> getCategory(Integer filmId) throws NotFoundException {
		Optional<Film> filmOptional = dao.findById(filmId);
		if (filmOptional.isEmpty()) {
			throw new NotFoundException("Film not found");
		}
		return filmOptional.get().getCategories();
	}

	
    public void removeCategory(int filmId, Category category) throws NotFoundException {
		Optional<Film> filmOptional = dao.findById(filmId);
		if (filmOptional.isEmpty()) {
			throw new NotFoundException("Film not found");
		}
		
		filmOptional.get().removeCategory(category);
    }
}
