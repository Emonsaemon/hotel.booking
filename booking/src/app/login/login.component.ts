import { Component, OnInit } from '@angular/core';
import {CookieService} from 'ngx-cookie-service';
import {User} from '../model/user';
import {UserService} from '../service/user.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  user: User = new User(0, '', '', '', '', '');

  constructor( private cookieService: CookieService, private router: Router, private userService: UserService) { }

  ngOnInit() {
  }

  login() {
    this.userService.login(this.user).subscribe(
      data => {
        this.userService.set(data.token);
        console.log(data.token);
      }
    );
    window.location.href = 'http://localhost:4200/home';
  }

}
