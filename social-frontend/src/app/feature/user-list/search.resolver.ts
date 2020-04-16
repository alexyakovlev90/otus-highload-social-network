import {Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {UserInfoItem} from "../user-page/user.model";
import {Observable} from "rxjs";
import {UserService} from "../user-page/user.service";
import {ListResponse} from "../../shared/response.model";
import {map} from "rxjs/operators";

// @ts-ignore
@Injectable({
  providedIn: 'root'
})
export class SearchResolver implements Resolve<UserInfoItem[]> {

  constructor(private userService: UserService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<UserInfoItem[]> {
    return this.userService.getAll()
      .pipe(
        map((resp: ListResponse<UserInfoItem>) => resp.data)
      )
  }

}
