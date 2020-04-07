import {OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Observable, Subscription} from "rxjs/index";
import {NgForm} from '@angular/forms';
import {ObjectResponse} from "../model/response.model";

export abstract class AbstractEditorComponent<E> implements OnInit, OnDestroy {

  item: E;

  private getItemSubscription: Subscription;
  private saveItemSubscription: Subscription;

  constructor(protected route: ActivatedRoute,
              protected router: Router) {
  }

  ngOnInit() {
    this.getItemSubscription = this.route.data
      .subscribe(item => this.item = item.body);
  }

  ngOnDestroy() {
    if (this.getItemSubscription) {
      this.getItemSubscription.unsubscribe();
    }
    if (this.saveItemSubscription) {
      this.saveItemSubscription.unsubscribe();
    }
  }

  onSave($event, ...forms: NgForm[]) {
    console.log($event);
    this.saveItemSubscription = this.saveItem(this.item).subscribe(resp => this.leave());//todo проверка ошибок
  }

  onCancel($event) {
    console.log($event);
    //todo проверка несохраненых изменений
    this.leave();
  }

  leave() {
    let parentPath = this.route.snapshot.url
      .slice(0, this.route.snapshot.url.length - 1)
      .map(segment => segment.path);

    this.router.navigate(parentPath, {queryParams: null});
  }

  isValid(...forms: NgForm[]) {
    return forms.filter(f => f.invalid).length === 0;
  }

  protected abstract saveItem(item: E): Observable<ObjectResponse<E>>
}
