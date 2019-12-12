import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVariacaoProduto } from 'app/shared/model/variacao-produto.model';

@Component({
  selector: 'rv-variacao-produto-detail',
  templateUrl: './variacao-produto-detail.component.html'
})
export class VariacaoProdutoDetailComponent implements OnInit {
  variacaoProduto: IVariacaoProduto;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ variacaoProduto }) => {
      this.variacaoProduto = variacaoProduto;
    });
  }

  previousState() {
    window.history.back();
  }
}
