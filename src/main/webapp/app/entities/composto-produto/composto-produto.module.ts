import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MandaSharedModule } from 'app/shared/shared.module';
import { CompostoProdutoComponent } from './composto-produto.component';
import { CompostoProdutoDetailComponent } from './composto-produto-detail.component';
import { CompostoProdutoUpdateComponent } from './composto-produto-update.component';
import { CompostoProdutoDeleteDialogComponent } from './composto-produto-delete-dialog.component';
import { compostoProdutoRoute } from './composto-produto.route';

@NgModule({
  imports: [MandaSharedModule, RouterModule.forChild(compostoProdutoRoute)],
  declarations: [
    CompostoProdutoComponent,
    CompostoProdutoDetailComponent,
    CompostoProdutoUpdateComponent,
    CompostoProdutoDeleteDialogComponent
  ],
  entryComponents: [CompostoProdutoDeleteDialogComponent]
})
export class MandaCompostoProdutoModule {}
