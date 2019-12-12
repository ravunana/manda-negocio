import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDetalheAduaneiro } from 'app/shared/model/detalhe-aduaneiro.model';

@Component({
  selector: 'rv-detalhe-aduaneiro-detail',
  templateUrl: './detalhe-aduaneiro-detail.component.html'
})
export class DetalheAduaneiroDetailComponent implements OnInit {
  detalheAduaneiro: IDetalheAduaneiro;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ detalheAduaneiro }) => {
      this.detalheAduaneiro = detalheAduaneiro;
    });
  }

  previousState() {
    window.history.back();
  }
}
