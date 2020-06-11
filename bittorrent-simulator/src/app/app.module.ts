import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ClientComponent } from './components/client-management/client/client.component';
import { HeaderComponent } from './components/header/header.component';
import { ClientManagementComponent } from './components/client-management/client-management.component';
import { TrackerManagementComponent } from './components/tracker-management/tracker-management.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ClientListComponent } from './components/client-management/client-list/client-list.component';
import {FlexModule} from "@angular/flex-layout";

@NgModule({
  declarations: [
    AppComponent,
    ClientComponent,
    HeaderComponent,
    ClientManagementComponent,
    TrackerManagementComponent,
    ClientListComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FlexModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
