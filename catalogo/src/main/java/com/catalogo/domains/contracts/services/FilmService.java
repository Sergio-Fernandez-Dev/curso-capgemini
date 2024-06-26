package com.catalogo.domains.contracts.service;

import java.util.List;

import com.catalogo.domains.core.contracts.services.ProjectionDomainService;
import com.catalogo.domains.entities.Actor;
import com.catalogo.domains.entities.Category;
import com.catalogo.domains.entities.Film;
import com.catalogo.exceptions.InvalidDataException;
import com.catalogo.exceptions.NotFoundException;

public interface FilmService  extends ProjectionDomainService<Film, Integer> {

}
