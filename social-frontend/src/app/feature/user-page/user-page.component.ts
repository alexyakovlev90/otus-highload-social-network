import {Component, OnInit} from '@angular/core';
import {UserService} from "./user.service";
import {UserInfoItem} from "./user.model";
import {ActivatedRoute, Router} from "@angular/router";
import {AuthService} from "../../auth/auth.service";

@Component({
  selector: 'app-home',
  templateUrl: './user-page.component.html',
  styleUrls: ['./user-page.component.css']
})
export class UserPageComponent implements OnInit {

  user: UserInfoItem;

  constructor(private route: ActivatedRoute,
              private router: Router, private authService: AuthService) {
  }

  ngOnInit(): void {
    this.route.data.subscribe(item => {
      if (!item || !item.body) {
        this.authService.logout();
      }
      this.user = item.body
    },
      errorResponse => {
        this.authService.logout();
        console.log(errorResponse);
      });
  }

}
