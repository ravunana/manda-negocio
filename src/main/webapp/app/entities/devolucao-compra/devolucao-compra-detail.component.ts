import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IDevolucaoCompra } from 'app/shared/model/devolucao-compra.model';

@Component({
  selector: 'rv-devolucao-compra-detail',
  templateUrl: './devolucao-compra-detail.component.html'
})
export class DevolucaoCompraDetailComponent implements OnInit {
  devolucaoCompra: IDevolucaoCompra;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ devolucaoCompra }) => {
      this.devolucaoCompra = devolucaoCompra;
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
