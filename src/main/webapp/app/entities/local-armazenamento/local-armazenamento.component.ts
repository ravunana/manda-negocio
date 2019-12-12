import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILocalArmazenamento } from 'app/shared/model/local-armazenamento.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { LocalArmazenamentoService } from './local-armazenamento.service';
import { LocalArmazenamentoDeleteDialogComponent } from './local-armazenamento-delete-dialog.component';

@Component({
  selector: 'rv-local-armazenamento',
  templateUrl: './local-armazenamento.component.html'
})
export class LocalArmazenamentoComponent implements OnInit, OnDestroy {
  localArmazenamentos: ILocalArmazenamento[];
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
    protected localArmazenamentoService: LocalArmazenamentoService,
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
    this.localArmazenamentoService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ILocalArmazenamento[]>) => this.paginateLocalArmazenamentos(res.body, res.headers));
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/local-armazenamento'], {
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
      '/local-armazenamento',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInLocalArmazenamentos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ILocalArmazenamento) {
    return item.id;
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  registerChangeInLocalArmazenamentos() {
    this.eventSubscriber = this.eventManager.subscribe('localArmazenamentoListModification', () => this.loadAll());
  }

  delete(localArmazenamento: ILocalArmazenamento) {
    const modalRef = this.modalService.open(LocalArmazenamentoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.localArmazenamento = localArmazenamento;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateLocalArmazenamentos(data: ILocalArmazenamento[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.localArmazenamentos = data;
  }
}
