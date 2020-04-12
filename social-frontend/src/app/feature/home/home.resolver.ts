import {Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {UserInfoItem} from "../user/user.model";
import {Observable} from "rxjs";
import {UserService} from "../user/user.service";
import {ObjectResponse} from "../../shared/response.model";
import {map} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class HomeResolver implements Resolve<UserInfoItem> {

  constructor(private userService: UserService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<UserInfoItem> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (!id) {
      return this.userService.getById(1)
        .pipe(
          map((resp: ObjectResponse<UserInfoItem>) => resp.data)
        )
    }
  }

}
