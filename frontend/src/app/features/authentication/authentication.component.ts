import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { LoginComponent } from '../../components/auth/form-login-auth/form-login-auth.component';
import { SignupComponent } from '../../components/auth/form-signup-auth/form-signup-auth.component';

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
