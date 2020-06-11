import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ClientListComponent} from './client-list/client-list.component';
import {ClientComponent} from './client/client.component';
import {ClientManagementComponent} from './client-management.component';
import {FlexModule} from '@angular/flex-layout';
import {MatCardModule} from '@angular/material/card';
import {MatListModule} from '@angular/material/list';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {SharedModule} from '../../shared/shared.module';

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
    MatProgressSpinnerModule,
    SharedModule,
  ]
})
export class ClientManagementModule {
}
