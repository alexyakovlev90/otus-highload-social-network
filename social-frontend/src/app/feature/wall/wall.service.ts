import {HttpClient, HttpParams} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {ListResponse, ObjectResponse} from "../../shared/response.model";
import {WallPostListItem, WallPostUpdateItem} from "./wall.model";

@Injectable({
  providedIn: 'root'
})
export class WallService {

  constructor(protected httpClient: HttpClient) {
  }

  private static apiUrl(): string {
    return "/api/wall-posts";
  }

  getUserWallPosts(userId: number): Observable<ListResponse<WallPostListItem>> {
    const params = {
      params: new HttpParams()
        .set('userId', userId.toString())
    };
    return this.httpClient.get<ListResponse<WallPostListItem>>(`${WallService.apiUrl()}`, params);
  }

  getUserLenta(): Observable<ListResponse<WallPostListItem>> {
    return this.httpClient.get<ListResponse<WallPostListItem>>(`${WallService.apiUrl()}/lenta`);
  }

  save(user: WallPostUpdateItem): Observable<ObjectResponse<WallPostUpdateItem>> {
      return this.httpClient.post<ObjectResponse<WallPostUpdateItem>>(`${WallService.apiUrl()}`, user);
  }
}
