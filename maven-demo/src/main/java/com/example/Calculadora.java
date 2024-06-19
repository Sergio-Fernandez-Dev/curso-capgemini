package com.example;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Calculadora {
	
	private double redondea(double o) {
		return (new BigDecimal(o).setScale(16, RoundingMode.HALF_UP).doubleValue());
	}
	
	public double add(double a, double b) {
			return redondea(a + b);
	}
	
	public double div(double a, double b) {
		if(b == 0) throw new ArithmeticException(" / by zero");
		return redondea(a / b);
	}
	
	public double div(int a, int b) {
		if(b == 0) throw new ArithmeticException(" / by zero");
		return redondea(a / b);
	}
}
