import {Component, Input, OnInit} from '@angular/core';
import {WallPostListItem, WallPostUpdateItem} from "./wall.model";
import {ActivatedRoute} from "@angular/router";
import {WallService} from "./wall.service";

@Component({
  selector: 'app-wall',
  templateUrl: './wall.component.html',
  styleUrls: ['./wall.component.css']
})
export class WallComponent implements OnInit {

  @Input() userId: number;

  wallPost: WallPostUpdateItem;
  posts: WallPostListItem[];

  constructor(private route: ActivatedRoute, private wallService: WallService) {
  }

  ngOnInit(): void {
    this.wallPost = <WallPostUpdateItem>{toUser: this.userId};

    this.refreshPosts();
  }

  private refreshPosts() {
    this.wallService.getUserWallPosts(this.userId).subscribe(items => {
      this.posts = items.data;
    });
  }

  addPost() {
    this.wallService.save(this.wallPost).subscribe(resp => {
        this.refreshPosts();
      }
    )
  }

}
