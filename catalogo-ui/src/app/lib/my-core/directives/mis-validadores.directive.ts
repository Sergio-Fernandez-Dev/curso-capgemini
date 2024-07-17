import { Directive, ElementRef, forwardRef } from '@angular/core';
import { AbstractControl, NG_VALIDATORS, Validator, ValidationErrors } from '@angular/forms';


@Directive({
  selector: '[type][formControlName],[type][formControl],[type][ngModel]',
  standalone: true,
  providers: [
      { provide: NG_VALIDATORS, useExisting: forwardRef(() => TypeValidator), multi: true }
  ]
})
export class TypeValidator implements Validator {
  constructor(private elem: ElementRef) { }
  validate(control: AbstractControl): ValidationErrors | null {
      const valor = control.value;
      if (valor) {
        const dom = this.elem.nativeElement;
        if (dom.validity) { // dom.checkValidity();
          return (dom.validity.typeMismatch || dom.validity.stepMismatch) ? { 'type': dom.validationMessage } : null;
        }
      }
      return null;
  }
}

