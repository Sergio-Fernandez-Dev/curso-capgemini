package com.catalogo.domains.contracts.services;

import java.util.List;

import com.catalogo.domains.core.contracts.services.ProjectionDomainService;
import com.catalogo.domains.entities.Actor;
import com.catalogo.domains.entities.Film;
import com.catalogo.exceptions.InvalidDataException;
import com.catalogo.exceptions.NotFoundException;

public interface FilmService  extends ProjectionDomainService<Film, Integer> {
	public Film addActorToFilm(Integer filmId, Actor actor) throws InvalidDataException, NotFoundException;
	public List<Actor> getActorsInFilm(Integer filmId) throws NotFoundException;
	public void deleteActorFromFilm(Integer filmId, Actor actor) throws NotFoundException;
}
