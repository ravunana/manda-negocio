import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUnidadeMedida } from 'app/shared/model/unidade-medida.model';

@Component({
  selector: 'rv-unidade-medida-detail',
  templateUrl: './unidade-medida-detail.component.html'
})
export class UnidadeMedidaDetailComponent implements OnInit {
  unidadeMedida: IUnidadeMedida;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ unidadeMedida }) => {
      this.unidadeMedida = unidadeMedida;
    });
  }

  previousState() {
    window.history.back();
  }
}
