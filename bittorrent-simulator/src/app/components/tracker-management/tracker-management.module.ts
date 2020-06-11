import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {TrackerManagementComponent} from './tracker-management.component';

const MODULES = [
  TrackerManagementComponent
];

@NgModule({
  declarations: [
    ...MODULES
  ],
  exports: [
    ...MODULES
  ],
  imports: [
    CommonModule
  ]
})
export class TrackerManagementModule {
}
