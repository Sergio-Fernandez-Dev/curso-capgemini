/* eslint-disable @typescript-eslint/no-explicit-any */
import { HttpContext, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

import { Observable } from 'rxjs';
import { RESTDAOService, ModoCRUD } from '../code-base';
import { NavigationService, NotificationService } from '../common-services';
import { LoggerService } from '../../lib/my-components/services/logger.service';
import { AUTH_REQUIRED, AuthService } from '../security';

export interface IFilm{
  [index: string]: any;
  filmId?: number
  description?: string
  length?: number
  rating?: string
  releaseYear?: number
  rentalDuration?: number
  rentalRate?: number
  replacementCost?: number
  title?: string
  languageId?: number
  actors?: number[],
  categories?: number; 
}

export class Film implements IFilm {
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  [index: string]: any;
  constructor(
    public filmId?: number,
    public description?: string,
    public length?: number,
    public rating?: string,
    public releaseYear?: number,
    public rentalDuration?: number,
    public rentalRate?: number,
    public replacementCost?: number,
    public title?: string,
    public languageId?: number,
    public actors?: number[],
    public categories?: number 
  ) { }
}

@Injectable({
  providedIn: 'root'
})
export class FilmsDAOService extends RESTDAOService<any, number> {
  constructor() {
    super('films', { context: new HttpContext().set(AUTH_REQUIRED, false) });
  }
  page(page: number, rows: number = 20): Observable<{ page: number, pages: number, rows: number, list: Array<any> }> {
    return new Observable(subscriber => {
      const url = `${this.baseUrl}?_page=${page}&_rows=${rows}&_sort=title`
      this.http.get<any>(url, this.option).subscribe({
        next: data => subscriber.next({ page: data.number, pages: data.totalPages, rows: data.totalElements, list: data.content }),
        error: err => subscriber.error(err)
      })
    })
  }
}

@Injectable({
  providedIn: 'root'
})
export class FilmsViewModelService {
  protected modo: ModoCRUD = 'list';
  protected listado: IFilm[] = [];
  protected elemento: IFilm = {};
  protected idOriginal?: number;
  protected listURL = '/films/v1';

  constructor(protected notify: NotificationService,
    protected out: LoggerService,
    protected dao: FilmsDAOService
    , public auth: AuthService, protected router: Router, private navigation: NavigationService
  ) { }

  public get Modo(): ModoCRUD { return this.modo; }
  public get Listado() { return this.listado; }
  public get Elemento() { return this.elemento; }

  public list(): void {
    this.dao.query().subscribe({
      next: data => {
        this.listado = data;
        this.modo = 'list';
      },
      error: err => this.handleError(err)
    });
  }

  public add(): void {
    this.elemento = new Film();
    this.modo = 'add';
  }
  public edit(key: any): void {
    this.dao.get(key).subscribe({
      next: data => {
        this.elemento = data;
        this.idOriginal = key;
        this.modo = 'edit';
      },
      error: err => this.handleError(err)
    });
  }
  public view(key: any): void {
    this.dao.get(key).subscribe({
      next: data => {
        this.elemento = data;
        this.modo = 'view';
      },
      error: err => this.handleError(err)
    });
  }
  public delete(key: any): void {
    if (!window.confirm('¿Seguro?')) { return; }

    this.dao.remove(key).subscribe({
      next: () => {
        // this.list()
        this.load()
      },
      error: err => this.handleError(err)
    });
  }

  clear() {
    this.elemento = {};
    this.idOriginal = undefined;
    this.listado = [];
  }

  public cancel(): void {
    this.clear()
    // this.list();
    this.load(this.page)
    // this.router.navigateByUrl(this.listURL);
    // this.navigation.back()
  }
  public send(): void {
    switch (this.modo) {
      case 'add':
        this.dao.add(this.elemento).subscribe({
          next: () => this.cancel(),
          error: err => this.handleError(err)
        });
        break;
      case 'edit':
        if (!this.idOriginal) {
          this.out.error('Falta el identificador')
          return
        }
        this.dao.change(this.idOriginal, this.elemento).subscribe({
          next: () => this.cancel(),
          error: err => this.handleError(err)
        });
        break;
      case 'view':
        this.cancel();
        break;
    }
  }

  handleError(err: HttpErrorResponse) {
    let msg = ''
    switch (err.status) {
      case 0: msg = err.message; break;
      case 404: msg = `ERROR ${err.status}: ${err.statusText}`; break;
      default:
        msg = `ERROR ${err.status}: ${err.error?.['title'] ?? err.statusText}.${err.error?.['detail'] ? ` Detalles: ${err.error['detail']}` : ''}`
        break;
    }
    this.notify.add(msg)
  }

  // Paginado

  page = 0;
  totalPages = 0;
  totalRows = 0;
  rowsPerPage = 8;
  load(page: number = -1) {
    if (page < 0) page = this.page
    this.dao.page(page, this.rowsPerPage).subscribe({
      next: rslt => {
        this.page = rslt.page;
        this.totalPages = rslt.pages;
        this.totalRows = rslt.rows;
        this.listado = rslt.list;
        this.modo = 'list';
      },
      error: err => this.handleError(err)
    })
  }
  pageChange(page: number = 0) {
    this.router.navigate([], { queryParams: { page } })
  }
  imageErrorHandler(event: Event, item: any) {
    (event.target as HTMLImageElement).src = item.sexo === 'H' ? '/images/user-not-found-male.png' : '/images/user-not-found-female.png'
  }

}
