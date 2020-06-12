import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ClientListComponent} from './client-list/client-list.component';
import {ClientPanelComponent} from './client-panel/client-panel.component';
import {ClientManagementComponent} from './client-management.component';
import {FlexModule} from '@angular/flex-layout';
import {MatCardModule} from '@angular/material/card';
import {MatListModule} from '@angular/material/list';
import {SharedModule} from '../../shared/shared.module';
import {ServicesModule} from '../../services/services.module';
import {RegisterFileModalComponent} from './register-file-modal/register-file-modal.component';
import {MatButtonModule} from '@angular/material/button';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {ReactiveFormsModule} from '@angular/forms';
import {MatSnackBar} from '@angular/material/snack-bar';
import {OverlayModule} from '@angular/cdk/overlay';
import {RegisteredFileListComponent} from './registered-file-list/registered-file-list.component';
import {DownloadFileModalComponent} from './download-file/download-file-modal.component';
import {MatIconModule} from '@angular/material/icon';
import {MatMenuModule} from '@angular/material/menu';

const MODULES = [
  ClientManagementComponent,
  ClientListComponent,
  ClientPanelComponent,
  RegisterFileModalComponent
];

@NgModule({
  declarations: [
    ...MODULES,
    RegisteredFileListComponent,
    DownloadFileModalComponent
  ],
  exports: [
    ...MODULES
  ],
  imports: [
    OverlayModule,
    CommonModule,
    FlexModule,
    MatCardModule,
    MatListModule,
    SharedModule,
    ServicesModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    ReactiveFormsModule,
    MatIconModule,
    MatMenuModule
  ],
  entryComponents: [
    RegisterFileModalComponent,
    DownloadFileModalComponent
  ],
  providers: [
    MatSnackBar
  ]
})
export class ClientManagementModule {
}
