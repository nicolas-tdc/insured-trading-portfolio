import { Component } from '@angular/core';
import { AuthService } from '../../service';
import { CommonModule } from '@angular/common';
import { MatIcon } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { MatButton } from '@angular/material/button';
import { Router } from '@angular/router';
import { EntityService } from '../../../entity/service';

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
    private entityService: EntityService,
  ) { }

  // Authentication

  logout(): void {
    this.authService.logout();
    this.entityService.clearEntities();
    this.router.navigate(['/authentication']);
  }
}
