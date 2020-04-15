import {Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot} from "@angular/router";
import {UserInfoItem} from "./user.model";
import {Observable} from "rxjs";
import {UserService} from "./user.service";
import {ObjectResponse} from "../../shared/response.model";
import {map} from "rxjs/operators";
import {AuthService} from "../../auth/auth.service";

@Injectable({
  providedIn: 'root'
})
export class UserResolver implements Resolve<UserInfoItem> {

  constructor(private userService: UserService, private austService: AuthService, private router: Router) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<UserInfoItem> {
    const login = route.params['login'] ? route.params['login'] : null;
    if (!login) {
      // return this.userService.getById(1)
      //   .pipe(
      //     map((resp: ObjectResponse<UserInfoItem>) => resp.data)
      //   )
      return this.austService.getAuthUser()
        .pipe(
          map((resp: ObjectResponse<UserInfoItem>) => resp.data)
        )
    } else {
      return this.userService.getByLogin(login)
        .pipe(
          map((resp: ObjectResponse<UserInfoItem>) => resp.data)
        )
    }
  }

}
