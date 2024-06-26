package com.catalogo;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.catalogo.domains.contracts.service.FilmService;

@SpringBootApplication
public class CatalogoApplication implements CommandLineRunner{
	
	@Autowired
	FilmService service;

	public static void main(String[] args) {
		SpringApplication.run(CatalogoApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		System.err.println("Aplicación arrancada...");
		service.getAll().forEach(item -> System.out.println(item.getTitle()));
	}

}
