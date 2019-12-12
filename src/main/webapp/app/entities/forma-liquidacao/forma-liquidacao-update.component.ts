import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IFormaLiquidacao, FormaLiquidacao } from 'app/shared/model/forma-liquidacao.model';
import { FormaLiquidacaoService } from './forma-liquidacao.service';

@Component({
  selector: 'rv-forma-liquidacao-update',
  templateUrl: './forma-liquidacao-update.component.html'
})
export class FormaLiquidacaoUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required]],
    juro: [null, [Validators.required, Validators.min(0), Validators.max(100)]],
    prazoLiquidacao: [null, [Validators.required, Validators.min(0)]],
    quantidade: [null, [Validators.required, Validators.min(1)]],
    icon: []
  });

  constructor(
    protected formaLiquidacaoService: FormaLiquidacaoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ formaLiquidacao }) => {
      this.updateForm(formaLiquidacao);
    });
  }

  updateForm(formaLiquidacao: IFormaLiquidacao) {
    this.editForm.patchValue({
      id: formaLiquidacao.id,
      nome: formaLiquidacao.nome,
      juro: formaLiquidacao.juro,
      prazoLiquidacao: formaLiquidacao.prazoLiquidacao,
      quantidade: formaLiquidacao.quantidade,
      icon: formaLiquidacao.icon
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const formaLiquidacao = this.createFromForm();
    if (formaLiquidacao.id !== undefined) {
      this.subscribeToSaveResponse(this.formaLiquidacaoService.update(formaLiquidacao));
    } else {
      this.subscribeToSaveResponse(this.formaLiquidacaoService.create(formaLiquidacao));
    }
  }

  private createFromForm(): IFormaLiquidacao {
    return {
      ...new FormaLiquidacao(),
      id: this.editForm.get(['id']).value,
      nome: this.editForm.get(['nome']).value,
      juro: this.editForm.get(['juro']).value,
      prazoLiquidacao: this.editForm.get(['prazoLiquidacao']).value,
      quantidade: this.editForm.get(['quantidade']).value,
      icon: this.editForm.get(['icon']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFormaLiquidacao>>) {
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
