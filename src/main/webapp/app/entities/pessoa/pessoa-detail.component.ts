import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IPessoa } from 'app/shared/model/pessoa.model';
import { IContactoPessoa } from 'app/shared/model/contacto-pessoa.model';
import { IMoradaPessoa } from 'app/shared/model/morada-pessoa.model';
import { ContactoPessoaService } from '../contacto-pessoa/contacto-pessoa.service';
import { MoradaPessoaService } from '../morada-pessoa/morada-pessoa.service';

@Component({
  selector: 'rv-pessoa-detail',
  templateUrl: './pessoa-detail.component.html'
})
export class PessoaDetailComponent implements OnInit {
  pessoa: IPessoa;
  contactos: IContactoPessoa[];
  moradas: IMoradaPessoa[];

  constructor(
    protected dataUtils: JhiDataUtils,
    protected activatedRoute: ActivatedRoute,
    protected contactoService: ContactoPessoaService,
    protected moradaService: MoradaPessoaService
  ) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ pessoa }) => {
      this.pessoa = pessoa;
    });
    this.contactoService.query().subscribe(contactoResult => {
      this.contactos = contactoResult.body.filter(c => c.pessoaId === this.pessoa.id);
    });
    this.moradaService.query().subscribe(mordaResult => {
      this.moradas = mordaResult.body.filter(m => m.pessoaId === this.pessoa.id);
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
