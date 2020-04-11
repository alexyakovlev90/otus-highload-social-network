import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {SigninLayoutComponent} from "./auth/signin/signin-layout.component";
import {SigninComponent} from "./auth/signin/signin.component";
import {AuthGuard} from "./auth/auth.guard";
import {MainLayoutComponent} from "./main-layout/main-layout.component";
import {HomeComponent} from "./feature/home/home.component";
import {SearchComponent} from "./feature/search/search.component";


const routes: Routes = [
  {
    path: '',
    component: MainLayoutComponent,
    canActivate: [AuthGuard],
    children: [
      {path: '', redirectTo: '/home', pathMatch: 'full'},
      {
        path: 'home', component: HomeComponent,
        // resolve: {body: HomeResolver}
      },
      {
        path: 'search', component: SearchComponent,
        // resolve: {body: HomeResolver}
      },
    ]
  },
  {
    path: '', component: SigninLayoutComponent, children: [
      {path: 'signin', component: SigninComponent}
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
