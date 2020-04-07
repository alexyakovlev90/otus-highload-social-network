import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule, Routes} from '@angular/router';

import {MainComponent} from "./feature/main/main.component";
import {BuildingMaterialComponent} from "./feature/building-material/building-material.component";
import {BuildingObjectComponent} from "./feature/building-object/building-object.component";
import {SigninComponent} from "./feature/auth/signin/signin.component";
import {SigninLayoutComponent} from "./feature/auth/signin/signin-layout.component";

const routes: Routes = [
  {
    path: '',
    component: MainComponent,
    // canActivate: [AuthGuard],
    children: [
      {path: 'building-materials', component: BuildingMaterialComponent},
      {path: 'building-objects', component: BuildingObjectComponent}
    ]
  },
  {
    path: '', component: SigninLayoutComponent, children: [
      {path: 'signin', component: SigninComponent}
    ]
  }

];

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forRoot(routes)
  ],
  exports: [
    RouterModule
  ],
  declarations: []
})
export class AppRoutingModule {
}
