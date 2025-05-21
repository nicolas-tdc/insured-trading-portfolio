import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';
import { SignupRequest } from '../../models/auth-response.model';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent {
  form: SignupRequest = {
    firstName: '',
    lastName: '',
    email: '',
    password: '',
  };

  constructor(private authService: AuthService, private router: Router) {}

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
