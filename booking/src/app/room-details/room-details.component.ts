import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {RoomService} from '../service/room.service';
import {RoomDetails} from '../model/roomDetails'
import {UserService} from "../service/user.service";
import {UserRole} from "../model/UserRole";


@Component({
  selector: 'app-room-details',
  templateUrl: './room-details.component.html',
  styleUrls: ['./room-details.component.css']
})
export class RoomDetailsComponent implements OnInit {

  room: RoomDetails = new RoomDetails(0, 0, 0, [], 0, 0, '');
  id: number;
  isAdmin = false;

  constructor(private activatedRoute: ActivatedRoute,
              private router: Router,
              private roomService: RoomService,
              private userService: UserService) {
  }

  ngOnInit() {
	  this.activatedRoute.paramMap.subscribe(params => {
        this.id = +params.get('id');
      });
	  this.roomService.findById(this.id).subscribe(value => this.room = value);
    this.checkAdmin();
  }

  checkAdmin() {
    this.userService.checkAdmin().subscribe(
      data => {
        this.isAdmin = (data == UserRole.ADMIN);
      }
    );
  }

  delete() {
    this.roomService.deleteRoute(this.id).subscribe();
    window.location.href = 'http://localhost:4200/home';
  }
}
