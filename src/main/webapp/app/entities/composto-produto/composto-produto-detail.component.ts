import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICompostoProduto } from 'app/shared/model/composto-produto.model';

@Component({
  selector: 'rv-composto-produto-detail',
  templateUrl: './composto-produto-detail.component.html'
})
export class CompostoProdutoDetailComponent implements OnInit {
  compostoProduto: ICompostoProduto;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ compostoProduto }) => {
      this.compostoProduto = compostoProduto;
    });
  }

  previousState() {
    window.history.back();
  }
}
