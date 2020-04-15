import {Component, Input, OnInit} from '@angular/core';
import {UserInfoItem} from "../user-page/user.model";

@Component({
  selector: 'app-card',
  templateUrl: './user-card.component.html',
  styleUrls: ['./user-card.component.css']
})
export class UserCardComponent implements OnInit {

  @Input() user: UserInfoItem;

  constructor() { }

  ngOnInit(): void {
  }

}
