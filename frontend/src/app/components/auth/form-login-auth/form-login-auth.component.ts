import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import {
  AuthService,
  LoginRequest,
} from '../../../core';

@Component({
  selector: 'app-login',
  imports: [
    CommonModule,
    FormsModule,
  ],
  templateUrl: './form-login-auth.component.html',
  styleUrls: ['./form-login-auth.component.scss'],
})
export class LoginComponent {

  // Properties

  private _form: LoginRequest = {
    email: '',
    password: '',
  };

  // Accessors

  set email(value: string) { this._form.email = value; }
  set password(value: string) { this._form.password = value; }

  get email() { return this._form.email; }
  get password() { return this._form.password; }

  // Lifecycle

  constructor(
    private router: Router,
    private authService: AuthService,
  ) { }

  // API

  onSubmit(): void {
    this.authService.login(this._form).subscribe({
      next: data => {
        this.authService.saveToken(data.token);
        this.authService.saveUser(data);

        this.router.navigate(['/dashboard']);
      },
      error: err => {
        console.error(err);
      },
    });
  }
}
