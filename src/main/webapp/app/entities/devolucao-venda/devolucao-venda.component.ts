import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDevolucaoVenda } from 'app/shared/model/devolucao-venda.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { DevolucaoVendaService } from './devolucao-venda.service';
import { DevolucaoVendaDeleteDialogComponent } from './devolucao-venda-delete-dialog.component';

@Component({
  selector: 'rv-devolucao-venda',
  templateUrl: './devolucao-venda.component.html'
})
export class DevolucaoVendaComponent implements OnInit, OnDestroy {
  devolucaoVendas: IDevolucaoVenda[];
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
    protected devolucaoVendaService: DevolucaoVendaService,
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
    this.devolucaoVendaService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IDevolucaoVenda[]>) => this.paginateDevolucaoVendas(res.body, res.headers));
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/devolucao-venda'], {
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
      '/devolucao-venda',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInDevolucaoVendas();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IDevolucaoVenda) {
    return item.id;
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  registerChangeInDevolucaoVendas() {
    this.eventSubscriber = this.eventManager.subscribe('devolucaoVendaListModification', () => this.loadAll());
  }

  delete(devolucaoVenda: IDevolucaoVenda) {
    const modalRef = this.modalService.open(DevolucaoVendaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.devolucaoVenda = devolucaoVenda;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateDevolucaoVendas(data: IDevolucaoVenda[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.devolucaoVendas = data;
  }
}
