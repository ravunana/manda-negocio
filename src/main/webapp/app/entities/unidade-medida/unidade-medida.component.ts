import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IUnidadeMedida } from 'app/shared/model/unidade-medida.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { UnidadeMedidaService } from './unidade-medida.service';
import { UnidadeMedidaDeleteDialogComponent } from './unidade-medida-delete-dialog.component';

@Component({
  selector: 'rv-unidade-medida',
  templateUrl: './unidade-medida.component.html'
})
export class UnidadeMedidaComponent implements OnInit, OnDestroy {
  unidadeMedidas: IUnidadeMedida[];
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
    protected unidadeMedidaService: UnidadeMedidaService,
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
    this.unidadeMedidaService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IUnidadeMedida[]>) => this.paginateUnidadeMedidas(res.body, res.headers));
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/unidade-medida'], {
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
      '/unidade-medida',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInUnidadeMedidas();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IUnidadeMedida) {
    return item.id;
  }

  registerChangeInUnidadeMedidas() {
    this.eventSubscriber = this.eventManager.subscribe('unidadeMedidaListModification', () => this.loadAll());
  }

  delete(unidadeMedida: IUnidadeMedida) {
    const modalRef = this.modalService.open(UnidadeMedidaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.unidadeMedida = unidadeMedida;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateUnidadeMedidas(data: IUnidadeMedida[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.unidadeMedidas = data;
  }
}
