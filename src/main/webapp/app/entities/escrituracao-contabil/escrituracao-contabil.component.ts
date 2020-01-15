import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEscrituracaoContabil } from 'app/shared/model/escrituracao-contabil.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { EscrituracaoContabilService } from './escrituracao-contabil.service';
import { EscrituracaoContabilDeleteDialogComponent } from './escrituracao-contabil-delete-dialog.component';

@Component({
  selector: 'rv-escrituracao-contabil',
  templateUrl: './escrituracao-contabil.component.html'
})
export class EscrituracaoContabilComponent implements OnInit, OnDestroy {
  escrituracaoContabils: IEscrituracaoContabil[];
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
  filter;

  constructor(
    protected escrituracaoContabilService: EscrituracaoContabilService,
    protected parseLinks: JhiParseLinks,
    protected activatedRoute: ActivatedRoute,
    protected dataUtils: JhiDataUtils,
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
    this.escrituracaoContabilService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IEscrituracaoContabil[]>) => this.paginateEscrituracaoContabils(res.body, res.headers));
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/escrituracao-contabil'], {
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
      '/escrituracao-contabil',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInEscrituracaoContabils();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IEscrituracaoContabil) {
    return item.id;
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  registerChangeInEscrituracaoContabils() {
    this.eventSubscriber = this.eventManager.subscribe('escrituracaoContabilListModification', () => this.loadAll());
  }

  delete(escrituracaoContabil: IEscrituracaoContabil) {
    const modalRef = this.modalService.open(EscrituracaoContabilDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.escrituracaoContabil = escrituracaoContabil;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateEscrituracaoContabils(data: IEscrituracaoContabil[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.escrituracaoContabils = data;
  }
}
