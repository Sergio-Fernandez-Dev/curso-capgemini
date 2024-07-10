import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { SecurityModule } from './security';
import { DemosComponent } from './demos/demos.component';
import { NotificationModalComponent } from './main';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, SecurityModule, NotificationModalComponent,DemosComponent,],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title: string = 'angular-project';
}
