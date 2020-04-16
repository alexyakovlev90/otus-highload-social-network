import { Component, OnInit } from '@angular/core';
import {UserInfoItem} from "../user-page/user.model";
import {ActivatedRoute, Router} from "@angular/router";
import {UserService} from "../user-page/user.service";
import {FriendRequestService} from "../friend-request/friend-request.service";

@Component({
  selector: 'app-search',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.css']
})
export class UserListComponent implements OnInit {

  users: UserInfoItem[];
  authUserFriends: UserInfoItem[];

  constructor(private route: ActivatedRoute,
              private router: Router, private userService: UserService,
              private friendRequestService: FriendRequestService) { }



  ngOnInit(): void {
    this.route.data.subscribe(item => {
      this.users = item.body;
    });
  }

  setFriends() {

  }

  isFriend(user: UserInfoItem) : boolean {
    const userId = this.route.params['userId'] ? this.route.params['userId'] : null;
    if (userId) {
      return true
    } else {
      // if search page
      return this.authUserFriends.filter(friend => friend.id == user.id).length > 0
    }
  }
}
