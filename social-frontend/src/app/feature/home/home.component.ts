import {Component, OnInit} from '@angular/core';
import {UserService} from "../user/user.service";
import {UserInfoItem} from "../user/user.model";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  user: UserInfoItem;

  constructor(private route: ActivatedRoute,
              private router: Router, private userService: UserService) {
  }

  ngOnInit(): void {
    this.route.data.subscribe(item => {
      this.user = item.body
    });
  }

}
