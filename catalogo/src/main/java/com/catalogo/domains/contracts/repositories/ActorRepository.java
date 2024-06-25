package com.catalogo.domains.contracts.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.catalogo.domains.core.contracts.repositories.RepositoryWithProjections;
import com.catalogo.domains.entities.Actor;

public interface ActorRepository extends JpaRepository<Actor, Integer>, JpaSpecificationExecutor<Actor>, RepositoryWithProjections {

}
