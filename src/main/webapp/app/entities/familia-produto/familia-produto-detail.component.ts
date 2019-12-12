import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IFamiliaProduto } from 'app/shared/model/familia-produto.model';

@Component({
  selector: 'rv-familia-produto-detail',
  templateUrl: './familia-produto-detail.component.html'
})
export class FamiliaProdutoDetailComponent implements OnInit {
  familiaProduto: IFamiliaProduto;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ familiaProduto }) => {
      this.familiaProduto = familiaProduto;
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
