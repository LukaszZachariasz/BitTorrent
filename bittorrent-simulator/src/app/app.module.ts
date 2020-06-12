import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HeaderComponent} from './components/header/header.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {ClientManagementModule} from './components/client-management/client-management.module';
import {TrackerManagementModule} from './components/tracker-management/tracker-management.module';
import {SharedModule} from './shared/shared.module';
import {ServicesModule} from './services/services.module';
import {MatCardModule} from '@angular/material/card';
import {FlexModule} from '@angular/flex-layout';


@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    ClientManagementModule,
    TrackerManagementModule,
    SharedModule,
    MatCardModule,
    FlexModule
  ],
  providers: [
    ServicesModule
  ],
  bootstrap: [
    AppComponent
  ]
})
export class AppModule {
}
