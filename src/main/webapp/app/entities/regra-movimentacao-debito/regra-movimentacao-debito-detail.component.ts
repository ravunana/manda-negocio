import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IRegraMovimentacaoDebito } from 'app/shared/model/regra-movimentacao-debito.model';

@Component({
  selector: 'rv-regra-movimentacao-debito-detail',
  templateUrl: './regra-movimentacao-debito-detail.component.html'
})
export class RegraMovimentacaoDebitoDetailComponent implements OnInit {
  regraMovimentacaoDebito: IRegraMovimentacaoDebito;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ regraMovimentacaoDebito }) => {
      this.regraMovimentacaoDebito = regraMovimentacaoDebito;
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
