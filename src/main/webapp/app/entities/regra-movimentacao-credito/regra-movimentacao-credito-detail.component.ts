import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IRegraMovimentacaoCredito } from 'app/shared/model/regra-movimentacao-credito.model';

@Component({
  selector: 'rv-regra-movimentacao-credito-detail',
  templateUrl: './regra-movimentacao-credito-detail.component.html'
})
export class RegraMovimentacaoCreditoDetailComponent implements OnInit {
  regraMovimentacaoCredito: IRegraMovimentacaoCredito;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ regraMovimentacaoCredito }) => {
      this.regraMovimentacaoCredito = regraMovimentacaoCredito;
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
