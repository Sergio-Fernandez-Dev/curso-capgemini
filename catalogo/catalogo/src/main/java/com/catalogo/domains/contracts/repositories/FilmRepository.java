package com.catalogo.domains.contracts.repositories;

import java.util.List;
import java.util.Optional;

import com.catalogo.domains.core.contracts.repositories.ProjectionsAndSpecificationJpaRepository;
import com.catalogo.domains.entities.Actor;
import com.catalogo.domains.entities.Film;
import com.catalogo.exceptions.InvalidDataException;
import com.catalogo.exceptions.NotFoundException;

public interface FilmRepository extends ProjectionsAndSpecificationJpaRepository<Film, Integer> {

}
