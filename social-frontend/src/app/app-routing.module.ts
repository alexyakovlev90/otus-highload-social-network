import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {SignInLayoutComponent} from "./feature/sign-in/sign-in-layout.component";
import {SignInComponent} from "./feature/sign-in/sign-in.component";
import {AuthGuard} from "./auth/auth.guard";
import {MainLayoutComponent} from "./main-layout/main-layout.component";
import {HomeComponent} from "./feature/home/home.component";
import {SearchComponent} from "./feature/search/search.component";
import {SignUpComponent} from "./feature/sign-up/sign-up.component";
import {HomeResolver} from "./feature/home/home.resolver";


const routes: Routes = [
  {
    path: '',
    component: MainLayoutComponent,
    canActivate: [AuthGuard],
    children: [
      {path: '', redirectTo: '/home', pathMatch: 'full'},
      {
        path: 'home', component: HomeComponent,
        resolve: {body: HomeResolver}
      },
      {
        path: 'search', component: SearchComponent,
        // resolve: {body: HomeResolver}
      },
      {path: 'signup', component: SignUpComponent},
    ]
  },
  {
    path: '', component: SignInLayoutComponent, children: [
      {path: 'signin', component: SignInComponent}
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
