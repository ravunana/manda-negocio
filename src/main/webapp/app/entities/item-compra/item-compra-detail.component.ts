import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IItemCompra } from 'app/shared/model/item-compra.model';

@Component({
  selector: 'rv-item-compra-detail',
  templateUrl: './item-compra-detail.component.html'
})
export class ItemCompraDetailComponent implements OnInit {
  itemCompra: IItemCompra;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ itemCompra }) => {
      this.itemCompra = itemCompra;
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
