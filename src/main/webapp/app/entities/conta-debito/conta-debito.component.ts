import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IContaDebito } from 'app/shared/model/conta-debito.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ContaDebitoService } from './conta-debito.service';
import { ContaDebitoDeleteDialogComponent } from './conta-debito-delete-dialog.component';

@Component({
  selector: 'rv-conta-debito',
  templateUrl: './conta-debito.component.html'
})
export class ContaDebitoComponent implements OnInit, OnDestroy {
  contaDebitos: IContaDebito[];
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
    protected contaDebitoService: ContaDebitoService,
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
    this.contaDebitoService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IContaDebito[]>) => this.paginateContaDebitos(res.body, res.headers));
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/conta-debito'], {
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
      '/conta-debito',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInContaDebitos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IContaDebito) {
    return item.id;
  }

  registerChangeInContaDebitos() {
    this.eventSubscriber = this.eventManager.subscribe('contaDebitoListModification', () => this.loadAll());
  }

  delete(contaDebito: IContaDebito) {
    const modalRef = this.modalService.open(ContaDebitoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.contaDebito = contaDebito;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateContaDebitos(data: IContaDebito[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.contaDebitos = data;
  }
}
