import { NgModule } from '@angular/core';
import { MandaSharedLibsModule } from './shared-libs.module';
import { FindLanguageFromKeyPipe } from './language/find-language-from-key.pipe';
import { RvAlertComponent } from './alert/alert.component';
import { RvAlertErrorComponent } from './alert/alert-error.component';
import { RvLoginModalComponent } from './login/login.component';
import { HasAnyAuthorityDirective } from './auth/has-any-authority.directive';

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
    HasAnyAuthorityDirective
  ]
})
export class MandaSharedModule {}
