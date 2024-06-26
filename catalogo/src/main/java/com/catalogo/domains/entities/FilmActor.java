package com.catalogo.domains.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonManagedReference;


/**
 * The persistent class for the film_actor database table.
 * 
 */
@Entity
@Table(name="film_actor")
@NamedQuery(name="FilmActor.findAll", query="SELECT f FROM FilmActor f")
public class FilmActor implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FilmActorPK id;
	
	@NotNull
	@Column(name="last_update", nullable=false)
	private Timestamp lastUpdate;

	//bi-directional many-to-one association to Actor
	@ManyToOne
	@NotNull
	@JoinColumn(name="actor_id", nullable=false, insertable=false, updatable=false)
	@JsonManagedReference
	private Actor actor;

	//bi-directional many-to-one association to Film
	@ManyToOne
	@NotNull
	@JoinColumn(name="film_id", nullable=false, insertable=false, updatable=false)
	private Film film;

	public FilmActor() {
	}
	
	public FilmActor(Film film, Actor actor) {
		this.film = film;
		this.actor = actor;
	}

	public FilmActorPK getId() {
		return this.id;
	}

	public void setId(FilmActorPK id) {
		this.id = id;
	}

	public Timestamp getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(Timestamp lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Actor getActor() {
		return this.actor;
	}

	public void setActor(Actor actor) {
		this.actor = actor;
	}

	public Film getFilm() {
		return this.film;
	}

	public void setFilm(Film film) {
		this.film = film;
	}

}