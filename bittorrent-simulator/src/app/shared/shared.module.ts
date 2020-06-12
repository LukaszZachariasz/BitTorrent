import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {LoaderComponent} from './components/loader/loader.component';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {FlexModule} from '@angular/flex-layout';
import {ModalBuilderService} from './services/modal-builder.service';
import {MatDialogModule} from '@angular/material/dialog';

const MODULES = [
  LoaderComponent
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
    MatProgressSpinnerModule,
    MatDialogModule,
    FlexModule
  ],
  providers: [
    ModalBuilderService
  ]
})
export class SharedModule {
}
