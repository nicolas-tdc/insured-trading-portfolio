import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../../service';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';

/**
 * FormLoginAuthComponent
 * 
 * Form for logging in
 * 
 * @export
 */
@Component({
  selector: 'app-form-login-auth',
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatButtonModule,
    MatInputModule,
    MatIconModule,
    MatCardModule,
  ],
  templateUrl: './form-login-auth.component.html',
  styleUrls: ['./form-login-auth.component.scss'],
})
export class FormLoginAuthComponent implements OnInit {

  /**
   * Login form group
   */
  public loginForm!: FormGroup;

  /**
   * Hide password flag
   */
  public hidePassword: boolean = true;

  /**
   * Error message
   */
  public errorMessage: string = '';

  /**
   * Initializes the component
   * Injects required services for routing and authentication
   * 
   * @param router 
   * @param authService 
   */
  constructor(
    private readonly router: Router,
    private readonly authService: AuthService,
  ) { }

  /**
   * Lifecycle hook called on component initialization
   * Initializes login form
   */
  ngOnInit(): void {
    // Initialize login form
    this.loginForm = new FormGroup({
      email: new FormControl('', [
        Validators.required,
        Validators.email,
        Validators.maxLength(255),
      ]),
      password: new FormControl('', [
        Validators.required,
        Validators.minLength(4),
        Validators.maxLength(32),
      ]),
    });
  }

  /**
   * Logs in the user
   * 
   * @returns void
   */
  login(): void {
    // Login using authentication service
    this.authService.login(this.loginForm.value).subscribe({
      next: () => {
        // On success, reset form and navigate to dashboard
        this.loginForm.reset();
        this.router.navigate(['/dashboard']);
      },
      error: err => {
        this.errorMessage = err.message;
      },
    });
  }
}
