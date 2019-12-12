import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IArquivo } from 'app/shared/model/arquivo.model';

@Component({
  selector: 'rv-arquivo-detail',
  templateUrl: './arquivo-detail.component.html'
})
export class ArquivoDetailComponent implements OnInit {
  arquivo: IArquivo;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ arquivo }) => {
      this.arquivo = arquivo;
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
