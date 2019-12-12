import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IDevolucaoVenda } from 'app/shared/model/devolucao-venda.model';

@Component({
  selector: 'rv-devolucao-venda-detail',
  templateUrl: './devolucao-venda-detail.component.html'
})
export class DevolucaoVendaDetailComponent implements OnInit {
  devolucaoVenda: IDevolucaoVenda;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ devolucaoVenda }) => {
      this.devolucaoVenda = devolucaoVenda;
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
