import {Component, OnDestroy, OnInit} from '@angular/core';
import {NgForm} from '@angular/forms'
import {ActivatedRoute, Router} from '@angular/router';
import {AuthService} from "../../auth/auth.service";
import {Subscription} from "rxjs";


@Component({
  selector: 'app-signin',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.css'],
})
export class SignInComponent implements OnInit, OnDestroy {

  login: string;
  password: string;
  error: string;
  loginSubscription: Subscription;

  constructor(private authService: AuthService, protected route: ActivatedRoute,
              protected router: Router) {
  }

  ngOnInit() {
  }

  ngOnDestroy() {
    if (this.loginSubscription) {
      this.loginSubscription.unsubscribe();
    }
  }

  doSignin($event, form: NgForm) {
    this.error = '';
    this.loginSubscription = this.authService.login(this.login, this.password)
      .subscribe(next => {
        this.router.navigate(["/"], {queryParams: null});
      }, errorResponce => {
        this.error = errorResponce.error.msg;
      });
  }

  doSignUp() {
    this.router.navigate(["/signup"], {queryParams: null});
  }
}
