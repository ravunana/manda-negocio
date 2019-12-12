import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IVariacaoProduto } from 'app/shared/model/variacao-produto.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { VariacaoProdutoService } from './variacao-produto.service';
import { VariacaoProdutoDeleteDialogComponent } from './variacao-produto-delete-dialog.component';

@Component({
  selector: 'rv-variacao-produto',
  templateUrl: './variacao-produto.component.html'
})
export class VariacaoProdutoComponent implements OnInit, OnDestroy {
  variacaoProdutos: IVariacaoProduto[];
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
    protected variacaoProdutoService: VariacaoProdutoService,
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
    this.variacaoProdutoService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IVariacaoProduto[]>) => this.paginateVariacaoProdutos(res.body, res.headers));
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/variacao-produto'], {
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
      '/variacao-produto',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInVariacaoProdutos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IVariacaoProduto) {
    return item.id;
  }

  registerChangeInVariacaoProdutos() {
    this.eventSubscriber = this.eventManager.subscribe('variacaoProdutoListModification', () => this.loadAll());
  }

  delete(variacaoProduto: IVariacaoProduto) {
    const modalRef = this.modalService.open(VariacaoProdutoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.variacaoProduto = variacaoProduto;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateVariacaoProdutos(data: IVariacaoProduto[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.variacaoProdutos = data;
  }
}
