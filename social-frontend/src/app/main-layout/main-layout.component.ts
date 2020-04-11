import {Component} from '@angular/core';
import {AuthService} from "../auth/auth.service";

@Component({
  selector: 'app-main-layout',
  templateUrl: './main-layout.component.html',
})
export class MainLayoutComponent {

  constructor(private authService: AuthService) { }

  logout() {
    this.authService.logout();
  }

}
