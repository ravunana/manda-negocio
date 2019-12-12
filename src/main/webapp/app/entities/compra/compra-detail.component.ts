import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ICompra } from 'app/shared/model/compra.model';

@Component({
  selector: 'rv-compra-detail',
  templateUrl: './compra-detail.component.html'
})
export class CompraDetailComponent implements OnInit {
  compra: ICompra;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ compra }) => {
      this.compra = compra;
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }
  previousState() {
    window.history.back();
  }
}
