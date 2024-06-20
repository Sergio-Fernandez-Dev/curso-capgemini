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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


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
			
			@ParameterizedTest(name = "Caso {index}: {0} + {1} = {2}")
			@DisplayName("Suma dos reales")
			@CsvSource(value = {"1,2,3","3,-1,2","-1,2,1","-2,-3,-5","0,1,1","0.1,0.2,0.3"})
			void testAdd(double operando1, double operando2, double result) {
				assertEquals(result, calculadora.add(operando1, operando2));
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
