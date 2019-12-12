import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILocalizacaoEmpresa } from 'app/shared/model/localizacao-empresa.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { LocalizacaoEmpresaService } from './localizacao-empresa.service';
import { LocalizacaoEmpresaDeleteDialogComponent } from './localizacao-empresa-delete-dialog.component';

@Component({
  selector: 'rv-localizacao-empresa',
  templateUrl: './localizacao-empresa.component.html'
})
export class LocalizacaoEmpresaComponent implements OnInit, OnDestroy {
  localizacaoEmpresas: ILocalizacaoEmpresa[];
  error: any;
  success: any;
  eventSubscriber: Subscription;
  routeData: any;
  links: any;
  totalItems: any;
  itemsPerPage: any;
  page: any;
  predicate: any;
  previousPage: any;
  reverse: any;

  constructor(
    protected localizacaoEmpresaService: LocalizacaoEmpresaService,
    protected parseLinks: JhiParseLinks,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.routeData = this.activatedRoute.data.subscribe(data => {
      this.page = data.pagingParams.page;
      this.previousPage = data.pagingParams.page;
      this.reverse = data.pagingParams.ascending;
      this.predicate = data.pagingParams.predicate;
    });
  }

  loadAll() {
    this.localizacaoEmpresaService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ILocalizacaoEmpresa[]>) => this.paginateLocalizacaoEmpresas(res.body, res.headers));
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/localizacao-empresa'], {
      queryParams: {
        page: this.page,
        size: this.itemsPerPage,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    });
    this.loadAll();
  }

  clear() {
    this.page = 0;
    this.router.navigate([
      '/localizacao-empresa',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInLocalizacaoEmpresas();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ILocalizacaoEmpresa) {
    return item.id;
  }

  registerChangeInLocalizacaoEmpresas() {
    this.eventSubscriber = this.eventManager.subscribe('localizacaoEmpresaListModification', () => this.loadAll());
  }

  delete(localizacaoEmpresa: ILocalizacaoEmpresa) {
    const modalRef = this.modalService.open(LocalizacaoEmpresaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.localizacaoEmpresa = localizacaoEmpresa;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateLocalizacaoEmpresas(data: ILocalizacaoEmpresa[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.localizacaoEmpresas = data;
  }
}
