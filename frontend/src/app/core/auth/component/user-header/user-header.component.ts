import { Component } from '@angular/core';
import { AuthService } from '../../auth.service';
import { CommonModule } from '@angular/common';
import { MatIcon } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { MatButton } from '@angular/material/button';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-header',
  standalone: true,
  imports: [
    CommonModule,
    MatIcon,
    MatCardModule,
    MatButton,
  ],
  templateUrl: './user-header.component.html',
  styleUrl: './user-header.component.scss',
})
export class UserHeaderComponent {

  // Properties

  public get authUser() {
    return this.authService.authUser;
  }

  // Lifecycle

  constructor(
    private authService: AuthService,
    private router: Router,
  ) { }

  // Authentication

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/authentication']);
  }
}
