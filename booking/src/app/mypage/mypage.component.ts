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

}
