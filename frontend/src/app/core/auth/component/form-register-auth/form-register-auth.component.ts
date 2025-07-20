import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../../service';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';

/**
 * FormRegisterAuthComponent
 * 
 * Form for registering a new user
 * 
 * @export
 */
@Component({
  selector: 'app-form-register-auth',
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatCardModule,
  ],
  templateUrl: './form-register-auth.component.html',
  styleUrls: ['./form-register-auth.component.scss'],
})
export class FormRegisterAuthComponent implements OnInit {

  /**
   * Register form group
   */
  public registerForm!: FormGroup;

  /**
   * Hide password flag
   */
  public hidePassword: boolean = true;

  /**
   * Success message
   */
  public successMessage: string = '';

  /**
   * Error message
   */
  public errorMessage: string = '';

  /**
   * Registered email
   */
  public registeredEmail: string = '';

  /**
   * Initializes the component
   * Injects required services for authentication
   * 
   * @param authService Service for authentication
   */
  constructor(
    private readonly authService: AuthService,
  ) { }

  /**
   * Lifecycle hook called on component initialization
   * Initializes register form
   */
  ngOnInit(): void {
    // Initialize register form
    this.registerForm = new FormGroup({
      firstName: new FormControl('', [
        Validators.required,
        Validators.maxLength(255),
        Validators.minLength(2),
      ]),
      lastName: new FormControl('', [
        Validators.required,
        Validators.maxLength(255),
        Validators.minLength(2),
      ]),
      email: new FormControl('', [
        Validators.required,
        Validators.email,
      ]),
      password: new FormControl('', [
        Validators.required,
        Validators.minLength(4),
        Validators.maxLength(32),
      ]),
    });
  }

  /**
   * Registers a new user
   * 
   * @returns void
   */
  register(): void {
    // Register using authentication service
    this.authService.register(this.registerForm.value).subscribe({
      next: res => {
        // On success, reset form and disable
        this.successMessage = res.message;
        this.registeredEmail = this.registerForm.value.email;
        this.registerForm.reset();
        this.registerForm.disable();
      },
      error: err => {
        this.errorMessage = err.message;
      },
    });
  }
}
