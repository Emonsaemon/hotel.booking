import {Component, OnInit} from '@angular/core';
import {CookieService} from 'ngx-cookie-service';
import {UserRole} from "./model/UserRole";
import {UserService} from "./service/user.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  isLoggedIn = false;
  isAdmin = false;
  role: UserRole;

  constructor(private cookieService: CookieService,
              private userService: UserService) {}

  ngOnInit() {
    this.checkLoggedIn();
  }

  checkLoggedIn() {
    this.isLoggedIn = this.cookieService.check('Authentication');
    if (this.isLoggedIn) {
      this.checkAdmin();
    } else {
      this.isAdmin = false;
    }
  }

  checkAdmin() {
    this.userService.checkAdmin().subscribe(
      data => {
        this.role = data;
        this.isAdmin = (this.role == UserRole.ADMIN);
      }
    );
  }

  delete() {}
}
