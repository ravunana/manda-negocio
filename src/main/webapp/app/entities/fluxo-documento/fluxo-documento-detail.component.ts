import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IFluxoDocumento } from 'app/shared/model/fluxo-documento.model';

@Component({
  selector: 'rv-fluxo-documento-detail',
  templateUrl: './fluxo-documento-detail.component.html'
})
export class FluxoDocumentoDetailComponent implements OnInit {
  fluxoDocumento: IFluxoDocumento;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ fluxoDocumento }) => {
      this.fluxoDocumento = fluxoDocumento;
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
