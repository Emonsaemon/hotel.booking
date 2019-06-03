import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {User} from '../model/user';
import {Observable} from 'rxjs';
import {findAll} from '@angular/compiler-cli/src/ngcc/src/utils';
import {CookieService} from 'ngx-cookie-service';
import {Token} from '../model/token';
import {UserRole} from "../model/UserRole";

@Injectable({
	providedIn: 'root'
})
export class UserService {
	
	constructor(private http: HttpClient, private cookieService: CookieService) {
	}
	
	baseUrl: string = 'http://localhost:8080/users/';
  authUrl: string = 'http://localhost:8080/authentication/';

	findAll(): Observable<User[]> {
		return this.http.get<User[]>(this.baseUrl);
	}

	findById(id: number): Observable<User> {
		return this.http.get<User>(this.baseUrl + id);
	}

	findCurrent(): Observable<User> {
    return this.http.get<User>((this.authUrl + "current"));
  }

	signup(user: User): Observable<Token> {
    return this.http.post<Token>(this.authUrl + "signup", user);
  }

  login(user: User): Observable<Token> {
    return this.http.post<Token>(this.authUrl + "signin", user);
  }

  logout(): Observable<any> {
	  return this.http.get<any>(this.authUrl + "logout");
  }
  
	createUser(user: User): Observable<User> {
		return this.http.post<User>(this.baseUrl + "create", user);
	}
	
	updateUser(user: User): Observable<User> {
		return this.http.put<User>(this.baseUrl + "update", user);
	}

	deleteRoute(id: number): Observable<User> {
		return this.http.delete<User>(this.baseUrl + "delete/" + id);
	}

	checkAdmin(): Observable<UserRole> {
    return this.http.get<UserRole>(this.baseUrl + "isadmin");
  }

  set(token: string) {
    this.cookieService.set('Authentication', token);
    alert(this.cookieService.get('Authentication'));
  }

  get() {
    alert(this.cookieService.get('Authentication'));
    return this.cookieService.get('Authentication');
  }

  del() {
    alert(this.cookieService.delete('Authentication'));
  }

}
