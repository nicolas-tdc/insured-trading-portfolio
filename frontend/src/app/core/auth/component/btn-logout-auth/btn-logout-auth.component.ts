import { Component } from '@angular/core';
import { AuthService } from '../../auth.service';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-btn-logout-auth',
  imports: [
    RouterLink,
  ],
  templateUrl: './btn-logout-auth.component.html',
  styleUrl: './btn-logout-auth.component.scss'
})
export class BtnLogoutAuthComponent {

  // Lifecycle

  constructor(
    private authService: AuthService,
  ) { }

  // API

  logout(): void {
    this.authService.logout();
  }
}
