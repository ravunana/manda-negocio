import { Component, OnInit } from '@angular/core';
import { IVenda } from 'app/shared/model/venda.model';
import { IItemVenda } from 'app/shared/model/item-venda.model';
import { IEmpresa } from 'app/shared/model/empresa.model';
import { IPessoa } from 'app/shared/model/pessoa.model';
import { ICliente } from 'app/shared/model/cliente.model';
import { IContactoEmpresa } from 'app/shared/model/contacto-empresa.model';
import { ILocalizacaoEmpresa } from 'app/shared/model/localizacao-empresa.model';
import { IContactoPessoa } from 'app/shared/model/contacto-pessoa.model';
import { IMoradaPessoa } from 'app/shared/model/morada-pessoa.model';
import { ICoordenadaBancaria } from 'app/shared/model/coordenada-bancaria.model';
import { IDetalheLancamento } from 'app/shared/model/detalhe-lancamento.model';
import { JhiDataUtils } from 'ng-jhipster';
import { ActivatedRoute } from '@angular/router';
import { ItemVendaService } from 'app/entities/item-venda/item-venda.service';
import { EmpresaService } from 'app/entities/empresa/empresa.service';
import { PessoaService } from 'app/entities/pessoa/pessoa.service';
import { ClienteService } from 'app/entities/cliente/cliente.service';
import { ContactoEmpresaService } from 'app/entities/contacto-empresa/contacto-empresa.service';
import { LocalizacaoEmpresaService } from 'app/entities/localizacao-empresa/localizacao-empresa.service';
import { ContactoPessoaService } from 'app/entities/contacto-pessoa/contacto-pessoa.service';
import { MoradaPessoaService } from 'app/entities/morada-pessoa/morada-pessoa.service';
import { CoordenadaBancariaService } from 'app/entities/coordenada-bancaria/coordenada-bancaria.service';
import { VendaService } from '../venda.service';
import { DetalheLancamentoService } from 'app/entities/detalhe-lancamento/detalhe-lancamento.service';
import { LancamentoFinanceiroService } from 'app/entities/lancamento-financeiro/lancamento-financeiro.service';
import { ProdutoService } from 'app/entities/produto/produto.service';
import { PdfMakeWrapper, Txt, Columns } from 'pdfmake-wrapper';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import pdfFonts from 'pdfmake/build/vfs_fonts';

@Component({
  selector: 'rv-ticket-report',
  templateUrl: './ticket-report.component.html',
  styleUrls: ['./ticket-report.component.scss']
})
export class TicketReportComponent implements OnInit {
  venda: IVenda;
  items: IItemVenda[];
  empresa: IEmpresa;
  pessoa: IPessoa;
  cliente: ICliente;
  contactoEmpresa: IContactoEmpresa[];
  localizacaoEmpresa: ILocalizacaoEmpresa;
  contactoPessoa: IContactoPessoa[];
  moradaPessoa: IMoradaPessoa;
  coordenadasBancaria: ICoordenadaBancaria[];
  recebimentos: IDetalheLancamento[];

  SUB_TOTAL = 0;
  TOTAL_DESCONTO = 0;
  TOTAL_PAGAR = 0;
  TOTAL_ENTREGUE = 0;
  TROCO = 0;

