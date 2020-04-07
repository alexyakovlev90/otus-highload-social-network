import {EventEmitter, OnDestroy, OnInit} from "@angular/core";
import {ActivatedRoute, Router} from "@angular/router";
import {ListResponse} from "../model/response.model";
import {map, switchMap} from "rxjs/internal/operators";
import {Observable, Subscription} from "rxjs/index";


export abstract class ListComponent<LIST_ITEM> implements OnInit, OnDestroy {
  items: LIST_ITEM[]; //То что будет в таблице
  selectedId;
  isLoadingResults = true;

  protected filterChanges: EventEmitter<any> = new EventEmitter();

  private queryParamsSubscription: Subscription;

  constructor(protected route: ActivatedRoute, protected router: Router) {
  }


  ngOnInit() {
    this.initResponsiveTable();
  }

  ngOnDestroy() {
    console.log("DESTROY");
    this.queryParamsSubscription.unsubscribe();
  }

  protected abstract getItems(): Observable<ListResponse<LIST_ITEM>>;

  protected initResponsiveTable() {
    this.queryParamsSubscription = this.route.queryParams.pipe(
      switchMap(
        (params) => {
          this.applyParams(params);
          this.isLoadingResults = true;
          return this.getItems();
        }),
      map(pagedListResponse => {
        // Flip flag to show that loading has finished.
        this.isLoadingResults = false;
        return pagedListResponse.data;
      })
    ).subscribe(data => this.items = data);

  }

  //надо переопределять в сабклассах
  protected abstract applyParams(params);

  toggleSelectedItem(item) {
    console.log(item);
    if(item.id === this.selectedId){
      this.selectedId = null;
    } else {
      this.selectedId = item.id;
    }
  }

  isItemSelected(item){
    return this.selectedId === item.id;
  }

  onNewItem() {
    this.router.navigate(['new'], {queryParams: null, relativeTo: this.route})
  }

  onEditItem() {
    this.router.navigate([this.selectedId], {queryParams: null, relativeTo: this.route})
  }

}
