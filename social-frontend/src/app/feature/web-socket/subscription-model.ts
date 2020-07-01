import {Subscription} from "rxjs";

export class SubscriptionModel {
  topic: string;
  subscription: Subscription;
  component: any;

  constructor(topic: string, subscription: Subscription, component: any) {
    this.topic = topic;
    this.subscription = subscription;
    this.component = component;
  }
}
