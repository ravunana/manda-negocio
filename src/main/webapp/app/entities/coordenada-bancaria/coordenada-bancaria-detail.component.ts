import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICoordenadaBancaria } from 'app/shared/model/coordenada-bancaria.model';

@Component({
  selector: 'rv-coordenada-bancaria-detail',
  templateUrl: './coordenada-bancaria-detail.component.html'
})
export class CoordenadaBancariaDetailComponent implements OnInit {
  coordenadaBancaria: ICoordenadaBancaria;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ coordenadaBancaria }) => {
      this.coordenadaBancaria = coordenadaBancaria;
    });
  }

  previousState() {
    window.history.back();
  }
}
