import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IContactoPessoa } from 'app/shared/model/contacto-pessoa.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ContactoPessoaService } from './contacto-pessoa.service';
import { ContactoPessoaDeleteDialogComponent } from './contacto-pessoa-delete-dialog.component';

@Component({
  selector: 'rv-contacto-pessoa',
  templateUrl: './contacto-pessoa.component.html'
})
export class ContactoPessoaComponent implements OnInit, OnDestroy {
  contactoPessoas: IContactoPessoa[];
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
    protected contactoPessoaService: ContactoPessoaService,
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
    this.contactoPessoaService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IContactoPessoa[]>) => this.paginateContactoPessoas(res.body, res.headers));
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/contacto-pessoa'], {
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
      '/contacto-pessoa',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInContactoPessoas();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IContactoPessoa) {
    return item.id;
  }

  registerChangeInContactoPessoas() {
    this.eventSubscriber = this.eventManager.subscribe('contactoPessoaListModification', () => this.loadAll());
  }

  delete(contactoPessoa: IContactoPessoa) {
    const modalRef = this.modalService.open(ContactoPessoaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.contactoPessoa = contactoPessoa;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateContactoPessoas(data: IContactoPessoa[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.contactoPessoas = data;
  }
}
