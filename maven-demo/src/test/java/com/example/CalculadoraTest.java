package com.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.java.com.example.Calculadora;

class CalculadoraTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void test() {
		var calculadora = new Calculadora();
		
		var result = calculadora.add(1, 2);
		
		assertEquals(3, result);
	}
	
	@Test
	void test2() {
		var calculadora = new Calculadora();
		
		var result = calculadora.add(0.1, 0.2);
		
		assertEquals(0.3, result);
	}
	
	@Test
	void testDivInt() {
		var calculadora = new Calculadora();
		
		var result = calculadora.div(3, 2);
		
		assertEquals(1, result);
	}

	@Test
	void testDivReal() {
		var calculadora = new Calculadora();
		
		var result = calculadora.div(3.0, 2.0);
		
		assertEquals(1.5, result);
	}
	
	@Test
	void testDivRealKO() {
		var calculadora = new Calculadora();
		
		assertThrows(ArithmeticException.class, () -> calculadora.div(3.0, 0));
	}

}
