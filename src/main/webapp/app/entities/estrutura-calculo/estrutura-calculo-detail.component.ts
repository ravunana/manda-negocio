import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEstruturaCalculo } from 'app/shared/model/estrutura-calculo.model';

@Component({
  selector: 'rv-estrutura-calculo-detail',
  templateUrl: './estrutura-calculo-detail.component.html'
})
export class EstruturaCalculoDetailComponent implements OnInit {
  estruturaCalculo: IEstruturaCalculo;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ estruturaCalculo }) => {
      this.estruturaCalculo = estruturaCalculo;
    });
  }

  previousState() {
    window.history.back();
  }
}
