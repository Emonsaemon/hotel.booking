import {Component, OnInit} from '@angular/core';
import {CookieService} from 'ngx-cookie-service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  isLoggedIn = false;

  constructor(private cookieService: CookieService) {}

  ngOnInit() {
    this.checkLoggedIn();
  }

  checkLoggedIn() {
    this.isLoggedIn = this.cookieService.check('Authentication');
  }
}
