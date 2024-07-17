/* eslint-disable @typescript-eslint/no-unused-vars */
/* eslint-disable @typescript-eslint/no-empty-function */
/* eslint-disable @angular-eslint/no-empty-lifecycle-method */
import {
  Component,
  OnInit,
  OnDestroy,
  Input,
  OnChanges,
  SimpleChanges,
  forwardRef,
} from '@angular/core';
import { ActivatedRoute, Router, ParamMap, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { DatePipe, NgIf } from '@angular/common';
import { PaginatorModule } from 'primeng/paginator';

import { Subscription } from 'rxjs';
import { FilmsViewModelService } from './servicios.service';
import { TypeValidator } from '../../lib/my-components';

@Component({
  selector: 'app-films',
  templateUrl: './tmpl-anfitrion.component.html',
  styleUrls: ['./films.component.css'],
  // providers: [ActorsViewModelService]
  standalone: true,
  imports: [
    forwardRef(() => FilmsAddComponent),
    forwardRef(() => FilmsEditComponent),
    forwardRef(() => FilmsViewComponent),
    forwardRef(() => FilmsListComponent),
  ],
})
export class FilmsComponent implements OnInit, OnDestroy {
  constructor(protected vm: FilmsViewModelService) {}
  public get VM(): FilmsViewModelService {
    return this.vm;
  }
  ngOnInit(): void {
    // this.vm.list();
    this.vm.load();
  }
  ngOnDestroy(): void {
    this.vm.clear();
  }
}

@Component({
  selector: 'app-films-list',
  templateUrl: './tmpl-list.sin-rutas.component.html',
  styleUrls: ['./films.component.css'],
  standalone: true,
  imports: [PaginatorModule, NgIf],
})
export class FilmsListComponent implements OnInit, OnDestroy {
  constructor(protected vm: FilmsViewModelService) {}
  public get VM(): FilmsViewModelService {
    return this.vm;
  }
  ngOnInit(): void {}
  ngOnDestroy(): void {}
}
@Component({
  selector: 'app-films-add',
  templateUrl: './tmpl-form.component.html',
  styleUrls: ['./films.component.css'],
  standalone: true,
  imports: [FormsModule, TypeValidator],
})
export class FilmsAddComponent implements OnInit {
  constructor(protected vm: FilmsViewModelService) {}
  public get VM(): FilmsViewModelService {
    return this.vm;
  }
  ngOnInit(): void {}
}
@Component({
  selector: 'app-films-edit',
  templateUrl: './tmpl-form.component.html',
  styleUrls: ['./films.component.css'],
  standalone: true,
  imports: [FormsModule, TypeValidator],
})
export class FilmsEditComponent implements OnInit, OnDestroy {
  constructor(protected vm: FilmsViewModelService) {}
  public get VM(): FilmsViewModelService {
    return this.vm;
  }
  ngOnInit(): void {}
  ngOnDestroy(): void {}
}
@Component({
  selector: 'app-films-view',
  templateUrl: './tmpl-view.component.html',
  styleUrls: ['./films.component.css'],
  standalone: true,
  imports: [DatePipe],
})
export class FilmsViewComponent implements OnInit, OnDestroy {
  constructor(protected vm: FilmsViewModelService) {}
  public get VM(): FilmsViewModelService {
    return this.vm;
  }
  ngOnInit(): void {}
  ngOnDestroy(): void {}
}

export const FILMS_COMPONENTS = [
  FilmsListComponent,
  FilmsAddComponent,
  FilmsEditComponent,
  FilmsViewComponent,
];
