import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAuditoriaCompra } from 'app/shared/model/auditoria-compra.model';

@Component({
  selector: 'rv-auditoria-compra-detail',
  templateUrl: './auditoria-compra-detail.component.html'
})
export class AuditoriaCompraDetailComponent implements OnInit {
  auditoriaCompra: IAuditoriaCompra;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ auditoriaCompra }) => {
      this.auditoriaCompra = auditoriaCompra;
    });
  }

  previousState() {
    window.history.back();
  }
}
