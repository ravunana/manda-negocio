import { NgModule } from '@angular/core';
import { MandaSharedLibsModule } from './shared-libs.module';
import { FindLanguageFromKeyPipe } from './language/find-language-from-key.pipe';
import { RvAlertComponent } from './alert/alert.component';
import { RvAlertErrorComponent } from './alert/alert-error.component';
import { RvLoginModalComponent } from './login/login.component';
import { HasAnyAuthorityDirective } from './auth/has-any-authority.directive';
import { ExportAsModule } from 'ngx-export-as';
import { PrimengModule } from 'app/primeng/primeng.module';
import { LoadingBarHttpClientModule } from '@ngx-loading-bar/http-client';
import { LoadingBarModule } from '@ngx-loading-bar/core';
import { LoadingBarRouterModule } from '@ngx-loading-bar/router';

@NgModule({
  imports: [MandaSharedLibsModule],
  declarations: [FindLanguageFromKeyPipe, RvAlertComponent, RvAlertErrorComponent, RvLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [RvLoginModalComponent],
  exports: [
    MandaSharedLibsModule,
    FindLanguageFromKeyPipe,
    RvAlertComponent,
    RvAlertErrorComponent,
    RvLoginModalComponent,
    HasAnyAuthorityDirective,
    ExportAsModule,
    PrimengModule,
    LoadingBarHttpClientModule,
    LoadingBarModule,
    LoadingBarRouterModule
    // MoedaNacionalDirective
  ],
  providers: []
})
export class MandaSharedModule {}
