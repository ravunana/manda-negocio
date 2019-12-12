import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IRegraMovimentacaoDebito } from 'app/shared/model/regra-movimentacao-debito.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { RegraMovimentacaoDebitoService } from './regra-movimentacao-debito.service';
import { RegraMovimentacaoDebitoDeleteDialogComponent } from './regra-movimentacao-debito-delete-dialog.component';

@Component({
  selector: 'rv-regra-movimentacao-debito',
  templateUrl: './regra-movimentacao-debito.component.html'
})
export class RegraMovimentacaoDebitoComponent implements OnInit, OnDestroy {
  regraMovimentacaoDebitos: IRegraMovimentacaoDebito[];
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
    protected regraMovimentacaoDebitoService: RegraMovimentacaoDebitoService,
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
    this.regraMovimentacaoDebitoService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IRegraMovimentacaoDebito[]>) => this.paginateRegraMovimentacaoDebitos(res.body, res.headers));
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/regra-movimentacao-debito'], {
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
      '/regra-movimentacao-debito',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInRegraMovimentacaoDebitos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IRegraMovimentacaoDebito) {
    return item.id;
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  registerChangeInRegraMovimentacaoDebitos() {
    this.eventSubscriber = this.eventManager.subscribe('regraMovimentacaoDebitoListModification', () => this.loadAll());
  }

  delete(regraMovimentacaoDebito: IRegraMovimentacaoDebito) {
    const modalRef = this.modalService.open(RegraMovimentacaoDebitoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.regraMovimentacaoDebito = regraMovimentacaoDebito;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateRegraMovimentacaoDebitos(data: IRegraMovimentacaoDebito[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.regraMovimentacaoDebitos = data;
  }
}
