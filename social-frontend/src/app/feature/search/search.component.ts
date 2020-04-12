import { Component, OnInit } from '@angular/core';
import {UserInfoItem} from "../user/user.model";
import {ActivatedRoute, Router} from "@angular/router";
import {UserService} from "../user/user.service";

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  constructor(private route: ActivatedRoute,
              private router: Router, private userService: UserService) { }

  users: UserInfoItem[];

  ngOnInit(): void {
    this.route.data.subscribe(item => {
      this.users = item.body
    });
  }

}
