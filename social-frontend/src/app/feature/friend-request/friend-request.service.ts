import {HttpClient, HttpParams} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {ListResponse, ObjectResponse} from "../../shared/response.model";
import {UserInfoItem} from "../user-page/user.model";

@Injectable({
  providedIn: 'root'
})
export class FriendRequestService {

  constructor(protected httpClient: HttpClient) {
  }

  private static apiUrl(): string {
    return "/api/friend-requests";
  }

  getUserFriends(userId: number): Observable<ListResponse<UserInfoItem>> {
    if (userId) {
      const params = {
        params: new HttpParams()
          .set('userId', userId.toString())
      };
      return this.httpClient.get<ListResponse<UserInfoItem>>(`${FriendRequestService.apiUrl()}`, params);
    } else {
      return this.httpClient.get<ListResponse<UserInfoItem>>(`${FriendRequestService.apiUrl()}`);
    }
  }

  addFriends(fromUserId: number, toUserId: number) : Observable<ObjectResponse<any>> {
    const params = {
      params: new HttpParams()
        .set('fromUserId', fromUserId.toString())
        .set('toUserId', toUserId.toString())
    };
    return this.httpClient.post<ObjectResponse<any>>(`${FriendRequestService.apiUrl()}`, null, params);
  }

  checkIfAreFriends(fromUserId: number, toUserId: number) : Observable<ObjectResponse<boolean>> {
    const params = {
      params: new HttpParams()
        .set('fromUserId', fromUserId.toString())
        .set('toUserId', toUserId.toString())
    };
    return this.httpClient.get<ObjectResponse<boolean>>(`${FriendRequestService.apiUrl()}/check`, params);
  }

  isFriend(userId: number) : Observable<ObjectResponse<boolean>> {
    const params = {
      params: new HttpParams()
        .set('userId', userId.toString())
    };
    return this.httpClient.get<ObjectResponse<boolean>>(`${FriendRequestService.apiUrl()}/is-friend`, params);
  }

  addFriend(userId: number) : Observable<ObjectResponse<any>> {
    const params = {
      params: new HttpParams()
        .set('userId', userId.toString())
    };
    return this.httpClient.post<ObjectResponse<any>>(`${FriendRequestService.apiUrl()}`, null, params);
  }
}
