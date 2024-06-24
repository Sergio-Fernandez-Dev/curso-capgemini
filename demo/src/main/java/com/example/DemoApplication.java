package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;

import com.example.domains.contracts.repositories.ActorRepository;
import com.example.domains.entities.Actor;
import com.example.ioc.Entorno;
import com.example.ioc.Rango;
import com.example.ioc.Saluda;

import jakarta.transaction.Transactional;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	@Autowired
	ActorRepository dao;

//	@Autowired
////	@Qualifier("es")
//	Saluda saluda;
//	@Autowired
////	@Qualifier("en")
//	Saluda saluda2;
//	@Autowired
//	Entorno entorno;
//	@Autowired
//	private Rango rango;

//	@Autowired(required = false)
//	SaludaEnImpl kk;
	
	@Override
	@Transactional
	public void run(String... args) throws Exception {
		System.err.println("Aplicación arrancada...");
		
//		dao.findAll().forEach(System.out::println);
//		if (item.isEmpty()) {
//			System.err.println("No encontrado");
//		} else {
//			System.out.println(item.get());
//		}		
//		var actor = new Actor(0, "Pepito", "Grillo");
//		dao.save(actor);
//		var item = dao.findById(1);
//		if (item.isEmpty()) {
//			System.err.println("No encontrado");
//		} else {
//			var actor = item.get();
//			actor.setFirstName(actor.getFirstName().toUpperCase());
//			dao.save(actor);
//		}
//		dao.deleteById(201);
//		dao.findAll().forEach(System.out::println);
//		dao.findTop5ByLastNameStartingWithOrderByFirstNameDesc("P").forEach(System.out::println);
//		dao.findTop5ByLastNameStartingWith("P",Sort.by("LastName").ascending()).forEach(System.out::println);
//		dao.findByActorIdGreaterThanEqual(200).forEach(System.out::println);
//		dao.findByJPQL(200).forEach(System.out::println);
//		dao.findBySQL(200).forEach(System.out::println);
//		dao.findAll((root, query, builder) -> builder.greaterThanOrEqualTo(root.get("actorId"), 200)).forEach(System.out::println);
//		dao.findAll((root, query, builder) -> builder.lessThan(root.get("actorId"), 10)).forEach(System.out::println);
//		var item = dao.findById(1);
//		if (item.isEmpty()) {
//			System.err.println("No encontrado");
//		} else {
//			var actor = item.get();
//			System.out.println(actor);
//			actor.getFilmActors().forEach(f -> System.out.println(f.getFilm().getTitle()));
//		}
		var actor = new Actor(0, "   ", "12345678Z");
		if (actor.isValid()) {
			System.out.println(actor);
		} else {
			actor.getErrors().forEach(System.out::println);
		}
	}

}
