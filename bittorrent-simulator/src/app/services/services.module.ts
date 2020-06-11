import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ClientService} from './client.service';
import {TrackerService} from './tracker.service';


@NgModule({
  declarations: [],
  imports: [
    CommonModule
  ],
  providers: [
    ClientService,
    TrackerService
  ]
})
export class ServicesModule {
}
