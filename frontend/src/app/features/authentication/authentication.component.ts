import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormLoginAuthComponent } from '../../core/auth/component/form-login-auth/form-login-auth.component';
import { FormRegisterAuthComponent } from '../../core/auth/component/form-register-auth/form-register-auth.component';

@Component({
  selector: 'app-authentication',
  templateUrl: './authentication.component.html',
  styleUrl: './authentication.component.scss',
  imports: [
    FormLoginAuthComponent,
    FormRegisterAuthComponent,
  ],
  providers: [
    HttpClient
  ],
})
export class AuthenticationComponent {

}
