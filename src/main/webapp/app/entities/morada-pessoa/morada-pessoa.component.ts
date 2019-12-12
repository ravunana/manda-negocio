import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IMoradaPessoa } from 'app/shared/model/morada-pessoa.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { MoradaPessoaService } from './morada-pessoa.service';
import { MoradaPessoaDeleteDialogComponent } from './morada-pessoa-delete-dialog.component';

@Component({
  selector: 'rv-morada-pessoa',
  templateUrl: './morada-pessoa.component.html'
})
export class MoradaPessoaComponent implements OnInit, OnDestroy {
  moradaPessoas: IMoradaPessoa[];
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
    protected moradaPessoaService: MoradaPessoaService,
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
    this.moradaPessoaService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IMoradaPessoa[]>) => this.paginateMoradaPessoas(res.body, res.headers));
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/morada-pessoa'], {
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
      '/morada-pessoa',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInMoradaPessoas();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IMoradaPessoa) {
    return item.id;
  }

  registerChangeInMoradaPessoas() {
    this.eventSubscriber = this.eventManager.subscribe('moradaPessoaListModification', () => this.loadAll());
  }

  delete(moradaPessoa: IMoradaPessoa) {
    const modalRef = this.modalService.open(MoradaPessoaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.moradaPessoa = moradaPessoa;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateMoradaPessoas(data: IMoradaPessoa[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.moradaPessoas = data;
  }
}
