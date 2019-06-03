import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {RoomInfo} from '../model/roomInfo';
import {RoomDetails} from '../model/roomDetails';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ImageService {

  constructor(private http: HttpClient) {
  }

  baseUrl: string = 'http://localhost:8080/rooms/';

  createImage(file: File, id: number): Observable<RoomDetails> {
    return this.http.post<RoomDetails>(this.baseUrl + id + "/images", file);
  }

  updateImage(file: File, id: number): Observable<RoomDetails> {
    return this.http.put<RoomDetails>(this.baseUrl + id + "/image", file);
  }

  deleteImage(id: number): Observable<RoomDetails> {
    return this.http.delete<RoomDetails>(this.baseUrl + id + "/images");
  }

}
