import { Component, Signal } from '@angular/core';
import { AuthService } from '../../service';
import { CommonModule } from '@angular/common';
import { MatIcon } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { MatButton } from '@angular/material/button';
import { Router } from '@angular/router';
import { EntityService } from '../../../entity/service';
import { MatTooltipModule } from '@angular/material/tooltip';
import { User } from '../../model';

/**
 * UserHeaderComponent
 * 
 * Component for displaying user information and logout button
 * 
 * @export
 */
@Component({
  selector: 'app-user-header',
  standalone: true,
  imports: [
    CommonModule,
    MatIcon,
    MatCardModule,
    MatButton,
    MatTooltipModule,
  ],
  templateUrl: './user-header.component.html',
  styleUrl: './user-header.component.scss',
})
export class UserHeaderComponent {

  /**
   * Get the authenticated user
   * Provided by the authentication service
   * 
   * @returns Signal<User | null>
   */
  public get authUser(): Signal<User | null> { return this.authService.authUser; }

  /**
   * Check if the user is logged in
   * 
   * @returns boolean
   */
  public get isLoggedIn(): boolean { return this.authService.isLoggedIn(); }

  /**
   * Initializes the component
   * Injects required services for entities, routing and authentication
   * 
   * @param authService Service for authentication
   * @param router Service for routing
   * @param entityService Service for entities
   */
  constructor(
    private readonly authService: AuthService,
    private readonly router: Router,
    private readonly entityService: EntityService,
  ) { }

  /**
   * Logs out the user
   * 
   * @returns void
   */
  logout(): void {
    if (this.isLoggedIn) {
      // Logout using authentication service
      this.authService.logout();
    
      // Clear entities
      this.entityService.clearEntities();
    }
    // Navigate to authentication
    this.router.navigate(['/authentication']);
  }
}
