import {Component, Input, OnInit} from '@angular/core';
import {WallPostListItem} from "../wall.model";

@Component({
  selector: 'app-post-card',
  templateUrl: './post-card.component.html',
  styleUrls: ['./post-card.component.css']
})
export class PostCardComponent implements OnInit {

  @Input() wallPost: WallPostListItem;

  @Input() caption: boolean = false;

  constructor() { }

  ngOnInit(): void {
  }

}
