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
import {ClientFileListComponent, KeysPipe} from './client-file-list/client-file-list.component';
import {DownloadFileModalComponent} from './download-file/download-file-modal.component';
import {MatIconModule} from '@angular/material/icon';
import {MatMenuModule} from '@angular/material/menu';
import {MatTableModule} from '@angular/material/table';
import {MatStepperModule} from '@angular/material/stepper';
import {MatProgressBarModule} from '@angular/material/progress-bar';
import {MatTooltipModule} from '@angular/material/tooltip';
import {MatSliderModule} from '@angular/material/slider';

const MODULES = [
  ClientManagementComponent,
  ClientListComponent,
  ClientPanelComponent,
  RegisterFileModalComponent,
  DownloadFileModalComponent,
  KeysPipe
];

@NgModule({
  declarations: [
    ClientFileListComponent,
    ...MODULES
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
    MatMenuModule,
    MatTableModule,
    MatStepperModule,
    MatProgressBarModule,
    MatTooltipModule,
    MatSliderModule
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
