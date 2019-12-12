import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IDetalheLancamento } from 'app/shared/model/detalhe-lancamento.model';

@Component({
  selector: 'rv-detalhe-lancamento-detail',
  templateUrl: './detalhe-lancamento-detail.component.html'
})
export class DetalheLancamentoDetailComponent implements OnInit {
  detalheLancamento: IDetalheLancamento;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ detalheLancamento }) => {
      this.detalheLancamento = detalheLancamento;
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
