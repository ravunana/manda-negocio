import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILicencaSoftware } from 'app/shared/model/licenca-software.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { LicencaSoftwareService } from './licenca-software.service';
import { LicencaSoftwareDeleteDialogComponent } from './licenca-software-delete-dialog.component';

@Component({
  selector: 'rv-licenca-software',
  templateUrl: './licenca-software.component.html'
})
export class LicencaSoftwareComponent implements OnInit, OnDestroy {
  licencaSoftwares: ILicencaSoftware[];
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
    protected licencaSoftwareService: LicencaSoftwareService,
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
    this.licencaSoftwareService
      .query({
        page: this.page - 1,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ILicencaSoftware[]>) => this.paginateLicencaSoftwares(res.body, res.headers));
  }

  loadPage(page: number) {
    if (page !== this.previousPage) {
      this.previousPage = page;
      this.transition();
    }
  }

  transition() {
    this.router.navigate(['/licenca-software'], {
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
      '/licenca-software',
      {
        page: this.page,
        sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
      }
    ]);
    this.loadAll();
  }

  ngOnInit() {
    this.loadAll();
    this.registerChangeInLicencaSoftwares();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ILicencaSoftware) {
    return item.id;
  }

  registerChangeInLicencaSoftwares() {
    this.eventSubscriber = this.eventManager.subscribe('licencaSoftwareListModification', () => this.loadAll());
  }

  delete(licencaSoftware: ILicencaSoftware) {
    const modalRef = this.modalService.open(LicencaSoftwareDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.licencaSoftware = licencaSoftware;
  }

  sort() {
    const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateLicencaSoftwares(data: ILicencaSoftware[], headers: HttpHeaders) {
    this.links = this.parseLinks.parse(headers.get('link'));
    this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
    this.licencaSoftwares = data;
  }
}
