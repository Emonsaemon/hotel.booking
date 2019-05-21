import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {RoomInfo} from '../model/roomInfo';
import {RoomDetails} from '../model/roomDetails';
import {Observable} from 'rxjs';

@Injectable({
	providedIn: 'root'
})
export class RoomService {
	
	constructor(private http: HttpClient) {
	}
	
	baseUrl: string = 'http://localhost:8080/rooms/';

	findAll(): Observable<RoomInfo[]> {
		return this.http.get<RoomInfo[]>(this.baseUrl);
	}

	findById(id: number): Observable<RoomDetails> {
		return this.http.get<RoomDetails>(this.baseUrl + id);
	}
	
	createRoom(room: RoomDetails): Observable<RoomDetails> {
		return this.http.post<RoomDetails>(this.baseUrl + "create", room);
	}
	
	updateRoom(room: RoomDetails): Observable<RoomDetails> {
		return this.http.put<RoomDetails>(this.baseUrl + "update", room);
	}

	deleteRoute(id: number): Observable<RoomDetails> {
		return this.http.delete<RoomDetails>(this.baseUrl + "delete/" + id);
	}

}