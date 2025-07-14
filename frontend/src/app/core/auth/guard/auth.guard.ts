import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from '../service';

/**
 * AuthGuard
 * 
 * @param route Route
 * @param state Auth state
 * @returns boolean
 * 
 * @export
 */
export const AuthGuard: CanActivateFn = (route, state) => {
  // Inject required services
  const authService = inject(AuthService);
  const router = inject(Router);

  // Check if the user is logged in
  if (authService.isLoggedIn()) { return true; }

  // If not logged in, navigate to authentication
  router.navigate(['/authentication']);

  return false;
};
