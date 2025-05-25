import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import {
  AuthService,
  SignupRequest,
} from '../../core';

@Component({
  standalone: true,
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css'],
  imports: [CommonModule, FormsModule],
})
export class SignupComponent {
  form: SignupRequest = {
    firstName: '',
    lastName: '',
    email: '',
    password: '',
  };

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  onSubmit(): void {
    this.authService.register(this.form).subscribe({
      next: res => {
        this.router.navigate(['/login']);
      },
      error: err => {
        console.error(err);
      },
    });
  }
}
