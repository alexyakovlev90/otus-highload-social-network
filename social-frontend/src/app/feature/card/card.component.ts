import {Component, Input, OnInit} from '@angular/core';
import {UserInfoItem} from "../user-page/user.model";

@Component({
  selector: 'app-card',
  templateUrl: './card.component.html',
  styleUrls: ['./card.component.css']
})
export class CardComponent implements OnInit {

  @Input() user: UserInfoItem;

  constructor() { }

  ngOnInit(): void {
  }

}
