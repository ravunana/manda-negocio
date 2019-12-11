import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { MandaSharedModule } from 'app/shared/shared.module';
import { MandaCoreModule } from 'app/core/core.module';
import { MandaAppRoutingModule } from './app-routing.module';
import { MandaHomeModule } from './home/home.module';
import { MandaEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { RvMainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    MandaSharedModule,
    MandaCoreModule,
    MandaHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    MandaEntityModule,
    MandaAppRoutingModule
  ],
  declarations: [RvMainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
  bootstrap: [RvMainComponent]
})
export class MandaAppModule {}
