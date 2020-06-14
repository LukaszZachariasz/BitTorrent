import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ClientListService} from './client-list.service';
import {TrackerService} from './tracker.service';
import {HttpClientModule} from '@angular/common/http';
import {ClientControlBehaviourService} from './client-control-behaviour.service';


@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    HttpClientModule
  ],
  providers: [
    ClientListService,
    TrackerService,
    ClientControlBehaviourService
  ]
})
export class ServicesModule {
}
