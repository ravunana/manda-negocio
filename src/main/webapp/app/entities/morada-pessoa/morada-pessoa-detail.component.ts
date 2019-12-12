import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMoradaPessoa } from 'app/shared/model/morada-pessoa.model';

@Component({
  selector: 'rv-morada-pessoa-detail',
  templateUrl: './morada-pessoa-detail.component.html'
})
export class MoradaPessoaDetailComponent implements OnInit {
  moradaPessoa: IMoradaPessoa;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ moradaPessoa }) => {
      this.moradaPessoa = moradaPessoa;
    });
  }

  previousState() {
    window.history.back();
  }
}
