import {Component, Input, OnInit} from '@angular/core';
import {UserInfoItem} from "../user-page/user.model";
import {AuthService} from "../../auth/auth.service";
import {FriendRequestService} from "../friend-request/friend-request.service";

@Component({
  selector: 'app-user-card',
  templateUrl: './user-card.component.html',
  styleUrls: ['./user-card.component.css']
})
export class UserCardComponent implements OnInit {

  @Input() user: UserInfoItem;

  constructor(private authService: AuthService, private friendRequestService: FriendRequestService) { }

  ngOnInit(): void {
  }

  addFriend(userId: number) {
    this.friendRequestService.addFriend(userId)
      .subscribe(resp => this.user.friend = true )
  }
}
