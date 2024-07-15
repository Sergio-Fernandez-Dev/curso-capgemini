import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ConfiguracionComponent } from './configuracion/configuracion.component';
import { DatosComponent } from './datos/datos.component';
import { PermisosComponent } from './permisos/permisos.component';
import { Routes, RouterModule } from '@angular/router';
import {
  ContactosListComponent,
  ContactosAddComponent,
  ContactosEditComponent,
  ContactosViewComponent,
} from '../contactos';

const routes: Routes = [
  { path: '', pathMatch: 'full', component: ConfiguracionComponent },
  { path: 'datos', component: DatosComponent },
  { path: 'permisos', component: PermisosComponent },
  {
    path: 'contactos',
    children: [
      { path: '', component: ContactosListComponent },
      { path: 'add', component: ContactosAddComponent },
      { path: ':id/edit', component: ContactosEditComponent },
      { path: ':id', component: ContactosViewComponent },
      { path: ':id/:kk', component: ContactosViewComponent },
    ],
  },
];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    ConfiguracionComponent,
    DatosComponent,
    PermisosComponent,
    ContactosListComponent,
    ContactosAddComponent,
    ContactosEditComponent,
    ContactosViewComponent,
    ContactosViewComponent,
  ],
})
export default class ConfigModule {}
