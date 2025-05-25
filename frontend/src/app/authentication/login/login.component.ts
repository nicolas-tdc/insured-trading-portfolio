import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import {
  AuthService,
  LoginRequest,
} from '../../core';

@Component({
  standalone: true,
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  imports: [
    CommonModule,
    FormsModule,
  ],
})
export class LoginComponent {
  form: LoginRequest = {
    email: '',
    password: '',
  };

  constructor(
    private router: Router,
    private authService: AuthService,
  ) { }

  onSubmit(): void {
    this.authService.login(this.form).subscribe({
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
