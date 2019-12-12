import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IClasseConta } from 'app/shared/model/classe-conta.model';

@Component({
  selector: 'rv-classe-conta-detail',
  templateUrl: './classe-conta-detail.component.html'
})
export class ClasseContaDetailComponent implements OnInit {
  classeConta: IClasseConta;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ classeConta }) => {
      this.classeConta = classeConta;
    });
  }

  previousState() {
    window.history.back();
  }
}
