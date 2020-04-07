import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {Observable} from 'rxjs/Rx';


import {Router} from '@angular/router';


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



  private checkLoggedIn(){
    return this.httpClient.get(
      "/api/security")
      .catch(err => Observable.of(false))
      .map(next => true, error => false);
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
