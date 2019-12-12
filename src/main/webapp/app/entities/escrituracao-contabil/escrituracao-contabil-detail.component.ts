import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IEscrituracaoContabil } from 'app/shared/model/escrituracao-contabil.model';

@Component({
  selector: 'rv-escrituracao-contabil-detail',
  templateUrl: './escrituracao-contabil-detail.component.html'
})
export class EscrituracaoContabilDetailComponent implements OnInit {
  escrituracaoContabil: IEscrituracaoContabil;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ escrituracaoContabil }) => {
      this.escrituracaoContabil = escrituracaoContabil;
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
