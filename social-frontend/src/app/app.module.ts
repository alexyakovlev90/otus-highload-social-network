import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {SignInLayoutComponent} from "./feature/sign-in/sign-in-layout.component";
import {SignInComponent} from "./feature/sign-in/sign-in.component";
import {MainLayoutComponent} from "./main-layout/main-layout.component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatCardModule} from "@angular/material/card";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatToolbarModule} from "@angular/material/toolbar";
import {UserPageComponent} from './feature/user-page/user-page.component';
import {UserListComponent} from './feature/user-list/user-list.component';
import {AuthGuard} from "./auth/auth.guard";
import {AuthService} from "./auth/auth.service";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {MatInputModule} from "@angular/material/input";
import {MatButtonModule} from "@angular/material/button";
import {MatDividerModule} from "@angular/material/divider";
import {MatListModule} from "@angular/material/list";
import {UserCardComponent} from './feature/user-card/user-card.component';
import {SignUpComponent} from './feature/sign-up/sign-up.component';
import {MatSelectModule} from "@angular/material/select";
import {AuthExpiredInterceptor} from "./auth/auth-expired.interceptor";
import { LentaComponent } from './feature/lenta/lenta.component';
import { WallComponent } from './feature/wall/wall.component';
import { PostCardComponent } from './feature/wall/post-card/post-card.component';

@NgModule({
  declarations: [
    AppComponent,
    MainLayoutComponent,
    SignInComponent,
    SignInLayoutComponent,
    UserPageComponent,
    UserListComponent,
    UserCardComponent,
    SignUpComponent,
    LentaComponent,
    WallComponent,
    PostCardComponent,
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        FormsModule,
        BrowserAnimationsModule,
        MatCardModule,
        MatFormFieldModule,
        MatToolbarModule,
        HttpClientModule,
        MatInputModule,
        MatButtonModule,
        MatDividerModule,
        MatListModule,
        MatSelectModule,
        ReactiveFormsModule,
    ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthExpiredInterceptor,
      multi: true
    },
    AuthGuard,
    AuthService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
