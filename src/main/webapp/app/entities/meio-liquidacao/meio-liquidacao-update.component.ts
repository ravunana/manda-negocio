import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IMeioLiquidacao, MeioLiquidacao } from 'app/shared/model/meio-liquidacao.model';
import { MeioLiquidacaoService } from './meio-liquidacao.service';

@Component({
  selector: 'rv-meio-liquidacao-update',
  templateUrl: './meio-liquidacao-update.component.html'
})
export class MeioLiquidacaoUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nome: [null, []],
    sigla: [null, []],
    icon: [],
    mostrarPontoVenda: []
  });

  constructor(protected meioLiquidacaoService: MeioLiquidacaoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ meioLiquidacao }) => {
      this.updateForm(meioLiquidacao);
    });
  }

  updateForm(meioLiquidacao: IMeioLiquidacao) {
    this.editForm.patchValue({
      id: meioLiquidacao.id,
      nome: meioLiquidacao.nome,
      sigla: meioLiquidacao.sigla,
      icon: meioLiquidacao.icon,
      mostrarPontoVenda: meioLiquidacao.mostrarPontoVenda
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const meioLiquidacao = this.createFromForm();
    if (meioLiquidacao.id !== undefined) {
      this.subscribeToSaveResponse(this.meioLiquidacaoService.update(meioLiquidacao));
    } else {
      this.subscribeToSaveResponse(this.meioLiquidacaoService.create(meioLiquidacao));
    }
  }

  private createFromForm(): IMeioLiquidacao {
    return {
      ...new MeioLiquidacao(),
      id: this.editForm.get(['id']).value,
      nome: this.editForm.get(['nome']).value,
      sigla: this.editForm.get(['sigla']).value,
      icon: this.editForm.get(['icon']).value,
      mostrarPontoVenda: this.editForm.get(['mostrarPontoVenda']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMeioLiquidacao>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
