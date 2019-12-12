import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IContactoEmpresa } from 'app/shared/model/contacto-empresa.model';

@Component({
  selector: 'rv-contacto-empresa-detail',
  templateUrl: './contacto-empresa-detail.component.html'
})
export class ContactoEmpresaDetailComponent implements OnInit {
  contactoEmpresa: IContactoEmpresa;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ contactoEmpresa }) => {
      this.contactoEmpresa = contactoEmpresa;
    });
  }

  previousState() {
    window.history.back();
  }
}
