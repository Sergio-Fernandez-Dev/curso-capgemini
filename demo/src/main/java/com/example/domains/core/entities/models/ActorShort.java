package com.example.domains.core.entities.models;

import org.springframework.beans.factory.annotation.Value;

public interface ActorShort {
	int getActorId();
	@Value("#{target.firstname + ' ' + target.lastName}")
	String getNombre();
}
