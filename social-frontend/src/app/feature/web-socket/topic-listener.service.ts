import {Injectable} from '@angular/core';
import {TopicListener} from "./topic-listener";
import {WebSocketClient} from "./web-socket-client.service";
import {BaseResponse} from "../../shared/response.model";
import {Subscription} from "rxjs";
import {ConnectionCallBack} from "./websocket-callback-interface";


@Injectable({
  providedIn: 'root'
})
export class TopicListenerService<T extends TopicListener<any>, J> {
  subscription: Subscription;

  constructor(private webSocketClient: WebSocketClient<BaseResponse<J>>) {
  }

  openConnection(connectionCallBack: ConnectionCallBack) {
    this.webSocketClient.connect(connectionCallBack);
  }

  subscribe(topicListenerComponent: T, topic: string) {
    try {
      this.subscription = this.webSocketClient.subscribe(topicListenerComponent, topic, {
        onDataReceived(response: BaseResponse<J>) {
          if (response.result === 'success') {
            topicListenerComponent.messageReceived(response.data);
          } else {
            topicListenerComponent.errorReceived(response);
          }
        }
      });
    } catch (e) {
      setTimeout(() => {
        this.subscribe(topicListenerComponent, topic);
      }, 500);
    }
  }

  startMonitoringTheBootProcess(topicListener: T, topic: string) {
    this.subscribe(topicListener, topic);
  }

  endMonitoringTheBootProcess(topicListener: T, topic: string) {
    this.webSocketClient.unsubscribe(this.subscription, topic, topicListener);
  }
}
