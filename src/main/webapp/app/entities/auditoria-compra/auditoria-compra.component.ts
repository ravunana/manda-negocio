import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAuditoriaCompra } from 'app/shared/model/auditoria-compra.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { AuditoriaCompraService } from './auditoria-compra.service';
import { AuditoriaCompraDeleteDialogComponent } from './auditoria-compra-delete-dialog.component';

@Component({
  selector: 'rv-auditoria-compra',
  templateUrl: './auditoria-compra.component.html'
})
export class AuditoriaCompraComponent implements OnInit, OnDestroy {
  auditoriaCompras: IAuditoriaCompra[];
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
    protected auditoriaCompraService: AuditoriaCompraService,
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
    this.auditoriaCompraService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IAuditoriaCompra[]>) => this.paginateAuditoriaCompras(res.body, res.headers));
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/auditoria-compra'], {
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
      '/auditoria-compra',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInAuditoriaCompras();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IAuditoriaCompra) {
    return item.id;
  }

  registerChangeInAuditoriaCompras() {
    this.eventSubscriber = this.eventManager.subscribe('auditoriaCompraListModification', () => this.loadAll());
  }

  delete(auditoriaCompra: IAuditoriaCompra) {
    const modalRef = this.modalService.open(AuditoriaCompraDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.auditoriaCompra = auditoriaCompra;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateAuditoriaCompras(data: IAuditoriaCompra[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.auditoriaCompras = data;
  }
}
