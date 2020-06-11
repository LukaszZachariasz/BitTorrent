import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ClientListService} from './client-list.service';
import {TrackerService} from './tracker.service';
import {HttpClientModule} from '@angular/common/http';


@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    HttpClientModule
  ],
  providers: [
    ClientListService,
    TrackerService
  ]
})
export class ServicesModule {
}
