import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MainPageComponent } from './main-page/main-page.component';
import { AlertModule } from 'ngx-bootstrap';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import { RoomDetailsComponent } from './room-details/room-details.component';
import { BookingComponent } from './booking/booking.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { RegistrationComponent } from './registration/registration.component';
import { CookieService } from 'ngx-cookie-service';
import { LoginComponent } from './login/login.component';
import {AuthService} from './interceptors/auth.service';

@NgModule({
  declarations: [
    AppComponent,
    MainPageComponent,
    RoomDetailsComponent,
    BookingComponent,
    RegistrationComponent,
    LoginComponent
  ],
  imports: [
    AlertModule.forRoot(),
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule
  ],
  providers: [CookieService, { provide: HTTP_INTERCEPTORS, useClass: AuthService, multi: true }],
  bootstrap: [AppComponent]
})
export class AppModule { }
