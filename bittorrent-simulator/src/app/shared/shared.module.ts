import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {LoaderComponent} from './loader/loader.component';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {FlexModule} from '@angular/flex-layout';

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
    FlexModule
  ]
})
export class SharedModule {
}
