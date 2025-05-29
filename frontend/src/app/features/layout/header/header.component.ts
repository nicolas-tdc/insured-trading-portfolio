import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BtnLogoutAuthComponent } from '../../../auth/component/btn-logout-auth/btn-logout-auth.component';
import { User } from '../../../auth/model';
import { AuthService } from '../../../auth/auth.service';

@Component({
  selector: 'app-header',
  imports: [
    CommonModule,
    BtnLogoutAuthComponent,
  ],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent {

  // Properties

  user: User | null = null;

  // Lifecycle

  constructor(private authService: AuthService) {
    this.authService.authUser.subscribe(data => {
      this.user = data;
    });
  }

  // API

  logout() {
    this.authService.logout();
  }
}
