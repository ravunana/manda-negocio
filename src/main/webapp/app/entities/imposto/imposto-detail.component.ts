import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IImposto } from 'app/shared/model/imposto.model';

@Component({
  selector: 'rv-imposto-detail',
  templateUrl: './imposto-detail.component.html'
})
export class ImpostoDetailComponent implements OnInit {
  imposto: IImposto;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ imposto }) => {
      this.imposto = imposto;
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
