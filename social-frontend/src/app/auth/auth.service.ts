import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";


import {Router} from '@angular/router';
import {ObjectResponse} from "../shared/response.model";
import {catchError, map} from "rxjs/operators";
import {Observable} from "rxjs";


@Injectable(
  {providedIn: 'root'}
)
export class AuthService {


  get isLoggedIn() {
    return this.checkLoggedIn();
  }


  constructor(private httpClient: HttpClient, protected router: Router) {
  }

  public login(username: string, password: string): Observable<Object> {

    let body = new FormData();
    body.append('username', username);
    body.append('password', password);
    return this.httpClient.post(
      "/api/login",
      body,
      {});
  }

  private checkLoggedIn() {
    return this.httpClient.get<ObjectResponse<any>>(
      "/api/security")
      .pipe(
        map(next => next.result === "success", error => false),
        catchError(err => Observable.throw(err))
      )
  }

  public logout() {
    this.httpClient.get("/api/logout").subscribe(() => {
      this.toLoginPage();
    })
  }

  private toLoginPage() {
    this.router.navigate(['signin'])
  }

}