  constructor(
    protected dataUtils: JhiDataUtils,
    protected activatedRoute: ActivatedRoute,
    public itemVendaService: ItemVendaService,
    protected empresaService: EmpresaService,
    protected pessoaService: PessoaService,
    protected clienteService: ClienteService,
    protected contactoEmpresaService: ContactoEmpresaService,
    protected localizacaoEmpresaService: LocalizacaoEmpresaService,
    protected contactoPessoaService: ContactoPessoaService,
    protected moradaPessoaService: MoradaPessoaService,
    protected coordenadaBancariaService: CoordenadaBancariaService,
    protected vendaService: VendaService,
    protected detalheLancamentoService: DetalheLancamentoService,
    protected lancamentoFinanceiroService: LancamentoFinanceiroService,
    protected produtoService: ProdutoService
  ) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ venda }) => {
      this.venda = venda;
    });

    this.empresaService.query().subscribe(data => {
      this.empresa = data.body.shift();

      this.contactoEmpresaService.query().subscribe(data => {
        this.contactoEmpresa = data.body.filter(c => c.empresaId === this.empresa.id);
      });

      this.localizacaoEmpresaService.query().subscribe(data => {
        this.localizacaoEmpresa = data.body.filter(c => c.empresaId === this.empresa.id).shift();
      });
    });

    this.itemVendaService.query().subscribe(data => {
      this.items = data.body.filter(i => i.vendaId === this.venda.id);
      this.SUB_TOTAL = this.items
        .map(i => i.quantidade * i.valor)
        .reduce(function(total, subTotal) {
          return total + subTotal;
        });

      // valor, quantidade, desconto
      let ValorDesconto = this.items
        .map(i => this.produtoService.calcularSubTotalItem(i.quantidade, i.desconto, i.valor))
        .reduce(function(total, desconto) {
          return total + desconto;
        });

      this.TOTAL_DESCONTO = this.SUB_TOTAL - ValorDesconto;

      this.TOTAL_PAGAR = this.SUB_TOTAL - this.TOTAL_DESCONTO;
    });

    this.clienteService.query().subscribe(clienteResult => {
      this.cliente = clienteResult.body.filter(c => c.id === this.venda.clienteId).shift();

      this.pessoaService.query().subscribe(pessoaResult => {
        this.pessoa = pessoaResult.body.filter(p => p.id === this.cliente.pessoaId).shift();

        this.contactoPessoaService.query().subscribe(contactoPessoaResult => {
          this.contactoPessoa = contactoPessoaResult.body.filter(c => c.pessoaId === this.pessoa.id);
        });
        this.moradaPessoaService.query().subscribe(moradaPessoaResult => {
          this.moradaPessoa = moradaPessoaResult.body.filter(c => c.pessoaId === this.pessoa.id).shift();
        });
      });
    });

    this.coordenadaBancariaService.query().subscribe(data => {
      this.coordenadasBancaria = data.body.filter(c => c.mostrarDocumento === true);
    });

    this.lancamentoFinanceiroService.query({ 'numeroDocumento.equals': this.venda.numero }).subscribe(data => {
      const lancamento = data.body.shift();
      this.detalheLancamentoService.query().subscribe(data => {
        this.recebimentos = data.body.filter(d => d.lancamentoFinanceiroId === lancamento.id);
        this.TOTAL_ENTREGUE = this.recebimentos
          .map(i => i.valor)
          .reduce(function(total, subTotal) {
            return total + subTotal;
          });
      });
    });
  }

  async ticketReport() {
    PdfMakeWrapper.setFonts(pdfFonts);

    const pdf = new PdfMakeWrapper();
    pdf.defaultStyle({
      bold: false,
      fontSize: 8
    });
    pdf.pageMargins([4, 8]);
    pdf.pageSize('A7');
    // pdf.pageSize({
    //   width: 275,
    //   height: 'auto'
    // });

    // pdf.pageBreakBefore(
    //   (currentNode, followingNodesOnPage, nodesOnNextPage, previousNodesOnPage) => {
    //       return currentNode.headlineLevel === 1 && followingNodesOnPage.length === 0;
    //   });

    // alert( this.empresa.logotipo + '' + this.empresa.logotipoContentType );
    // <img [src]="'data:' + empresa.logotipoContentType + ';base64,' + empresa.logotipo" style="max-height: 30px;" alt="empresa image"/>

    // pdf.add( await new Img('data:' + this.empresa.logotipoContentType + ';base64,' + this.empresa.logotipo, true).build() );
    pdf.add(new Txt(this.empresa.nome).alignment('center').bold().end);
    pdf.add(
      new Txt(
        `Localização: ${this.localizacaoEmpresa.bairro}, ${this.localizacaoEmpresa.rua}, ${this.localizacaoEmpresa.quarteirao}, ${this.localizacaoEmpresa.numeroPorta}`
      )
        .alignment('center')
        .bold().end
    );
    pdf.add(new Txt('NIF: ' + this.empresa.nif).alignment('center').bold().end);

    for (let contacto of this.contactoEmpresa) {
      pdf.add(new Txt(`${contacto.tipoContacto} : ${contacto.contacto}`).alignment('center').bold().end);
    }

    pdf.add('-------------------------------------------------------------------------------------------');
    pdf.add('Factura/recibo ' + ': ' + this.venda.numero);
    pdf.add(`Data e Hora ${this.venda.data.format(DATE_TIME_FORMAT)}`);
    pdf.add('-------------------------------------------------------------------------------------------');
    pdf.add('Cliente: ' + this.pessoa.nome);
    pdf.add('Morada: ' + this.moradaPessoa);
    pdf.add(`NIF: ${this.pessoa.nif} | Nº cliente: ${this.venda.clienteNumero}`);
    pdf.add('-------------------------------------------------------------------------------------------');

    pdf.add(new Columns(['Produto|Serviço', 'Qtde', 'Preço', 'SubTotal']).end);

    for (let item of this.items) {
      pdf.add(
        new Columns([item.produtoNome, item.quantidade + ',00', item.valor + ',00', item.quantidade * item.valor + ',00']).columnGap(5).end
      );
    }

    pdf.add('-------------------------------------------------------------------------------------------');
    pdf.add('Subtotal: ' + this.SUB_TOTAL + ',00');
    pdf.add('Descontos: ' + this.TOTAL_DESCONTO + ',00');
    pdf.add('Total: ' + this.TOTAL_PAGAR + ',00');
    pdf.add('------------------');
    pdf.add('Modo de recebimento');

    for (let metodo of this.recebimentos) {
      pdf.add(` ${metodo.metodoLiquidacaoNome} : ${metodo.valor},00 ${metodo.moedaCodigo}`);
    }

    pdf.add('Total recebido: ' + this.TOTAL_ENTREGUE + ',00');
    pdf.add('Troco: ' + (this.TOTAL_ENTREGUE - this.TOTAL_PAGAR) + ',00');
    pdf.add('-------------------------------------------------------------------------------------------');
    pdf.add('Vendedor(a): ' + this.venda.vendedorLogin);
    pdf.add('-------------------------------------------------------------------------------------------');

    pdf.add(new Txt('Obrigado pela visita, volte sempre !!!').alignment('center').bold().end);
    pdf.add(new Txt('Desenvolvido por RAVUNANA - ravunana.netlify.com').alignment('center').bold().end);

    pdf.create().print();
  }
}
