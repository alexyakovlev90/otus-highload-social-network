import {Component, OnInit} from '@angular/core';
import {UserInfoItem} from "./user.model";
import {ActivatedRoute, Router} from "@angular/router";
import {AuthService} from "../../auth/auth.service";
import {FriendRequestService} from "../friend-request/friend-request.service";

@Component({
  selector: 'app-home',
  templateUrl: './user-page.component.html',
  styleUrls: ['./user-page.component.css']
})
export class UserPageComponent implements OnInit {

  user: UserInfoItem;


  constructor(private route: ActivatedRoute,
              private router: Router, private authService: AuthService,
              private friendRequestService: FriendRequestService) {
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

  showFriends() {
    this.router.navigate(['friends/' + this.user.id])
  }

  addFriend(userId: number) {
    this.friendRequestService.addFriend(userId)
      .subscribe(resp => this.user.friend = true )
  }
}
