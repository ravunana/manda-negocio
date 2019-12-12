import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IGrupoTributacaoImposto } from 'app/shared/model/grupo-tributacao-imposto.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { GrupoTributacaoImpostoService } from './grupo-tributacao-imposto.service';
import { GrupoTributacaoImpostoDeleteDialogComponent } from './grupo-tributacao-imposto-delete-dialog.component';

@Component({
  selector: 'rv-grupo-tributacao-imposto',
  templateUrl: './grupo-tributacao-imposto.component.html'
})
export class GrupoTributacaoImpostoComponent implements OnInit, OnDestroy {
  grupoTributacaoImpostos: IGrupoTributacaoImposto[];
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
    protected grupoTributacaoImpostoService: GrupoTributacaoImpostoService,
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
    this.grupoTributacaoImpostoService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IGrupoTributacaoImposto[]>) => this.paginateGrupoTributacaoImpostos(res.body, res.headers));
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/grupo-tributacao-imposto'], {
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
      '/grupo-tributacao-imposto',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInGrupoTributacaoImpostos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IGrupoTributacaoImposto) {
    return item.id;
  }

  registerChangeInGrupoTributacaoImpostos() {
    this.eventSubscriber = this.eventManager.subscribe('grupoTributacaoImpostoListModification', () => this.loadAll());
  }

  delete(grupoTributacaoImposto: IGrupoTributacaoImposto) {
    const modalRef = this.modalService.open(GrupoTributacaoImpostoDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.grupoTributacaoImposto = grupoTributacaoImposto;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateGrupoTributacaoImpostos(data: IGrupoTributacaoImposto[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.grupoTributacaoImpostos = data;
  }
}
