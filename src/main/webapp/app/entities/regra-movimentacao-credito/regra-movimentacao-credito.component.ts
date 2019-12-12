import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IRegraMovimentacaoCredito } from 'app/shared/model/regra-movimentacao-credito.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { RegraMovimentacaoCreditoService } from './regra-movimentacao-credito.service';
import { RegraMovimentacaoCreditoDeleteDialogComponent } from './regra-movimentacao-credito-delete-dialog.component';

@Component({
  selector: 'rv-regra-movimentacao-credito',
  templateUrl: './regra-movimentacao-credito.component.html'
})
export class RegraMovimentacaoCreditoComponent implements OnInit, OnDestroy {
  regraMovimentacaoCreditos: IRegraMovimentacaoCredito[];
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
    protected regraMovimentacaoCreditoService: RegraMovimentacaoCreditoService,
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
    this.regraMovimentacaoCreditoService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IRegraMovimentacaoCredito[]>) => this.paginateRegraMovimentacaoCreditos(res.body, res.headers));
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/regra-movimentacao-credito'], {
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
      '/regra-movimentacao-credito',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInRegraMovimentacaoCreditos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IRegraMovimentacaoCredito) {
    return item.id;
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  registerChangeInRegraMovimentacaoCreditos() {
    this.eventSubscriber = this.eventManager.subscribe('regraMovimentacaoCreditoListModification', () => this.loadAll());
  }

  delete(regraMovimentacaoCredito: IRegraMovimentacaoCredito) {
    const modalRef = this.modalService.open(RegraMovimentacaoCreditoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.regraMovimentacaoCredito = regraMovimentacaoCredito;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateRegraMovimentacaoCreditos(data: IRegraMovimentacaoCredito[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.regraMovimentacaoCreditos = data;
  }
}
