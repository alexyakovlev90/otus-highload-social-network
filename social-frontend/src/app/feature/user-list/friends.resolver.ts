import {Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {UserInfoItem} from "../user-page/user.model";
import {Observable} from "rxjs";
import {UserService} from "../user-page/user.service";
import {ListResponse} from "../../shared/response.model";
import {map} from "rxjs/operators";
import {FriendRequestService} from "../friend-request/friend-request.service";

// @ts-ignore
@Injectable({
  providedIn: 'root'
})
export class FriendsResolver implements Resolve<UserInfoItem[]> {

  constructor(private friendRequestService: FriendRequestService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<UserInfoItem[]> {
    const user = route.params['userId'] ? route.params['userId'] : null;
      return this.friendRequestService.getUserFriends(user)
        .pipe(
          map((resp: ListResponse<UserInfoItem>) => resp.data)
        )
  }

}
