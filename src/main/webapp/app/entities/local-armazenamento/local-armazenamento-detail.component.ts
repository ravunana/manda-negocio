import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ILocalArmazenamento } from 'app/shared/model/local-armazenamento.model';

@Component({
  selector: 'rv-local-armazenamento-detail',
  templateUrl: './local-armazenamento-detail.component.html'
})
export class LocalArmazenamentoDetailComponent implements OnInit {
  localArmazenamento: ILocalArmazenamento;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ localArmazenamento }) => {
      this.localArmazenamento = localArmazenamento;
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
