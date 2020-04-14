import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {SignInLayoutComponent} from "./feature/sign-in/sign-in-layout.component";
import {SignInComponent} from "./feature/sign-in/sign-in.component";
import {AuthGuard} from "./auth/auth.guard";
import {MainLayoutComponent} from "./main-layout/main-layout.component";
import {UserPageComponent} from "./feature/user-page/user-page.component";
import {SearchComponent} from "./feature/search/search.component";
import {SignUpComponent} from "./feature/sign-up/sign-up.component";
import {UserResolver} from "./feature/user-page/user.resolver";
import {SearchResolver} from "./feature/search/search.resolver";


const routes: Routes = [
  {
    path: '',
    component: MainLayoutComponent,
    canActivate: [AuthGuard],
    children: [
      {path: '', redirectTo: '/home', pathMatch: 'full'},
      {
        path: 'home', component: UserPageComponent,
        resolve: {body: UserResolver}
      },
      {
        path: 'user/:login', component: UserPageComponent,
        resolve: {body: UserResolver}
      },
      {
        path: 'search', component: SearchComponent,
        resolve: {body: SearchResolver}
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
