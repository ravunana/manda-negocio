import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IConversaoUnidade } from 'app/shared/model/conversao-unidade.model';

@Component({
  selector: 'rv-conversao-unidade-detail',
  templateUrl: './conversao-unidade-detail.component.html'
})
export class ConversaoUnidadeDetailComponent implements OnInit {
  conversaoUnidade: IConversaoUnidade;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ conversaoUnidade }) => {
      this.conversaoUnidade = conversaoUnidade;
    });
  }

  previousState() {
    window.history.back();
  }
}
