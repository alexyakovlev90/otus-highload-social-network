import {Component, OnInit} from '@angular/core';
import {UserService} from "./user.service";
import {UserInfoItem} from "./user.model";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-home',
  templateUrl: './user-page.component.html',
  styleUrls: ['./user-page.component.css']
})
export class UserPageComponent implements OnInit {

  user: UserInfoItem;

  constructor(private route: ActivatedRoute,
              private router: Router, private userService: UserService) {
  }

  ngOnInit(): void {
    this.route.data.subscribe(item => {
      this.user = item.body
    },
      errorResponse => {
      this.router.navigate(['/home'], {queryParams: null})
    });
  }

}
