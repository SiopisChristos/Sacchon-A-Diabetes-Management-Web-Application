import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {RouterModule} from '@angular/router';
import { PatientModule } from './patient/patient.module';
import { CarbModule } from './patient/carb/carb.module';
import { GlucoseModule } from './patient/glucose/glucose.module';
import { LoginModule } from './login/login.module';
import { MainComponent } from './main/main.component';
import { DoctorModule } from './doctor/doctor.module';
import { NoteModule } from './doctor/note/note.module';
import { ChiefModule } from './chief/chief.module';

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
    DoctorModule,
     ChiefModule,
    CarbModule,
    NoteModule,
    GlucoseModule,
    LoginModule,
    RouterModule.forRoot([])
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
