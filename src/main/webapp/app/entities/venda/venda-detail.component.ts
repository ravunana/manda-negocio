import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IVenda } from 'app/shared/model/venda.model';

@Component({
  selector: 'rv-venda-detail',
  templateUrl: './venda-detail.component.html'
})
export class VendaDetailComponent implements OnInit {
  venda: IVenda;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ venda }) => {
      this.venda = venda;
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
