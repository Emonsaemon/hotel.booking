import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {RoomService} from '../service/room.service';
import {RoomDetails} from '../model/roomDetails'


@Component({
  selector: 'app-room-details',
  templateUrl: './room-details.component.html',
  styleUrls: ['./room-details.component.css']
})
export class RoomDetailsComponent implements OnInit {

  room: RoomDetails = new RoomDetails(0, 0, 0, [], 0, 0, '');
  id: number;

  constructor(private activatedRoute: ActivatedRoute, private router: Router, private roomService: RoomService) {
  }

  ngOnInit() {
	  this.activatedRoute.paramMap.subscribe(params => {
        this.id = +params.get('id');
      });
	  this.roomService.findById(this.id).subscribe(value => this.room = value);
  }
}
