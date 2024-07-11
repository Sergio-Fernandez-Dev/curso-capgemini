import { Component, OnDestroy, OnInit } from '@angular/core';
import { NotificationService, NotificationType } from '../common-services';
import { Unsubscribable } from 'rxjs';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-demos',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './demos.component.html',
  styleUrl: './demos.component.css',
})
export class DemosComponent implements OnInit, OnDestroy {
  private suscriptor: Unsubscribable | undefined;
  private nombre: string = 'mundo';
  fecha = '2024-07-11';
  fontSize = 24;
  listado = [
    { id: 1, nombre: 'Madrid' },
    { id: 2, nombre: 'Barcelona' },
    { id: 3, nombre: 'Oviedo' },
    { id: 4, nombre: 'Ciudad Real' },
  ];
  idProvincia = 2;
  resultado?: string;
  visible = true;
  estetica = { importante: true, error: false, urgente: true };

  constructor(public vm: NotificationService) {}

  public get Nombre(): string {
    return this.nombre;
  }

  public set Nombre(value: string) {
    if (this.nombre === value) return;
    this.nombre = value;
  }

  public saluda(): void {
    this.resultado = `Hola ${this.nombre}`;
  }

  public despide(): void {
    this.resultado = `Adios ${this.nombre}`;
  }

  public di(algo: string): void {
    this.resultado = `Dice ${algo}`;
  }

  public cambia(): void {
    this.visible = !this.visible;
    this.estetica.error = !this.estetica.importante;
  }

  public calcula(a: number, b: number): number {
    return a + b;
  }

  public add(provincia: string): void {
    const id = this.listado[this.listado.length - 1]. id + 1;
    this.listado.push({id, nombre: provincia});
    this.idProvincia = id;
  }

  ngOnInit(): void {
    this.suscriptor = this.vm.Notificacion.subscribe((n) => {
      if (n.Type !== NotificationType.error) {
        return;
      }
      window.alert(`Suscripcion: ${n.Message}`);
      this.vm.remove(this.vm.Listado.length - 1);
    });
  }
  ngOnDestroy(): void {
    if (this.suscriptor) {
      this.suscriptor.unsubscribe();
    }
  }
}
