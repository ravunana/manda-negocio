import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ICliente } from 'app/shared/model/cliente.model';

@Component({
  selector: 'rv-cliente-detail',
  templateUrl: './cliente-detail.component.html'
})
export class ClienteDetailComponent implements OnInit {
  cliente: ICliente;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ cliente }) => {
      this.cliente = cliente;
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }
  previousState() {
    window.history.back();
  }
}
