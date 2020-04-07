import {ListResponse, ObjectResponse} from "../model/response.model";
import {Observable} from "rxjs/index";
import {HttpClient} from "@angular/common/http";

export abstract class AbstractItemService<LIST_ITEM, EDIT_ITEM extends EntityItem> {

  constructor(protected httpClient: HttpClient) {
  }

  protected abstract apiUrl(): string;

  getItemById(id: number): Observable<ObjectResponse<EDIT_ITEM>> {
    return this.httpClient.get<ObjectResponse<EDIT_ITEM>>(`${this.apiUrl()}/${id}`);
  }

  getItems(): Observable<ListResponse<LIST_ITEM>> {
    return this.httpClient.get<ListResponse<LIST_ITEM>>(this.apiUrl())
  }

  saveItem(editItem: EDIT_ITEM): Observable<ObjectResponse<EDIT_ITEM>> {
    if (editItem.id) {
      return this.httpClient.put<ObjectResponse<EDIT_ITEM>>(this.apiUrl() + editItem.id, editItem);
    } else {
      return this.httpClient.post<ObjectResponse<EDIT_ITEM>>(this.apiUrl(), editItem);
    }
  }
}

export interface EntityItem {
  id?: number
}
