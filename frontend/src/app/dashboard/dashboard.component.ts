import { Component } from '@angular/core';
import { AuthService } from '../auth/auth.service';
import { User } from '../models/user.model';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent {
  // Non-null assertion
  user!: User;

  constructor(private authService: AuthService) {}

  ngOnInit(): void {
    const currentUser = this.authService.getUser();
    if (!currentUser) {
      throw new Error('User not found in dashboard');
    }
    this.user = currentUser;
  }

  logout(): void {
    this.authService.logout();
    window.location.reload();
  }
}
