import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { LoginRequest } from '../../model';
import { AuthService } from '../../auth.service';

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

  // Properties, Accessors

  // Form
  private _form: LoginRequest = {
    email: '',
    password: '',
  };

  get email() { return this._form.email; }
  set email(value: string) { this._form.email = value; }

  get password() { return this._form.password; }
  set password(value: string) { this._form.password = value; }

  // Lifecycle

  constructor(
    private router: Router,
    private authService: AuthService,
  ) { }

  // API

  onSubmit(): void {
    this.authService.login(this._form).subscribe({
      next: () => {
        this.router.navigate(['/dashboard']);
      },
      error: err => {
        console.error(err);
      },
    });
  }
}
