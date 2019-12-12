import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IRelacionamentoPessoa } from 'app/shared/model/relacionamento-pessoa.model';

@Component({
  selector: 'rv-relacionamento-pessoa-detail',
  templateUrl: './relacionamento-pessoa-detail.component.html'
})
export class RelacionamentoPessoaDetailComponent implements OnInit {
  relacionamentoPessoa: IRelacionamentoPessoa;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ relacionamentoPessoa }) => {
      this.relacionamentoPessoa = relacionamentoPessoa;
    });
  }

  previousState() {
    window.history.back();
  }
}
