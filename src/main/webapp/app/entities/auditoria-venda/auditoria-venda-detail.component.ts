import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAuditoriaVenda } from 'app/shared/model/auditoria-venda.model';

@Component({
  selector: 'rv-auditoria-venda-detail',
  templateUrl: './auditoria-venda-detail.component.html'
})
export class AuditoriaVendaDetailComponent implements OnInit {
  auditoriaVenda: IAuditoriaVenda;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ auditoriaVenda }) => {
      this.auditoriaVenda = auditoriaVenda;
    });
  }

  previousState() {
    window.history.back();
  }
}
