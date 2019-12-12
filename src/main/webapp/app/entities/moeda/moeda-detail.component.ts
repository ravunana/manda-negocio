import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMoeda } from 'app/shared/model/moeda.model';

@Component({
  selector: 'rv-moeda-detail',
  templateUrl: './moeda-detail.component.html'
})
export class MoedaDetailComponent implements OnInit {
  moeda: IMoeda;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ moeda }) => {
      this.moeda = moeda;
    });
  }

  previousState() {
    window.history.back();
  }
}
