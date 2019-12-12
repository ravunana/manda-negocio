import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILancamentoFinanceiro } from 'app/shared/model/lancamento-financeiro.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { LancamentoFinanceiroService } from './lancamento-financeiro.service';
import { LancamentoFinanceiroDeleteDialogComponent } from './lancamento-financeiro-delete-dialog.component';

@Component({
  selector: 'rv-lancamento-financeiro',
  templateUrl: './lancamento-financeiro.component.html'
})
export class LancamentoFinanceiroComponent implements OnInit, OnDestroy {
  lancamentoFinanceiros: ILancamentoFinanceiro[];
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
    protected lancamentoFinanceiroService: LancamentoFinanceiroService,
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
    this.lancamentoFinanceiroService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ILancamentoFinanceiro[]>) => this.paginateLancamentoFinanceiros(res.body, res.headers));
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/lancamento-financeiro'], {
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
      '/lancamento-financeiro',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInLancamentoFinanceiros();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ILancamentoFinanceiro) {
    return item.id;
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  registerChangeInLancamentoFinanceiros() {
    this.eventSubscriber = this.eventManager.subscribe('lancamentoFinanceiroListModification', () => this.loadAll());
  }

  delete(lancamentoFinanceiro: ILancamentoFinanceiro) {
    const modalRef = this.modalService.open(LancamentoFinanceiroDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.lancamentoFinanceiro = lancamentoFinanceiro;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateLancamentoFinanceiros(data: ILancamentoFinanceiro[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.lancamentoFinanceiros = data;
  }
}
