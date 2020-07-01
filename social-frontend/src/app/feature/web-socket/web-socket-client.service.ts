import {Injectable, OnDestroy} from '@angular/core';
import * as SockJS from 'sockjs-client';
import * as Stomp from 'stompjs';
import {ConnectionCallBack, WebSocketCallBack} from './websocket-callback-interface';
import {Subscription} from 'rxjs';
import {SubscriptionModel} from './subscription-model';

const END_POINT = '/api';
const MAX_RETIRES_RECONNECT_COUNT = 5;

/**
 * Используется для подключения к топику сообщений с нашим бэком.
 * Сейчас используем для отображение загрузки файлов при сборе аудитории.
 * Еще будем точно использовать для отображения процесса операций
 */

@Injectable({
  providedIn: 'root'
})
export class WebSocketClient<T> implements OnDestroy {
  ws: any;
  isConnected = false;
  subscriptions: SubscriptionModel[] = [];

  constructor() {
  }

  // n - порог для retry ввели из недлительной дизни сессии в kubernetes
  connect(connectionCallBack: ConnectionCallBack, n: number = MAX_RETIRES_RECONNECT_COUNT) {
    if (!this.isConnected) {
      const socket = new SockJS(END_POINT);
      this.ws = Stomp.over(socket);
      const that = this;
      this.ws.connect({}, () => {
        that.isConnected = true;
        n = MAX_RETIRES_RECONNECT_COUNT;
        connectionCallBack.successConnection();
      }, () => {
        that.disconnect();
        if (n > 0) {
          console.log('Reconnecting....');
          that.connect(connectionCallBack, --n);
        } else {
          console.log('Проблема с переподключением по вебсокету после 5 попыток');
        }
      });
    } else {
      connectionCallBack.successConnection();
    }
  }

  disconnect() {
    if (this.isConnected) {
      this.isConnected = false;
      this.ws.disconnect();
    }
  }

  checkSubscription(component: any, topic: string): SubscriptionModel {
    return this.subscriptions.find(value => {
      return value.topic === topic && value.component === component;
    });
  }

  addSubscription(component: any, topic: string, subscription: Subscription) {
    this.subscriptions.push(new SubscriptionModel(topic, subscription, component));
  }

  removeSubscription(component: any, topic: string) {
    this.subscriptions.splice(this.subscriptions.findIndex(value => {
      return value.topic === topic && value.component === component;
    }), 1);
  }

  subscribe(component: any, topic: string, webSocketCallBack: WebSocketCallBack<T>): Subscription {
    const subscription = this.checkSubscription(component, topic);
    if (subscription) {
      return subscription.subscription;
    } else {
      const topicSubscription = this.ws.subscribe(topic, (response) => {
        webSocketCallBack.onDataReceived(JSON.parse(response.body));
      });
      this.addSubscription(component, topic, topicSubscription);
      return topicSubscription;
    }
  }

  unsubscribe(subscription: Subscription, topic: string, component: any) {
    this.removeSubscription(component, topic);
    for (const sub in this.ws.subscriptions) {
      if (this.ws.subscriptions.hasOwnProperty(sub)) {
        this.ws.unsubscribe(sub);
      }
    }
  }

  ngOnDestroy(): void {
    this.ws.disconnect();
  }
}
