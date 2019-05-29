import { Component, OnInit } from '@angular/core';
import {UserService} from '../service/user.service';
import {CookieService} from 'ngx-cookie-service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrls: ['./logout.component.css']
})
export class LogoutComponent implements OnInit {

  constructor(private userService: UserService, private cookieService: CookieService, private router: Router) { }

  ngOnInit() {
    this.userService.logout().subscribe();
    this.cookieService.delete('Authentication');
    this.router.navigate(['/home']);
  }

}
