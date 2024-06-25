package com.catalogo.domains.contracts.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.catalogo.domains.core.contracts.repositories.ProjectionsAndSpecificationJpaRepository;
import com.catalogo.domains.entities.Film;

public interface FilmRepository extends ProjectionsAndSpecificationJpaRepository<Film, Integer> {

}
