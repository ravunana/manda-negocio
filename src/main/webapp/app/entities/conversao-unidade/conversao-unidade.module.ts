import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MandaSharedModule } from 'app/shared/shared.module';
import { ConversaoUnidadeComponent } from './conversao-unidade.component';
import { ConversaoUnidadeDetailComponent } from './conversao-unidade-detail.component';
import { ConversaoUnidadeUpdateComponent } from './conversao-unidade-update.component';
import { ConversaoUnidadeDeleteDialogComponent } from './conversao-unidade-delete-dialog.component';
import { conversaoUnidadeRoute } from './conversao-unidade.route';

@NgModule({
  imports: [MandaSharedModule, RouterModule.forChild(conversaoUnidadeRoute)],
  declarations: [
    ConversaoUnidadeComponent,
    ConversaoUnidadeDetailComponent,
    ConversaoUnidadeUpdateComponent,
    ConversaoUnidadeDeleteDialogComponent
  ],
  entryComponents: [ConversaoUnidadeDeleteDialogComponent]
})
export class MandaConversaoUnidadeModule {}
