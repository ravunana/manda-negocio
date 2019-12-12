import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IDocumentoComercial } from 'app/shared/model/documento-comercial.model';

@Component({
  selector: 'rv-documento-comercial-detail',
  templateUrl: './documento-comercial-detail.component.html'
})
export class DocumentoComercialDetailComponent implements OnInit {
  documentoComercial: IDocumentoComercial;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ documentoComercial }) => {
      this.documentoComercial = documentoComercial;
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
