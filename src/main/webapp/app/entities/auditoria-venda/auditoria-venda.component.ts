import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAuditoriaVenda } from 'app/shared/model/auditoria-venda.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { AuditoriaVendaService } from './auditoria-venda.service';
import { AuditoriaVendaDeleteDialogComponent } from './auditoria-venda-delete-dialog.component';

@Component({
  selector: 'rv-auditoria-venda',
  templateUrl: './auditoria-venda.component.html'
})
export class AuditoriaVendaComponent implements OnInit, OnDestroy {
  auditoriaVendas: IAuditoriaVenda[];
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
    protected auditoriaVendaService: AuditoriaVendaService,
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
    this.auditoriaVendaService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IAuditoriaVenda[]>) => this.paginateAuditoriaVendas(res.body, res.headers));
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/auditoria-venda'], {
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
      '/auditoria-venda',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInAuditoriaVendas();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IAuditoriaVenda) {
    return item.id;
  }

  registerChangeInAuditoriaVendas() {
    this.eventSubscriber = this.eventManager.subscribe('auditoriaVendaListModification', () => this.loadAll());
  }

  delete(auditoriaVenda: IAuditoriaVenda) {
    const modalRef = this.modalService.open(AuditoriaVendaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.auditoriaVenda = auditoriaVenda;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateAuditoriaVendas(data: IAuditoriaVenda[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.auditoriaVendas = data;
  }
}
