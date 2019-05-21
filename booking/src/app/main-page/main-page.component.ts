import { Component, OnInit } from '@angular/core';
import {RoomService} from '../service/room.service';
import {RoomInfo} from '../model/roomInfo'

@Component({
	selector: 'app-main-page',
	templateUrl: './main-page.component.html',
	styleUrls: ['./main-page.component.css']
})
export class MainPageComponent implements OnInit {
	
	rooms: RoomInfo[];

	constructor(private roomService: RoomService) { }

	ngOnInit() {
		this.roomService.findAll()
      .subscribe(data => {
        this.rooms = data;
      });
	}

}
