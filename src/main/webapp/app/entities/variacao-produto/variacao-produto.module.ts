import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MandaSharedModule } from 'app/shared/shared.module';
import { VariacaoProdutoComponent } from './variacao-produto.component';
import { VariacaoProdutoDetailComponent } from './variacao-produto-detail.component';
import { VariacaoProdutoUpdateComponent } from './variacao-produto-update.component';
import { VariacaoProdutoDeleteDialogComponent } from './variacao-produto-delete-dialog.component';
import { variacaoProdutoRoute } from './variacao-produto.route';

@NgModule({
  imports: [MandaSharedModule, RouterModule.forChild(variacaoProdutoRoute)],
  declarations: [
    VariacaoProdutoComponent,
    VariacaoProdutoDetailComponent,
    VariacaoProdutoUpdateComponent,
    VariacaoProdutoDeleteDialogComponent
  ],
  entryComponents: [VariacaoProdutoDeleteDialogComponent]
})
export class MandaVariacaoProdutoModule {}
