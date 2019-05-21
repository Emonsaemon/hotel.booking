import {Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {RoomService} from '../service/room.service';
import {RoomDetails} from '../model/roomDetails';
import {Reservation} from '../model/reservation';
import {ReservationService} from '../service/reservation.service';

@Component({
  selector: 'app-booking',
  templateUrl: './booking.component.html',
  styleUrls: ['./booking.component.css']
})
export class BookingComponent implements OnInit {

  oneDay = 1000 * 60 * 60 * 24;
  bookings: Reservation[] = [];
  bookingDays: Date[] = [];
  unavailableDays: Date[] = [];
  reservation: Reservation = new Reservation(0, 0, 0, new Date(), new Date(), 0);
  room: number;
  isValid: boolean = true;

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private roomService: RoomService,
                private reservationService: ReservationService) {
  }

  ngOnInit() {
    this.activatedRoute.paramMap.subscribe(params => {
        this.room = +params.get('roomId');
      });
    this.reservationService.findByRoomId(this.room)
      .subscribe(data => {
        this.bookings = data;
        console.log(this.bookings);
      });
  }

  validateData(){
    this.isValid = true;
    this.unavailableDays = [];
    this.bookingDays = [];

    for (let i = 0; i < this.bookings.length; i++) {
      let beginDate = new Date(this.bookings[i].startDate);
      const endDate = new Date(this.bookings[i].endDate);
      while (endDate.getTime() >= beginDate.getTime()) {
        this.unavailableDays.push(beginDate);
        beginDate = new Date(beginDate.getTime() + this.oneDay);
      }
    }
    console.log(this.unavailableDays);

    let beginDate = new Date(this.reservation.startDate);
    const finishDate = new Date(this.reservation.endDate);
    console.log(beginDate);
    while (finishDate.getTime() >= beginDate.getTime()) {
      this.bookingDays.push(beginDate);
      beginDate = new Date(beginDate.getTime() + this.oneDay);
    }
    console.log(this.bookingDays);
    for (let i = 0; i < this.bookingDays.length; i++) {
      for (let j = 0; j < this.unavailableDays.length; j++) {
        if (this.bookingDays[i].getTime() === this.unavailableDays[j].getTime()) {
          this.isValid = false;
          console.log("Days are already booked");
          return;
        }
      }
    }
    this.createReservation();
    console.log("Create reservation");
  }

  createReservation() {
    this.reservation.room = this.room;
    this.reservationService.createReservation(this.reservation).subscribe();
    this.router.navigate(['/home']);
  }
}
