package com.catalogo.domains.entities.models;

import java.io.Serializable;

import com.catalogo.domains.entities.Actor;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class ActorDTO implements Serializable {
	
	@Schema(description = "Identificador del actor", accessMode = AccessMode.READ_ONLY)
	private int actorId;
	@Schema(description = "Nombre del actor")
	private String firstName;
	@Schema(description = "Apellidos del actor")
	private String lastName;

	public static ActorDTO from(Actor source) {
		return new ActorDTO(
				source.getActorId(), 
				source.getFirstName(), 
				source.getLastName()
				);
	}
	public static Actor from(ActorDTO source) {
		return new Actor(
				source.getActorId(), 
				source.getFirstName(), 
				source.getLastName()
				);
	}
}
