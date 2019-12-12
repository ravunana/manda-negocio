import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEstoqueConfig } from 'app/shared/model/estoque-config.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { EstoqueConfigService } from './estoque-config.service';
import { EstoqueConfigDeleteDialogComponent } from './estoque-config-delete-dialog.component';

@Component({
  selector: 'rv-estoque-config',
  templateUrl: './estoque-config.component.html'
})
export class EstoqueConfigComponent implements OnInit, OnDestroy {
  estoqueConfigs: IEstoqueConfig[];
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
    protected estoqueConfigService: EstoqueConfigService,
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
    this.estoqueConfigService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IEstoqueConfig[]>) => this.paginateEstoqueConfigs(res.body, res.headers));
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/estoque-config'], {
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
      '/estoque-config',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInEstoqueConfigs();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IEstoqueConfig) {
    return item.id;
  }

  registerChangeInEstoqueConfigs() {
    this.eventSubscriber = this.eventManager.subscribe('estoqueConfigListModification', () => this.loadAll());
  }

  delete(estoqueConfig: IEstoqueConfig) {
    const modalRef = this.modalService.open(EstoqueConfigDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.estoqueConfig = estoqueConfig;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateEstoqueConfigs(data: IEstoqueConfig[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.estoqueConfigs = data;
  }
}
