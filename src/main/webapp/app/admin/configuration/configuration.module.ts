import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MandaSharedModule } from 'app/shared/shared.module';

import { RvConfigurationComponent } from './configuration.component';

import { configurationRoute } from './configuration.route';

@NgModule({
  imports: [MandaSharedModule, RouterModule.forChild([configurationRoute])],
  declarations: [RvConfigurationComponent]
})
export class ConfigurationModule {}
