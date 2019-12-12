import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IContaCredito } from 'app/shared/model/conta-credito.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ContaCreditoService } from './conta-credito.service';
import { ContaCreditoDeleteDialogComponent } from './conta-credito-delete-dialog.component';

@Component({
  selector: 'rv-conta-credito',
  templateUrl: './conta-credito.component.html'
})
export class ContaCreditoComponent implements OnInit, OnDestroy {
  contaCreditos: IContaCredito[];
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
    protected contaCreditoService: ContaCreditoService,
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
    this.contaCreditoService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IContaCredito[]>) => this.paginateContaCreditos(res.body, res.headers));
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/conta-credito'], {
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
      '/conta-credito',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInContaCreditos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IContaCredito) {
    return item.id;
  }

  registerChangeInContaCreditos() {
    this.eventSubscriber = this.eventManager.subscribe('contaCreditoListModification', () => this.loadAll());
  }

  delete(contaCredito: IContaCredito) {
    const modalRef = this.modalService.open(ContaCreditoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.contaCredito = contaCredito;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateContaCreditos(data: IContaCredito[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.contaCreditos = data;
  }
}
