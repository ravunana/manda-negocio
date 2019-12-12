import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { ICliente, Cliente } from 'app/shared/model/cliente.model';
import { ClienteService } from './cliente.service';
import { IPessoa } from 'app/shared/model/pessoa.model';
import { PessoaService } from 'app/entities/pessoa/pessoa.service';
import { IConta } from 'app/shared/model/conta.model';
import { ContaService } from 'app/entities/conta/conta.service';

@Component({
  selector: 'rv-cliente-update',
  templateUrl: './cliente-update.component.html'
})
export class ClienteUpdateComponent implements OnInit {
  isSaving: boolean;

  pessoas: IPessoa[];

  contas: IConta[];

  editForm = this.fb.group({
    id: [],
    ativo: [],
    perfilProfissional: [],
    satisfacao: [],
    frequencia: [],
    canalUsado: [],
    numero: [null, [Validators.required]],
    autofacturacao: [],
    pessoaId: [null, Validators.required],
    contaId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected clienteService: ClienteService,
    protected pessoaService: PessoaService,
    protected contaService: ContaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ cliente }) => {
      this.updateForm(cliente);
    });
    this.pessoaService.query({ 'clienteId.specified': 'false' }).subscribe(
      (res: HttpResponse<IPessoa[]>) => {
        if (!this.editForm.get('pessoaId').value) {
          this.pessoas = res.body;
        } else {
          this.pessoaService
            .find(this.editForm.get('pessoaId').value)
            .subscribe(
              (subRes: HttpResponse<IPessoa>) => (this.pessoas = [subRes.body].concat(res.body)),
              (subRes: HttpErrorResponse) => this.onError(subRes.message)
            );
        }
      },
      (res: HttpErrorResponse) => this.onError(res.message)
    );
    this.contaService
      .query()
      .subscribe((res: HttpResponse<IConta[]>) => (this.contas = res.body), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(cliente: ICliente) {
    this.editForm.patchValue({
      id: cliente.id,
      ativo: cliente.ativo,
      perfilProfissional: cliente.perfilProfissional,
      satisfacao: cliente.satisfacao,
      frequencia: cliente.frequencia,
      canalUsado: cliente.canalUsado,
      numero: cliente.numero,
      autofacturacao: cliente.autofacturacao,
      pessoaId: cliente.pessoaId,
      contaId: cliente.contaId
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  setFileData(event, field: string, isImage) {
    return new Promise((resolve, reject) => {
      if (event && event.target && event.target.files && event.target.files[0]) {
        const file: File = event.target.files[0];
        if (isImage && !file.type.startsWith('image/')) {
          reject(`File was expected to be an image but was found to be ${file.type}`);
        } else {
          const filedContentType: string = field + 'ContentType';
          this.dataUtils.toBase64(file, base64Data => {
            this.editForm.patchValue({
              [field]: base64Data,
              [filedContentType]: file.type
            });
          });
        }
      } else {
        reject(`Base64 data was not set as file could not be extracted from passed parameter: ${event}`);
      }
    }).then(
      // eslint-disable-next-line no-console
      () => console.log('blob added'), // success
      this.onError
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const cliente = this.createFromForm();
    if (cliente.id !== undefined) {
      this.subscribeToSaveResponse(this.clienteService.update(cliente));
    } else {
      this.subscribeToSaveResponse(this.clienteService.create(cliente));
    }
  }

  private createFromForm(): ICliente {
    return {
      ...new Cliente(),
      id: this.editForm.get(['id']).value,
      ativo: this.editForm.get(['ativo']).value,
      perfilProfissional: this.editForm.get(['perfilProfissional']).value,
      satisfacao: this.editForm.get(['satisfacao']).value,
      frequencia: this.editForm.get(['frequencia']).value,
      canalUsado: this.editForm.get(['canalUsado']).value,
      numero: this.editForm.get(['numero']).value,
      autofacturacao: this.editForm.get(['autofacturacao']).value,
      pessoaId: this.editForm.get(['pessoaId']).value,
      contaId: this.editForm.get(['contaId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICliente>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackPessoaById(index: number, item: IPessoa) {
    return item.id;
  }

  trackContaById(index: number, item: IConta) {
    return item.id;
  }
}
