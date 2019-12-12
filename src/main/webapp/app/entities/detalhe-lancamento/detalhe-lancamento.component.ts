import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDetalheLancamento } from 'app/shared/model/detalhe-lancamento.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { DetalheLancamentoService } from './detalhe-lancamento.service';
import { DetalheLancamentoDeleteDialogComponent } from './detalhe-lancamento-delete-dialog.component';

@Component({
  selector: 'rv-detalhe-lancamento',
  templateUrl: './detalhe-lancamento.component.html'
})
export class DetalheLancamentoComponent implements OnInit, OnDestroy {
  detalheLancamentos: IDetalheLancamento[];
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
    protected detalheLancamentoService: DetalheLancamentoService,
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
    this.detalheLancamentoService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IDetalheLancamento[]>) => this.paginateDetalheLancamentos(res.body, res.headers));
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/detalhe-lancamento'], {
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
      '/detalhe-lancamento',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInDetalheLancamentos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IDetalheLancamento) {
    return item.id;
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  registerChangeInDetalheLancamentos() {
    this.eventSubscriber = this.eventManager.subscribe('detalheLancamentoListModification', () => this.loadAll());
  }

  delete(detalheLancamento: IDetalheLancamento) {
    const modalRef = this.modalService.open(DetalheLancamentoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.detalheLancamento = detalheLancamento;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateDetalheLancamentos(data: IDetalheLancamento[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.detalheLancamentos = data;
  }
}
