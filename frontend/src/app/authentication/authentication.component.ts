import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';

@Component({
  selector: 'app-authentication',
  templateUrl: './authentication.component.html',
  styleUrl: './authentication.component.scss',
  imports: [
    LoginComponent,
    SignupComponent,
    ],
  providers: [
    HttpClient
  ],
})
export class AuthenticationComponent {

}
