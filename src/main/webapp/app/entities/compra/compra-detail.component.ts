import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ICompra } from 'app/shared/model/compra.model';
import { IItemCompra } from 'app/shared/model/item-compra.model';
import { ItemCompraService } from '../item-compra/item-compra.service';
import { MoedaService } from '../moeda/moeda.service';
import { ProdutoService } from '../produto/produto.service';
import { IDetalheLancamento } from 'app/shared/model/detalhe-lancamento.model';
import { DetalheLancamentoService } from '../detalhe-lancamento/detalhe-lancamento.service';
import { LancamentoFinanceiroService } from '../lancamento-financeiro/lancamento-financeiro.service';
import { EntidadeSistema } from 'app/shared/model/enumerations/entidade-sistema.model';

@Component({
  selector: 'rv-compra-detail',
  templateUrl: './compra-detail.component.html'
})
export class CompraDetailComponent implements OnInit {
  compra: ICompra;
  items: IItemCompra[] = [];
  moedaNacional;
  pagamentos: IDetalheLancamento[] = [];

  constructor(
    protected dataUtils: JhiDataUtils,
    protected activatedRoute: ActivatedRoute,
    protected itemService: ItemCompraService,
    protected moedaService: MoedaService,
    public produtoService: ProdutoService,
    protected detalheLancamentoService: DetalheLancamentoService,
    private lancamentoFinanceiroService: LancamentoFinanceiroService
  ) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ compra }) => {
      this.compra = compra;
    });
    this.itemService.query().subscribe(itemResult => {
      this.items = itemResult.body.filter(i => i.compraId === this.compra.id);
    });

    this.moedaService.query().subscribe(moedaResult => {
      this.moedaNacional = moedaResult.body.filter(m => m.nacional).shift().codigo;
    });

    this.lancamentoFinanceiroService.query().subscribe(data => {
      const lancamento = data.body
        .filter(l => l.entidadeDocumento === EntidadeSistema.COMPRA && l.numeroDocumento === this.compra.numero)
        .shift();
      this.detalheLancamentoService.query().subscribe(detalheResult => {
        this.pagamentos = detalheResult.body.filter(d => d.lancamentoFinanceiroId === lancamento.id);
      });
    });

    // this.lancamentoFinanceiroService.getLancamentoByEntidadeAndNumero(EntidadeSistema.COMPRA, 'FR 2019/2')
    //     .subscribe( dataResult => {
    //               alert( 'OOOOOOOO' + dataResult.numero );
    // })

    // this.detalheLancamentoService.query().subscribe(data => {
    //   this.pagamentos = data.body.filter(d => d.lancamentoFinanceiroId === dataResult.id);
    // });
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
