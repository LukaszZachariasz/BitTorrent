import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ClientListComponent} from './client-list/client-list.component';
import {ClientComponent} from './client/client.component';
import {ClientManagementComponent} from './client-management.component';
import {FlexModule} from '@angular/flex-layout';
import {MatCardModule} from '@angular/material/card';
import {MatListModule} from '@angular/material/list';
import {SharedModule} from '../../shared/shared.module';
import {ServicesModule} from '../../services/services.module';

const MODULES = [
  ClientManagementComponent,
  ClientListComponent,
  ClientComponent
];

@NgModule({
  declarations: [
    ...MODULES
  ],
  exports: [
    ...MODULES
  ],
  imports: [
    CommonModule,
    FlexModule,
    MatCardModule,
    MatListModule,
    SharedModule,
    ServicesModule
  ],
  providers: []
})
export class ClientManagementModule {
}
