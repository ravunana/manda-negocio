import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICompostoProduto } from 'app/shared/model/composto-produto.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { CompostoProdutoService } from './composto-produto.service';
import { CompostoProdutoDeleteDialogComponent } from './composto-produto-delete-dialog.component';

@Component({
  selector: 'rv-composto-produto',
  templateUrl: './composto-produto.component.html'
})
export class CompostoProdutoComponent implements OnInit, OnDestroy {
  compostoProdutos: ICompostoProduto[];
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
    protected compostoProdutoService: CompostoProdutoService,
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
    this.compostoProdutoService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ICompostoProduto[]>) => this.paginateCompostoProdutos(res.body, res.headers));
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/composto-produto'], {
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
      '/composto-produto',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInCompostoProdutos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICompostoProduto) {
    return item.id;
  }

  registerChangeInCompostoProdutos() {
    this.eventSubscriber = this.eventManager.subscribe('compostoProdutoListModification', () => this.loadAll());
  }

  delete(compostoProduto: ICompostoProduto) {
    const modalRef = this.modalService.open(CompostoProdutoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.compostoProduto = compostoProduto;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateCompostoProdutos(data: ICompostoProduto[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.compostoProdutos = data;
  }
}
