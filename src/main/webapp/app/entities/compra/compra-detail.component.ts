import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ICompra } from 'app/shared/model/compra.model';
import { IItemCompra } from 'app/shared/model/item-compra.model';
import { ItemCompraService } from '../item-compra/item-compra.service';
import { MoedaService } from '../moeda/moeda.service';
import { ProdutoService } from '../produto/produto.service';

@Component({
  selector: 'rv-compra-detail',
  templateUrl: './compra-detail.component.html'
})
export class CompraDetailComponent implements OnInit {
  compra: ICompra;
  items: IItemCompra[];
  moedaNacional;

  constructor(
    protected dataUtils: JhiDataUtils,
    protected activatedRoute: ActivatedRoute,
    protected itemService: ItemCompraService,
    protected moedaService: MoedaService,
    public produtoService: ProdutoService
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
