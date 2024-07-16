import { Component } from '@angular/core';

@Component({
  selector: 'app-calculadora',
  standalone: true,
  imports: [],
  templateUrl: './calculadora.component.html',
  styleUrl: './calculadora.component.css'
})
export class CalculadoraComponent {
  displayValue = '0';
  firstOperand: number | null = null;
  operator: string | null = null;
  waitingForSecondOperand = false;

  appendNumber(number: string) {
    if (this.displayValue.length >= 9) return;
    if (this.waitingForSecondOperand) {
      this.displayValue = number;
      this.waitingForSecondOperand = false;
    } else {
      this.displayValue = this.displayValue === '0' && number !== '.' ? number : this.displayValue + number;
    }
  }

  chooseOperation(operation: string) {
    if (this.firstOperand === null) {
      this.firstOperand = parseFloat(this.displayValue);
    } else if (this.operator) {
      const result = this.calculateResult(this.firstOperand, parseFloat(this.displayValue), this.operator);
      this.displayValue = `${this.roundToFixed(result, 10)}`;
      this.firstOperand = result;
    }
    this.operator = operation;
    this.waitingForSecondOperand = true;
  }

  calculate() {
    if (this.operator && this.firstOperand !== null) {
      this.displayValue = String(this.calculateResult(this.firstOperand, parseFloat(this.displayValue), this.operator));
      this.firstOperand = null;
      this.operator = null;
      this.waitingForSecondOperand = false;
    }
  }

  calculateResult(first: number, second: number, operator: string): number {
    switch (operator) {
      case '+':
        return first + second;
      case '-':
        return first - second;
      case '*':
        return first * second;
      case '/':
        return first / second;
      default:
        return second;
    }
  }

  roundToFixed(value: number, decimals: number): number {
    const factor = Math.pow(10, decimals);
    return Math.round(value * factor) / factor;
  }

  clear() {
    this.displayValue = '0';
    this.firstOperand = null;
    this.operator = null;
    this.waitingForSecondOperand = false;
  }
}
