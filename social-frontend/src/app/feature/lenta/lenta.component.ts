import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {WallPostListItem} from "../wall/wall.model";
import {TopicListener} from "../web-socket/topic-listener";
import {WebSocketClient} from "../web-socket/web-socket-client.service";
import {BaseResponse} from "../../shared/response.model";
import {TopicListenerService} from "../web-socket/topic-listener.service";
import {UserInfoItem} from "../user-page/user.model";
import {AuthService} from "../../auth/auth.service";

@Component({
  selector: 'app-lenta',
  templateUrl: './lenta.component.html',
  styleUrls: ['./lenta.component.css']
})
export class LentaComponent implements OnInit, TopicListener<WallPostListItem> {

  posts: WallPostListItem[];
  user: UserInfoItem;

  constructor(private route: ActivatedRoute, private authService: AuthService,
              private webSocketClient: WebSocketClient<BaseResponse<WallPostListItem>>,
              private topicListenerService: TopicListenerService<LentaComponent, string>) {
  }

  ngOnInit(): void {
    this.route.data.subscribe(item => {
      this.posts = item.body;
    });

    let that = this;
    this.authService.getAuthUser().subscribe(resp =>
      // userId used as a topic
      this.webSocketClient.connect({
        successConnection() {
          that.topicListenerService.endMonitoringTheBootProcess(that, resp.data.id.toString());
          that.topicListenerService.startMonitoringTheBootProcess(that, resp.data.id.toString());
        }
      })
    )
  }


  errorReceived(response: BaseResponse<WallPostListItem>) {

  }

  messageReceived(data: WallPostListItem) {
    console.log(data);
    this.posts.unshift(data);
  }


}
