import { Component, OnInit } from '@angular/core';
import {User} from '../model/user';
import {UserService} from '../service/user.service';
import {Router} from '@angular/router';
import {Token} from '../model/token';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  user: User = new User(0, '', '', '', '', '');
  rpassword: string;
  message: string = '';
  token: Token = new Token('invalid');

  constructor(private router: Router, private userService: UserService) { }

  ngOnInit() {
  }

  submit() {
    var pattern;

    pattern = /[A-Z][A-Za-z]{2,30}/;
    if(!pattern.test(this.user.surname) || !pattern.test(this.user.name)) {
      this.message = "Помилка! Ім'я та прізвіще мають складатися лише з букв і почитнатися з великої!";
      return false;
    }

    pattern = /[A-Za-z0-9]+@[a-z]+\.[a-z]+/;
    if(!pattern.test(this.user.email)) {
      this.message = "Помилка! Невірно вказана пошта!";
      return false;
    }
    if(this.user.password === this.rpassword) {
      if(this.user.password === this.user.email) {
        this.message = "Помилка! Пароль не може співпадати з поштою (логіном)";
        return false;
      }
      pattern = /[0-9]/;
      if(!pattern.test(this.user.password)) {
        this.message = "Помилка! Пароль має містити принаймні одну цифру (0-9)!";
        return false;
      }
      pattern = /[a-z]/;
      if(!pattern.test(this.user.password)) {
        this.message = "Помилка! Пароль має містити принаймні одну букву нижнього регістру (a-z)!";
        return false;
      }
      pattern = /[A-Z]/;
      if(!pattern.test(this.user.password)) {
        this.message = "Помилка! Пароль має містити принаймні одну букву верхнього регістру (A-Z)!";
        return false;
      }
    } else {
      this.message = "Помилка! Паролі не співпадають!";
      return false;
    }
    this.userService.signup(this.user).subscribe(data => {
      console.log(data.token);
      this.userService.set(data.token);
    });
    window.location.href = 'http://localhost:4200/home';
  }
}
