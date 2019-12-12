import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { RatingModule } from 'primeng/rating';
import { AccordionModule } from 'primeng/accordion';
import { InputMaskModule } from 'primeng/inputmask';
import { AutoCompleteModule } from 'primeng/autocomplete';
import { TreeTableModule } from 'primeng/treetable';
import { TabViewModule } from 'primeng/tabview';
import { DragDropModule } from 'primeng/dragdrop';
import { TooltipModule } from 'primeng/tooltip';
import { RadioButtonModule } from 'primeng/radiobutton';
import { DialogModule } from 'primeng/dialog';

@NgModule({
  declarations: [],
  imports: [CommonModule],
  exports: [
    RatingModule,
    AccordionModule,
    InputMaskModule,
    AutoCompleteModule,
    TreeTableModule,
    TabViewModule,
    DragDropModule,
    TooltipModule,
    RadioButtonModule,
    DialogModule
  ]
})
export class PrimengModule {}
