import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IEscrituracaoContabil } from 'app/shared/model/escrituracao-contabil.model';
import { IContaDebito } from 'app/shared/model/conta-debito.model';
import { IContaCredito } from 'app/shared/model/conta-credito.model';
import { ContaCreditoService } from '../conta-credito/conta-credito.service';
import { ContaDebitoService } from '../conta-debito/conta-debito.service';

@Component({
  selector: 'rv-escrituracao-contabil-detail',
  templateUrl: './escrituracao-contabil-detail.component.html'
})
export class EscrituracaoContabilDetailComponent implements OnInit {
  escrituracaoContabil: IEscrituracaoContabil;
  debitos: IContaDebito[];
  creditos: IContaCredito[];

  constructor(
    protected dataUtils: JhiDataUtils,
    protected activatedRoute: ActivatedRoute,
    protected creditoService: ContaCreditoService,
    protected debitoService: ContaDebitoService
  ) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ escrituracaoContabil }) => {
      this.escrituracaoContabil = escrituracaoContabil;
    });

    this.creditoService.query().subscribe(data => {
      this.creditos = data.body.filter(c => c.lancamentoCreditoId === this.escrituracaoContabil.id);
    });

    this.debitoService.query().subscribe(data => {
      this.debitos = data.body.filter(d => d.lancamentoDebitoId === this.escrituracaoContabil.id);
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
