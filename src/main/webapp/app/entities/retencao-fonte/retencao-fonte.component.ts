import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IRetencaoFonte } from 'app/shared/model/retencao-fonte.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { RetencaoFonteService } from './retencao-fonte.service';
import { RetencaoFonteDeleteDialogComponent } from './retencao-fonte-delete-dialog.component';

@Component({
  selector: 'rv-retencao-fonte',
  templateUrl: './retencao-fonte.component.html'
})
export class RetencaoFonteComponent implements OnInit, OnDestroy {
  retencaoFontes: IRetencaoFonte[];
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
    protected retencaoFonteService: RetencaoFonteService,
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
    this.retencaoFonteService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IRetencaoFonte[]>) => this.paginateRetencaoFontes(res.body, res.headers));
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/retencao-fonte'], {
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
      '/retencao-fonte',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInRetencaoFontes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IRetencaoFonte) {
    return item.id;
  }

  registerChangeInRetencaoFontes() {
    this.eventSubscriber = this.eventManager.subscribe('retencaoFonteListModification', () => this.loadAll());
  }

  delete(retencaoFonte: IRetencaoFonte) {
    const modalRef = this.modalService.open(RetencaoFonteDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.retencaoFonte = retencaoFonte;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateRetencaoFontes(data: IRetencaoFonte[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.retencaoFontes = data;
  }
}
