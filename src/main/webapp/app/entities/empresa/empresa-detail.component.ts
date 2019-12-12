import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IEmpresa } from 'app/shared/model/empresa.model';

@Component({
  selector: 'rv-empresa-detail',
  templateUrl: './empresa-detail.component.html'
})
export class EmpresaDetailComponent implements OnInit {
  empresa: IEmpresa;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ empresa }) => {
      this.empresa = empresa;
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
