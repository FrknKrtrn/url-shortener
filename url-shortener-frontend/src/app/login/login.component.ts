import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AppUser } from '../app-user';
import { LoginRequest } from '../login-request';
import { LoginService } from '../login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  request = new LoginRequest();
  appUser = new AppUser();
  invalidLogin = false
  message = '';

  constructor(private loginService: LoginService, private router: Router ) { }

  ngOnInit(): void {
  }

  onClickLogin() {
    this.loginService.authenticate(this.request).subscribe(
      data => {
        console.log('login is successful');
        this.appUser.id = data['id'];
        this.router.navigate([this.appUser.id, 'url-list']);
        this.invalidLogin = false
      },
      error => {
        console.log('cant login! [login.ts]');
        this.invalidLogin = true;
        this.message = 'Invalid email/password!';
      }
    );
  }

  goToSignUp() {
    this.router.navigate(['/sign-up']);
  }

}


/*
export class LoginComponent implements OnInit {

  request = new LoginRequest();
  username!: string;
  password!: string;
  message: any;

  constructor(private loginService: LoginService, private router: Router ) { }

  ngOnInit(): void {
  }

  onClickLogin() {
    this.loginService.login(this.username, this.password).subscribe(
      data => {
        console.log('login is successful');
        this.router.navigate(['/url-list']);
      },
      error => console.log('cant login!')
    );
  }

}
*/
