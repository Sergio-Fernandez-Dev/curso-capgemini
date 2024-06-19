package com.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import main.java.com.example.Calculadora;

@DisplayName("Pruebas de la clase Calculadora")
class CalculadoraTest {
	Calculadora calculadora;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		calculadora = new Calculadora();
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Nested
	class Add {
		@Nested
		class OK {
			@Test
			@DisplayName("Suma dos enteros")
			@RepeatedTest(value = 5, name = "{displayName} {currentRepetition} / {totalRepetitions}")
			void testAdd() {
				var result = calculadora.add(1, 2);
				
				assertEquals(3, result);
			}
			
			@Test
			@DisplayName("Suma dos reales")
			void test2() {
				var result = calculadora.add(0.1, 0.2);
				
				assertEquals(0.3, result);
			}
		}	
		@Nested
		class KO {
			
		}
	}
	
	@Nested
	class Div {
		@Nested
		class OK {
			@Test
			@DisplayName("Divide dos enteros")
			void testDivInt() {
				var result = calculadora.div(3, 2);
				
				assertEquals(1, result);
			}
			
			@Test
			@DisplayName("Divide dos reales")
			void testDivReal() {
				var result = calculadora.div(3.0, 2.0);
				
				assertEquals(1.5, result);
			}
		}	
		@Nested
		class KO {
			@Test
			@DisplayName("Comprueba que lanza error aritmético")
			void testDivRealKO() {			
				assertThrows(ArithmeticException.class, () -> calculadora.div(3.0, 0));
			}
		}
	}

}
