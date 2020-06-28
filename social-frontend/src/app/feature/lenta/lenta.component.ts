import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {WallPostListItem} from "../wall/wall.model";

@Component({
  selector: 'app-lenta',
  templateUrl: './lenta.component.html',
  styleUrls: ['./lenta.component.css']
})
export class LentaComponent implements OnInit {

  posts: WallPostListItem[];

  constructor(private route: ActivatedRoute) { }



  ngOnInit(): void {
    this.route.data.subscribe(item => {
      this.posts = item.body;
    });
  }


}
