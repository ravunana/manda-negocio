import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMeioLiquidacao } from 'app/shared/model/meio-liquidacao.model';

@Component({
  selector: 'rv-meio-liquidacao-detail',
  templateUrl: './meio-liquidacao-detail.component.html'
})
export class MeioLiquidacaoDetailComponent implements OnInit {
  meioLiquidacao: IMeioLiquidacao;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ meioLiquidacao }) => {
      this.meioLiquidacao = meioLiquidacao;
    });
  }

  previousState() {
    window.history.back();
  }
}
