import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AppUser } from '../app-user';
import { SignUpService } from '../sign-up.service';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.css']
})
export class SignUpComponent implements OnInit {

  user = new AppUser();
  message = '';

  constructor(private signUpService: SignUpService, private router: Router) { }

  ngOnInit(): void {
  }

  signUpUser() {
    this.signUpService.signUpUser(this.user).subscribe(
      data => {
        console.log(data);
        this.router.navigate(['/login']);
      },
      error => {
        console.log(error);
        this.message = 'Could not sign up!';
      }
    );
  }

}
