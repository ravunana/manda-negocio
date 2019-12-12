import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEstoqueConfig } from 'app/shared/model/estoque-config.model';

@Component({
  selector: 'rv-estoque-config-detail',
  templateUrl: './estoque-config-detail.component.html'
})
export class EstoqueConfigDetailComponent implements OnInit {
  estoqueConfig: IEstoqueConfig;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ estoqueConfig }) => {
      this.estoqueConfig = estoqueConfig;
    });
  }

  previousState() {
    window.history.back();
  }
}
