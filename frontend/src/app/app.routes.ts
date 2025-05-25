import { Routes } from '@angular/router';

import { AuthGuard } from './core/guards/auth.guard';

import { AuthenticationComponent } from './authentication/authentication.component';
import { DashboardComponent } from './dashboard/dashboard.component';

export const routes: Routes = [
  {
    path: 'authentication',
    component: AuthenticationComponent,
  },
  {
    path: 'dashboard',
    component: DashboardComponent,
    canActivate: [AuthGuard],
  },
  {
    path: '',
    redirectTo: '/dashboard',
    pathMatch: 'full',
  },
];
