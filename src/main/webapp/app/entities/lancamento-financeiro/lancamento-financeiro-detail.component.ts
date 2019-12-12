import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ILancamentoFinanceiro } from 'app/shared/model/lancamento-financeiro.model';

@Component({
  selector: 'rv-lancamento-financeiro-detail',
  templateUrl: './lancamento-financeiro-detail.component.html'
})
export class LancamentoFinanceiroDetailComponent implements OnInit {
  lancamentoFinanceiro: ILancamentoFinanceiro;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ lancamentoFinanceiro }) => {
      this.lancamentoFinanceiro = lancamentoFinanceiro;
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
