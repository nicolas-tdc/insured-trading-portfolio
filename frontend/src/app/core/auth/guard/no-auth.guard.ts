import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from '../service';

/**
 * NoAuthGuard
 * 
 * @param route Route
 * @param state Auth state
 * @returns boolean
 * 
 * @export
 */
export const NoAuthGuard: CanActivateFn = (route, state) => {
  // Inject required services
  const authService = inject(AuthService);
  const router = inject(Router);

  // Check if the user is logged in
  if (authService.isLoggedIn()) {
    // If logged in, navigate to dashboard
    router.navigate(['/dashboard']);

    return false;
  }

  return true;
};
