import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MandaSharedModule } from 'app/shared/shared.module';
import { FamiliaProdutoComponent } from './familia-produto.component';
import { FamiliaProdutoDetailComponent } from './familia-produto-detail.component';
import { FamiliaProdutoUpdateComponent } from './familia-produto-update.component';
import { FamiliaProdutoDeleteDialogComponent } from './familia-produto-delete-dialog.component';
import { familiaProdutoRoute } from './familia-produto.route';

@NgModule({
  imports: [MandaSharedModule, RouterModule.forChild(familiaProdutoRoute)],
  declarations: [
    FamiliaProdutoComponent,
    FamiliaProdutoDetailComponent,
    FamiliaProdutoUpdateComponent,
    FamiliaProdutoDeleteDialogComponent
  ],
  entryComponents: [FamiliaProdutoDeleteDialogComponent]
})
export class MandaFamiliaProdutoModule {}
