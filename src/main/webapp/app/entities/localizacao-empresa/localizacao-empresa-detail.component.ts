import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILocalizacaoEmpresa } from 'app/shared/model/localizacao-empresa.model';

@Component({
  selector: 'rv-localizacao-empresa-detail',
  templateUrl: './localizacao-empresa-detail.component.html'
})
export class LocalizacaoEmpresaDetailComponent implements OnInit {
  localizacaoEmpresa: ILocalizacaoEmpresa;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ localizacaoEmpresa }) => {
      this.localizacaoEmpresa = localizacaoEmpresa;
    });
  }

  previousState() {
    window.history.back();
  }
}
