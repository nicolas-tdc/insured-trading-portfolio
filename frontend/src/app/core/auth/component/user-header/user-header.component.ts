import { Component } from '@angular/core';
import { AuthService } from '../../auth.service';
import { BtnLogoutAuthComponent } from '../btn-logout-auth/btn-logout-auth.component';
import { RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-user-header',
  standalone: true,
  imports: [
    CommonModule,
    BtnLogoutAuthComponent,
  ],
  templateUrl: './user-header.component.html',
  styleUrl: './user-header.component.scss',
})
export class UserHeaderComponent {

  // Properties, Accessors

  public get authUser() {
    return this.authService.authUser();
  }

  // Lifecycle

  constructor(
    private authService: AuthService
  ) { }
}
