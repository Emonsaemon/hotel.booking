import { Component, OnInit } from '@angular/core';
import {User} from '../model/user';
import {UserService} from '../service/user.service';
import {Reservation} from '../model/reservation';
import {ReservationService} from '../service/reservation.service';

@Component({
  selector: 'app-mypage',
  templateUrl: './mypage.component.html',
  styleUrls: ['./mypage.component.css']
})
export class MypageComponent implements OnInit {

  user: User = new User(0, '', '', '', '', '');
  reservations: Reservation[] = [];
  empty = true;
  password: string = '';
  rpassword: string = '';
  message = '';

  constructor(private userService: UserService, private reservationService: ReservationService) { }

  ngOnInit() {
    this.userService.findCurrent().subscribe(
      data => { this.user = data;
        console.log('User: ');
        console.log(this.user);
        console.log(this.user.id);
        this.reservationService.findByUserId(this.user.id).subscribe(
          data => { this.reservations = data;
            if (this.reservations !== []) {
              this.empty = false;
            }
          }
        );
      }
    );
  }

  onSubmit() {
    let pattern;
    this.message = '';
    if(this.password !== '') {
      if(this.password === this.rpassword) {
        if(this.password === this.user.email) {
          this.message = "Помилка! Пароль не може співпадати з поштою (логіном)";
          return false;
        }
        pattern = /[0-9]/;
        if(!pattern.test(this.password)) {
          this.message = "Помилка! Пароль має містити принаймні одну цифру (0-9)!";
          return false;
        }
        pattern = /[a-z]/;
        if(!pattern.test(this.password)) {
          this.message = "Помилка! Пароль має містити принаймні одну букву нижнього регістру (a-z)!";
          return false;
        }
        pattern = /[A-Z]/;
        if(!pattern.test(this.password)) {
          this.message = "Помилка! Пароль має містити принаймні одну букву верхнього регістру (A-Z)!";
          return false;
        }
        this.user.password = this.password;
      } else {
        this.message = "Помилка! Паролі не співпадають!";
        return false;
      }
    }

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

    this.userService.updateUser(this.user).subscribe();
  }

}
