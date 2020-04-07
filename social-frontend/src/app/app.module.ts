import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {BuildingObjectComponent} from './feature/building-object/building-object.component';
import {BuildingMaterialComponent} from './feature/building-material/building-material.component';
import {MainComponent} from './feature/main/main.component';
import {SigninComponent} from "./feature/auth/signin/signin.component";
import {SigninLayoutComponent} from "./feature/auth/signin/signin-layout.component";
import {AppRoutingModule} from "./app-routing.module";
import {MaterialModule} from "./material/material.module";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";

@NgModule({
  declarations: [
    AppComponent,
    BuildingObjectComponent,
    BuildingMaterialComponent,
    SigninComponent,
    SigninLayoutComponent,
    MainComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    MaterialModule,
    HttpClientModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
