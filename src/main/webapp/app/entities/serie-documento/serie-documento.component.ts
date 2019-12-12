import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISerieDocumento } from 'app/shared/model/serie-documento.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { SerieDocumentoService } from './serie-documento.service';
import { SerieDocumentoDeleteDialogComponent } from './serie-documento-delete-dialog.component';

@Component({
  selector: 'rv-serie-documento',
  templateUrl: './serie-documento.component.html'
})
export class SerieDocumentoComponent implements OnInit, OnDestroy {
  serieDocumentos: ISerieDocumento[];
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
    protected serieDocumentoService: SerieDocumentoService,
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
    this.serieDocumentoService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ISerieDocumento[]>) => this.paginateSerieDocumentos(res.body, res.headers));
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/serie-documento'], {
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
      '/serie-documento',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInSerieDocumentos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ISerieDocumento) {
    return item.id;
  }

  registerChangeInSerieDocumentos() {
    this.eventSubscriber = this.eventManager.subscribe('serieDocumentoListModification', () => this.loadAll());
  }

  delete(serieDocumento: ISerieDocumento) {
    const modalRef = this.modalService.open(SerieDocumentoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.serieDocumento = serieDocumento;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateSerieDocumentos(data: ISerieDocumento[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.serieDocumentos = data;
  }
}
