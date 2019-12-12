import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'classe-conta',
        loadChildren: () => import('./classe-conta/classe-conta.module').then(m => m.MandaClasseContaModule)
      },
      {
        path: 'conta',
        loadChildren: () => import('./conta/conta.module').then(m => m.MandaContaModule)
      },
      {
        path: 'regra-movimentacao-debito',
        loadChildren: () =>
          import('./regra-movimentacao-debito/regra-movimentacao-debito.module').then(m => m.MandaRegraMovimentacaoDebitoModule)
      },
      {
        path: 'regra-movimentacao-credito',
        loadChildren: () =>
          import('./regra-movimentacao-credito/regra-movimentacao-credito.module').then(m => m.MandaRegraMovimentacaoCreditoModule)
      },
      {
        path: 'escrituracao-contabil',
        loadChildren: () => import('./escrituracao-contabil/escrituracao-contabil.module').then(m => m.MandaEscrituracaoContabilModule)
      },
      {
        path: 'conta-debito',
        loadChildren: () => import('./conta-debito/conta-debito.module').then(m => m.MandaContaDebitoModule)
      },
      {
        path: 'conta-credito',
        loadChildren: () => import('./conta-credito/conta-credito.module').then(m => m.MandaContaCreditoModule)
      },
      {
        path: 'serie-documento',
        loadChildren: () => import('./serie-documento/serie-documento.module').then(m => m.MandaSerieDocumentoModule)
      },
      {
        path: 'software',
        loadChildren: () => import('./software/software.module').then(m => m.MandaSoftwareModule)
      },
      {
        path: 'licenca-software',
        loadChildren: () => import('./licenca-software/licenca-software.module').then(m => m.MandaLicencaSoftwareModule)
      },
      {
        path: 'lookup',
        loadChildren: () => import('./lookup/lookup.module').then(m => m.MandaLookupModule)
      },
      {
        path: 'lookup-item',
        loadChildren: () => import('./lookup-item/lookup-item.module').then(m => m.MandaLookupItemModule)
      },
      {
        path: 'fluxo-documento',
        loadChildren: () => import('./fluxo-documento/fluxo-documento.module').then(m => m.MandaFluxoDocumentoModule)
      },
      {
        path: 'documento-comercial',
        loadChildren: () => import('./documento-comercial/documento-comercial.module').then(m => m.MandaDocumentoComercialModule)
      },
      {
        path: 'imposto',
        loadChildren: () => import('./imposto/imposto.module').then(m => m.MandaImpostoModule)
      },
      {
        path: 'grupo-tributacao-imposto',
        loadChildren: () =>
          import('./grupo-tributacao-imposto/grupo-tributacao-imposto.module').then(m => m.MandaGrupoTributacaoImpostoModule)
      },
      {
        path: 'morada-pessoa',
        loadChildren: () => import('./morada-pessoa/morada-pessoa.module').then(m => m.MandaMoradaPessoaModule)
      },
      {
        path: 'contacto-pessoa',
        loadChildren: () => import('./contacto-pessoa/contacto-pessoa.module').then(m => m.MandaContactoPessoaModule)
      },
      {
        path: 'pessoa',
        loadChildren: () => import('./pessoa/pessoa.module').then(m => m.MandaPessoaModule)
      },
      {
        path: 'relacionamento-pessoa',
        loadChildren: () => import('./relacionamento-pessoa/relacionamento-pessoa.module').then(m => m.MandaRelacionamentoPessoaModule)
      },
      {
        path: 'empresa',
        loadChildren: () => import('./empresa/empresa.module').then(m => m.MandaEmpresaModule)
      },
      {
        path: 'localizacao-empresa',
        loadChildren: () => import('./localizacao-empresa/localizacao-empresa.module').then(m => m.MandaLocalizacaoEmpresaModule)
      },
      {
        path: 'contacto-empresa',
        loadChildren: () => import('./contacto-empresa/contacto-empresa.module').then(m => m.MandaContactoEmpresaModule)
      },
      {
        path: 'coordenada-bancaria',
        loadChildren: () => import('./coordenada-bancaria/coordenada-bancaria.module').then(m => m.MandaCoordenadaBancariaModule)
      },
      {
        path: 'arquivo',
        loadChildren: () => import('./arquivo/arquivo.module').then(m => m.MandaArquivoModule)
      },
      {
        path: 'fornecedor',
        loadChildren: () => import('./fornecedor/fornecedor.module').then(m => m.MandaFornecedorModule)
      },
      {
        path: 'cliente',
        loadChildren: () => import('./cliente/cliente.module').then(m => m.MandaClienteModule)
      },
      {
        path: 'unidade-medida',
        loadChildren: () => import('./unidade-medida/unidade-medida.module').then(m => m.MandaUnidadeMedidaModule)
      },
      {
        path: 'moeda',
        loadChildren: () => import('./moeda/moeda.module').then(m => m.MandaMoedaModule)
      },
      {
        path: 'forma-liquidacao',
        loadChildren: () => import('./forma-liquidacao/forma-liquidacao.module').then(m => m.MandaFormaLiquidacaoModule)
      },
      {
        path: 'meio-liquidacao',
        loadChildren: () => import('./meio-liquidacao/meio-liquidacao.module').then(m => m.MandaMeioLiquidacaoModule)
      },
      {
        path: 'lancamento-financeiro',
        loadChildren: () => import('./lancamento-financeiro/lancamento-financeiro.module').then(m => m.MandaLancamentoFinanceiroModule)
      },
      {
        path: 'detalhe-lancamento',
        loadChildren: () => import('./detalhe-lancamento/detalhe-lancamento.module').then(m => m.MandaDetalheLancamentoModule)
      },
      {
        path: 'retencao-fonte',
        loadChildren: () => import('./retencao-fonte/retencao-fonte.module').then(m => m.MandaRetencaoFonteModule)
      },
      {
        path: 'local-armazenamento',
        loadChildren: () => import('./local-armazenamento/local-armazenamento.module').then(m => m.MandaLocalArmazenamentoModule)
      },
      {
        path: 'composto-produto',
        loadChildren: () => import('./composto-produto/composto-produto.module').then(m => m.MandaCompostoProdutoModule)
      },
      {
        path: 'familia-produto',
        loadChildren: () => import('./familia-produto/familia-produto.module').then(m => m.MandaFamiliaProdutoModule)
      },
      {
        path: 'produto',
        loadChildren: () => import('./produto/produto.module').then(m => m.MandaProdutoModule)
      },
      {
        path: 'detalhe-aduaneiro',
        loadChildren: () => import('./detalhe-aduaneiro/detalhe-aduaneiro.module').then(m => m.MandaDetalheAduaneiroModule)
      },
      {
        path: 'variacao-produto',
        loadChildren: () => import('./variacao-produto/variacao-produto.module').then(m => m.MandaVariacaoProdutoModule)
      },
      {
        path: 'estrutura-calculo',
        loadChildren: () => import('./estrutura-calculo/estrutura-calculo.module').then(m => m.MandaEstruturaCalculoModule)
      },
      {
        path: 'estoque-config',
        loadChildren: () => import('./estoque-config/estoque-config.module').then(m => m.MandaEstoqueConfigModule)
      },
      {
        path: 'conversao-unidade',
        loadChildren: () => import('./conversao-unidade/conversao-unidade.module').then(m => m.MandaConversaoUnidadeModule)
      },
      {
        path: 'compra',
        loadChildren: () => import('./compra/compra.module').then(m => m.MandaCompraModule)
      },
      {
        path: 'item-compra',
        loadChildren: () => import('./item-compra/item-compra.module').then(m => m.MandaItemCompraModule)
      },
      {
        path: 'devolucao-compra',
        loadChildren: () => import('./devolucao-compra/devolucao-compra.module').then(m => m.MandaDevolucaoCompraModule)
      },
      {
        path: 'auditoria-compra',
        loadChildren: () => import('./auditoria-compra/auditoria-compra.module').then(m => m.MandaAuditoriaCompraModule)
      },
      {
        path: 'venda',
        loadChildren: () => import('./venda/venda.module').then(m => m.MandaVendaModule)
      },
      {
        path: 'item-venda',
        loadChildren: () => import('./item-venda/item-venda.module').then(m => m.MandaItemVendaModule)
      },
      {
        path: 'devolucao-venda',
        loadChildren: () => import('./devolucao-venda/devolucao-venda.module').then(m => m.MandaDevolucaoVendaModule)
      },
      {
        path: 'auditoria-venda',
        loadChildren: () => import('./auditoria-venda/auditoria-venda.module').then(m => m.MandaAuditoriaVendaModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class MandaEntityModule {}
