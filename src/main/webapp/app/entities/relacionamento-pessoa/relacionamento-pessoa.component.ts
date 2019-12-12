import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IRelacionamentoPessoa } from 'app/shared/model/relacionamento-pessoa.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { RelacionamentoPessoaService } from './relacionamento-pessoa.service';
import { RelacionamentoPessoaDeleteDialogComponent } from './relacionamento-pessoa-delete-dialog.component';

@Component({
  selector: 'rv-relacionamento-pessoa',
  templateUrl: './relacionamento-pessoa.component.html'
})
export class RelacionamentoPessoaComponent implements OnInit, OnDestroy {
  relacionamentoPessoas: IRelacionamentoPessoa[];
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
    protected relacionamentoPessoaService: RelacionamentoPessoaService,
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
    this.relacionamentoPessoaService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IRelacionamentoPessoa[]>) => this.paginateRelacionamentoPessoas(res.body, res.headers));
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/relacionamento-pessoa'], {
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
      '/relacionamento-pessoa',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInRelacionamentoPessoas();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IRelacionamentoPessoa) {
    return item.id;
  }

  registerChangeInRelacionamentoPessoas() {
    this.eventSubscriber = this.eventManager.subscribe('relacionamentoPessoaListModification', () => this.loadAll());
  }

  delete(relacionamentoPessoa: IRelacionamentoPessoa) {
    const modalRef = this.modalService.open(RelacionamentoPessoaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.relacionamentoPessoa = relacionamentoPessoa;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateRelacionamentoPessoas(data: IRelacionamentoPessoa[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.relacionamentoPessoas = data;
  }
}
