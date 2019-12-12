import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IContactoEmpresa } from 'app/shared/model/contacto-empresa.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ContactoEmpresaService } from './contacto-empresa.service';
import { ContactoEmpresaDeleteDialogComponent } from './contacto-empresa-delete-dialog.component';

@Component({
  selector: 'rv-contacto-empresa',
  templateUrl: './contacto-empresa.component.html'
})
export class ContactoEmpresaComponent implements OnInit, OnDestroy {
  contactoEmpresas: IContactoEmpresa[];
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
    protected contactoEmpresaService: ContactoEmpresaService,
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
    this.contactoEmpresaService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IContactoEmpresa[]>) => this.paginateContactoEmpresas(res.body, res.headers));
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/contacto-empresa'], {
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
      '/contacto-empresa',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInContactoEmpresas();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IContactoEmpresa) {
    return item.id;
  }

  registerChangeInContactoEmpresas() {
    this.eventSubscriber = this.eventManager.subscribe('contactoEmpresaListModification', () => this.loadAll());
  }

  delete(contactoEmpresa: IContactoEmpresa) {
    const modalRef = this.modalService.open(ContactoEmpresaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.contactoEmpresa = contactoEmpresa;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateContactoEmpresas(data: IContactoEmpresa[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.contactoEmpresas = data;
  }
}
