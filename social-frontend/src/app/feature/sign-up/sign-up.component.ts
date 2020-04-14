import {Component, OnInit} from '@angular/core';
import {UserUpdateItem} from "../user-page/user.model";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {UserService} from "../user-page/user.service";

@Component({
  selector: 'app-reg-page',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {

  newUser: UserUpdateItem;

  form: FormGroup;

  constructor(private route: ActivatedRoute, private router: Router,
              private userService: UserService, private formBuilder: FormBuilder) {
  }

  ngOnInit(): void {
    this.newUser = <UserUpdateItem>{
      login: 'ivan777',
      password: 'Strong_password',
      firstName: 'Ivan',
      lastName: 'Ivanoff',
      age: 25,
      sex: true,
      interest: 'Sex, Drugs and Rock\'n\'Roll',
      city: 'Moscow'
    };

    this.form = this.formBuilder.group(USER_UPDATE_ITEM);
    this.form.patchValue(this.newUser);
  }

  doRegister() {
    this.userService.save(this.newUser).subscribe(resp => {
      var login = resp.data.login;
        this.router.navigate(['/user/' + login], {queryParams: null})
      }
    )
  }
}

export const USER_UPDATE_ITEM = {
  login: [null, [Validators.required]],
  password: [null, [Validators.required]],
  firstName: [null, [Validators.required]],
  lastName: [null, [Validators.required]],
  age: null,
  sex: null,
  interest: null,
  city: null
};
