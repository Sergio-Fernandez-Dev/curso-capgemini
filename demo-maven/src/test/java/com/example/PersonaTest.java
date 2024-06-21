package com.example;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.example.core.test.Smoke;

class PersonaTest {

	@Nested
	@DisplayName("Proceso de instanciacion")
	class Create {
		@Nested
		class OK {
			@Test
			@Smoke	
			void soloNombre() {
				var persona = new Persona(1, "Pepito");
				assertNotNull(persona);
				assertAll("Persona", () -> assertEquals(1, persona.getId(), "id"),
						() -> assertEquals("Pepito", persona.getNombre(), "nombre"),
						() -> assertTrue(persona.getApellidos().isEmpty(), "apellidos"));
			}
		}

		@Nested
		class KO {
			@ParameterizedTest
			@CsvSource(value = {"4,''","3,","5,'    '"})
			void soloNombre(int id, String nombre) {
				assertThrows(Exception.class, () -> new Persona(id, nombre));
			}
		}
		
		@Test
		void ponMayusculasServiceOK() {
			var persona = new Persona(1, "Pepito", "Grillo");
			var dao = mock(PersonaRepository.class);
			when(dao.getOne(anyInt())).thenReturn(Optional.of(persona));
			var srv = new PersonaService(dao);
			
			srv.ponMayusculas(1);
			
			assertEquals("PEPITO", persona.getNombre());
			verify(dao).modify(persona);
		}
		
		@Test
		void ponMayusculasServiceKO() {
			var persona = new Persona(1, "Pepito", "Grillo");
			var dao = mock(PersonaRepository.class);
			when(dao.getOne(anyInt())).thenReturn(Optional.empty());
			var srv = new PersonaService(dao);
			
			assertThrows(IllegalArgumentException.class, () -> srv.ponMayusculas(1));
		}
	}

}
