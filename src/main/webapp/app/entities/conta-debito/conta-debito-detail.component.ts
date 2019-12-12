import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IContaDebito } from 'app/shared/model/conta-debito.model';

@Component({
  selector: 'rv-conta-debito-detail',
  templateUrl: './conta-debito-detail.component.html'
})
export class ContaDebitoDetailComponent implements OnInit {
  contaDebito: IContaDebito;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ contaDebito }) => {
      this.contaDebito = contaDebito;
    });
  }

  previousState() {
    window.history.back();
  }
}
