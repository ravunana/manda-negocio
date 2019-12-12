import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGrupoTributacaoImposto } from 'app/shared/model/grupo-tributacao-imposto.model';

@Component({
  selector: 'rv-grupo-tributacao-imposto-detail',
  templateUrl: './grupo-tributacao-imposto-detail.component.html'
})
export class GrupoTributacaoImpostoDetailComponent implements OnInit {
  grupoTributacaoImposto: IGrupoTributacaoImposto;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ grupoTributacaoImposto }) => {
      this.grupoTributacaoImposto = grupoTributacaoImposto;
    });
  }

  previousState() {
    window.history.back();
  }
}
