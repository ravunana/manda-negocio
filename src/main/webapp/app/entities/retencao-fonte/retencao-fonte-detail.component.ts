import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRetencaoFonte } from 'app/shared/model/retencao-fonte.model';

@Component({
  selector: 'rv-retencao-fonte-detail',
  templateUrl: './retencao-fonte-detail.component.html'
})
export class RetencaoFonteDetailComponent implements OnInit {
  retencaoFonte: IRetencaoFonte;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ retencaoFonte }) => {
      this.retencaoFonte = retencaoFonte;
    });
  }

  previousState() {
    window.history.back();
  }
}
