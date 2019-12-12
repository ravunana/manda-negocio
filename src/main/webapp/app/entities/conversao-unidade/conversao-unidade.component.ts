import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IConversaoUnidade } from 'app/shared/model/conversao-unidade.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ConversaoUnidadeService } from './conversao-unidade.service';
import { ConversaoUnidadeDeleteDialogComponent } from './conversao-unidade-delete-dialog.component';

@Component({
  selector: 'rv-conversao-unidade',
  templateUrl: './conversao-unidade.component.html'
})
export class ConversaoUnidadeComponent implements OnInit, OnDestroy {
  conversaoUnidades: IConversaoUnidade[];
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
    protected conversaoUnidadeService: ConversaoUnidadeService,
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
    this.conversaoUnidadeService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IConversaoUnidade[]>) => this.paginateConversaoUnidades(res.body, res.headers));
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/conversao-unidade'], {
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
      '/conversao-unidade',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInConversaoUnidades();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IConversaoUnidade) {
    return item.id;
  }

  registerChangeInConversaoUnidades() {
    this.eventSubscriber = this.eventManager.subscribe('conversaoUnidadeListModification', () => this.loadAll());
  }

  delete(conversaoUnidade: IConversaoUnidade) {
    const modalRef = this.modalService.open(ConversaoUnidadeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.conversaoUnidade = conversaoUnidade;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateConversaoUnidades(data: IConversaoUnidade[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.conversaoUnidades = data;
  }
}
