/* eslint-disable @typescript-eslint/no-explicit-any */
import { Component } from '@angular/core';
import { HomeComponent } from 'src/app/main/home/home.component';
import GraficoSvgComponent from 'src/lib/my-core/components/grafico-svg/grafico-svg.component';
import { DemosComponent } from '../demos/demos.component';
import { NotificationComponent } from "../../main/notification/notification.component";
import { CommonModule } from '@angular/common';
import { CalculadoraComponent } from 'src/lib/my-core/components/calculadora/calculadora.component';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [NotificationComponent, CommonModule],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css',
})
export class DashboardComponent {
  menu = [
    { texto: 'calculadora', icono: '', componente: CalculadoraComponent },
    { texto: 'inicio', icono: '', componente: HomeComponent },
    { texto: 'demos', icono: '', componente: DemosComponent },
    { texto: 'gráfico', icono: '', componente: GraficoSvgComponent },

  ];
  actual: any = this.menu[0].componente;

  seleccionar(indice: number) {
    this.actual = this.menu[indice].componente;
  }
}
