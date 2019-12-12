import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IContactoPessoa } from 'app/shared/model/contacto-pessoa.model';

@Component({
  selector: 'rv-contacto-pessoa-detail',
  templateUrl: './contacto-pessoa-detail.component.html'
})
export class ContactoPessoaDetailComponent implements OnInit {
  contactoPessoa: IContactoPessoa;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ contactoPessoa }) => {
      this.contactoPessoa = contactoPessoa;
    });
  }

  previousState() {
    window.history.back();
  }
}
