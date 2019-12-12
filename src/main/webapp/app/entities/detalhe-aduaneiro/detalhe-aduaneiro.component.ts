import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDetalheAduaneiro } from 'app/shared/model/detalhe-aduaneiro.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { DetalheAduaneiroService } from './detalhe-aduaneiro.service';
import { DetalheAduaneiroDeleteDialogComponent } from './detalhe-aduaneiro-delete-dialog.component';

@Component({
  selector: 'rv-detalhe-aduaneiro',
  templateUrl: './detalhe-aduaneiro.component.html'
})
export class DetalheAduaneiroComponent implements OnInit, OnDestroy {
  detalheAduaneiros: IDetalheAduaneiro[];
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
    protected detalheAduaneiroService: DetalheAduaneiroService,
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
    this.detalheAduaneiroService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IDetalheAduaneiro[]>) => this.paginateDetalheAduaneiros(res.body, res.headers));
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/detalhe-aduaneiro'], {
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
      '/detalhe-aduaneiro',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInDetalheAduaneiros();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IDetalheAduaneiro) {
    return item.id;
  }

  registerChangeInDetalheAduaneiros() {
    this.eventSubscriber = this.eventManager.subscribe('detalheAduaneiroListModification', () => this.loadAll());
  }

  delete(detalheAduaneiro: IDetalheAduaneiro) {
    const modalRef = this.modalService.open(DetalheAduaneiroDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.detalheAduaneiro = detalheAduaneiro;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateDetalheAduaneiros(data: IDetalheAduaneiro[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.detalheAduaneiros = data;
  }
}
