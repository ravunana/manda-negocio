import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ILancamentoFinanceiro } from 'app/shared/model/lancamento-financeiro.model';
import { IDetalheLancamento } from 'app/shared/model/detalhe-lancamento.model';
import { DetalheLancamentoService } from '../detalhe-lancamento/detalhe-lancamento.service';

@Component({
  selector: 'rv-lancamento-financeiro-detail',
  templateUrl: './lancamento-financeiro-detail.component.html'
})
export class LancamentoFinanceiroDetailComponent implements OnInit {
  lancamentoFinanceiro: ILancamentoFinanceiro;
  detalhesLancamento: IDetalheLancamento[];

  constructor(
    protected dataUtils: JhiDataUtils,
    protected activatedRoute: ActivatedRoute,
    private detalheLancamentoService: DetalheLancamentoService
  ) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ lancamentoFinanceiro }) => {
      this.lancamentoFinanceiro = lancamentoFinanceiro;
    });

    this.detalheLancamentoService.query().subscribe(data => {
      this.detalhesLancamento = data.body.filter(d => d.lancamentoFinanceiroId === this.lancamentoFinanceiro.id);
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
