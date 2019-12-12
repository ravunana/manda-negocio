import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IFornecedor } from 'app/shared/model/fornecedor.model';

@Component({
  selector: 'rv-fornecedor-detail',
  templateUrl: './fornecedor-detail.component.html'
})
export class FornecedorDetailComponent implements OnInit {
  fornecedor: IFornecedor;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ fornecedor }) => {
      this.fornecedor = fornecedor;
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
