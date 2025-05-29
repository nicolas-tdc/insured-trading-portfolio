import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { SignupRequest } from '../../model';
import { AuthService } from '../../auth.service';

@Component({
  selector: 'app-signup',
  imports: [
    CommonModule,
    FormsModule
  ],
  templateUrl: './form-signup-auth.component.html',
  styleUrls: ['./form-signup-auth.component.scss'],
})
export class SignupComponent {

  // Properties

  private _form: SignupRequest = {
    firstName: '',
    lastName: '',
    email: '',
    password: '',
  };

  // Accessors

  set firstName(value: string) { this._form.firstName = value; }
  set lastName(value: string) { this._form.lastName = value; }
  set email(value: string) { this._form.email = value; }
  set password(value: string) { this._form.password = value; }

  get firstName() { return this._form.firstName; }
  get lastName() { return this._form.lastName; }
  get email() { return this._form.email; }
  get password() { return this._form.password; }

  // Lifecycle

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  // API

  onSubmit(): void {
    this.authService.register(this._form).subscribe({
      next: res => {
        this.router.navigate(['/login']);
      },
      error: err => {
        console.error(err);
      },
    });
  }
}
