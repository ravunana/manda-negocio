import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IItemVenda } from 'app/shared/model/item-venda.model';

@Component({
  selector: 'rv-item-venda-detail',
  templateUrl: './item-venda-detail.component.html'
})
export class ItemVendaDetailComponent implements OnInit {
  itemVenda: IItemVenda;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ itemVenda }) => {
      this.itemVenda = itemVenda;
    });
  }

  previousState() {
    window.history.back();
  }
}
