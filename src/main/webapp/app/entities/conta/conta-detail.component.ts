import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IConta } from 'app/shared/model/conta.model';

@Component({
  selector: 'rv-conta-detail',
  templateUrl: './conta-detail.component.html'
})
export class ContaDetailComponent implements OnInit {
  conta: IConta;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ conta }) => {
      this.conta = conta;
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
