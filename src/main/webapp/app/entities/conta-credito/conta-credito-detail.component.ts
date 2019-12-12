import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IContaCredito } from 'app/shared/model/conta-credito.model';

@Component({
  selector: 'rv-conta-credito-detail',
  templateUrl: './conta-credito-detail.component.html'
})
export class ContaCreditoDetailComponent implements OnInit {
  contaCredito: IContaCredito;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ contaCredito }) => {
      this.contaCredito = contaCredito;
    });
  }

  previousState() {
    window.history.back();
  }
}
