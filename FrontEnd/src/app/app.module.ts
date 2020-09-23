import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {RouterModule} from '@angular/router';
import { PatientModule } from './patient/patient.module';
import { MainComponent } from './main/main.component';
import { CarbModule } from './patient/carb/carb.module';
import { GlucoseModule } from './patient/glucose/glucose.module';

@NgModule({
  declarations: [
    AppComponent,
    MainComponent
    ],
  imports: [
    HttpClientModule,
    BrowserModule,
    AppRoutingModule,
    PatientModule,
    CarbModule,
    GlucoseModule,
    RouterModule.forRoot([])
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
