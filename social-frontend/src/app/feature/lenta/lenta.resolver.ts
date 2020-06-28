import {Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {Observable} from "rxjs";
import {ListResponse} from "../../shared/response.model";
import {map} from "rxjs/operators";
import {WallPostListItem} from "../wall/wall.model";
import {WallService} from "../wall/wall.service";

// @ts-ignore
@Injectable({
  providedIn: 'root'
})
export class LentaResolver implements Resolve<WallPostListItem[]> {

  constructor(private wallService: WallService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<WallPostListItem[]> {
    // const user = route.params['userId'] ? route.params['userId'] : null;
      return this.wallService.getUserLenta()
        .pipe(
          map((resp: ListResponse<WallPostListItem>) => resp.data)
        )
  }

}
