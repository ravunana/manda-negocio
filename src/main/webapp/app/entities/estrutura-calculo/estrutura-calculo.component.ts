import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEstruturaCalculo } from 'app/shared/model/estrutura-calculo.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { EstruturaCalculoService } from './estrutura-calculo.service';
import { EstruturaCalculoDeleteDialogComponent } from './estrutura-calculo-delete-dialog.component';
import { IMoeda } from 'app/shared/model/moeda.model';
import { MoedaService } from '../moeda/moeda.service';

@Component({
  selector: 'rv-estrutura-calculo',
  templateUrl: './estrutura-calculo.component.html'
})
export class EstruturaCalculoComponent implements OnInit, OnDestroy {
  estruturaCalculos: IEstruturaCalculo[];
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
  moedaNacional: IMoeda;
  produtoId = 0;

  constructor(
    protected estruturaCalculoService: EstruturaCalculoService,
    protected parseLinks: JhiParseLinks,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected moedaService: MoedaService
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
    this.activatedRoute.queryParams.subscribe(parmas => {
      this.produtoId = parmas.produtoId;
      this.estruturaCalculoService
        .query({
          page: this.page - 1,
          size: this.itemsPerPage,
          sort: this.sort()
        })
        .subscribe((res: HttpResponse<IEstruturaCalculo[]>) =>
          this.paginateEstruturaCalculos(res.body.filter(e => e.produtoId == this.produtoId), res.headers)
        );
    });
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/estrutura-calculo'], {
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
      '/estrutura-calculo',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInEstruturaCalculos();
    this.moedaService.query().subscribe(moedaResult => {
      this.moedaNacional = moedaResult.body.filter(m => m.nacional === true).shift();
    });
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IEstruturaCalculo) {
    return item.id;
  }

  registerChangeInEstruturaCalculos() {
    this.eventSubscriber = this.eventManager.subscribe('estruturaCalculoListModification', () => this.loadAll());
  }

  delete(estruturaCalculo: IEstruturaCalculo) {
    const modalRef = this.modalService.open(EstruturaCalculoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.estruturaCalculo = estruturaCalculo;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateEstruturaCalculos(data: IEstruturaCalculo[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.estruturaCalculos = data;
  }
}
