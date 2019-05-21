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

  user: User;

  constructor( private cookieService: CookieService, private router: Router, private userService: UserService) { }

  ngOnInit() {
  }

  login() {
    this.userService.login(this.user).subscribe();
    this.router.navigate(['/home']);
  }

}
