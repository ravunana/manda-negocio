import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICoordenadaBancaria } from 'app/shared/model/coordenada-bancaria.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { CoordenadaBancariaService } from './coordenada-bancaria.service';
import { CoordenadaBancariaDeleteDialogComponent } from './coordenada-bancaria-delete-dialog.component';

@Component({
  selector: 'rv-coordenada-bancaria',
  templateUrl: './coordenada-bancaria.component.html'
})
export class CoordenadaBancariaComponent implements OnInit, OnDestroy {
  coordenadaBancarias: ICoordenadaBancaria[];
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
    protected coordenadaBancariaService: CoordenadaBancariaService,
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
    this.coordenadaBancariaService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ICoordenadaBancaria[]>) => this.paginateCoordenadaBancarias(res.body, res.headers));
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/coordenada-bancaria'], {
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
      '/coordenada-bancaria',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInCoordenadaBancarias();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICoordenadaBancaria) {
    return item.id;
  }

  registerChangeInCoordenadaBancarias() {
    this.eventSubscriber = this.eventManager.subscribe('coordenadaBancariaListModification', () => this.loadAll());
  }

  delete(coordenadaBancaria: ICoordenadaBancaria) {
    const modalRef = this.modalService.open(CoordenadaBancariaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.coordenadaBancaria = coordenadaBancaria;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateCoordenadaBancarias(data: ICoordenadaBancaria[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.coordenadaBancarias = data;
  }
}
