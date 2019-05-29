import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Reservation} from '../model/reservation';
import {Observable} from 'rxjs';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
	providedIn: 'root'
})
export class ReservationService {
	
	constructor(private http: HttpClient, private cookieService: CookieService) {
	}

	baseUrl: string = 'http://localhost:8080/reservations/';

  // createAuthorizationHeader(headers: HttpHeaders): HttpHeaders {
  //   console.log(this.cookieService.get('Authentication'));
  //   if (this.cookieService.check('Authentication')) {
  //     headers.append('Authorization', this.cookieService.get('Authentication'));
  //   }
  //   return headers;
  // }

	findAll(): Observable<Reservation[]> {
		return this.http.get<Reservation[]>(this.baseUrl);
	}

	findById(id: number): Observable<Reservation> {
		return this.http.get<Reservation>(this.baseUrl + id);
	}
	
	createReservation(reservation: Reservation): Observable<Reservation> {
		return this.http.post<Reservation>(this.baseUrl + "create", reservation);
	}
	
	updateReservation(reservation: Reservation): Observable<Reservation> {
		return this.http.put<Reservation>(this.baseUrl + "update", reservation);
	}

	deleteRoute(id: number): Observable<Reservation> {
		return this.http.delete<Reservation>(this.baseUrl + "delete/" + id);
	}

	findByRoomId(id: number): Observable<Reservation[]> {
	  return this.http.get<Reservation[]>(this.baseUrl + "reservation/" + id);
  }

  findByUserId(id: number): Observable<Reservation[]> {
    return this.http.get<Reservation[]>(this.baseUrl + "user/" + id);
	}

}
