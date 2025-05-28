import { Routes } from '@angular/router';

import { AuthGuard } from './core/guards/auth.guard';

import { ShellComponent } from './layout/shell/shell.component';

export const routes: Routes = [
  {
    path: '',
    component: ShellComponent,
    children: [
      {
        path: 'authentication',
        loadComponent: () => import('./features/authentication/authentication.component')
          .then(m => m.AuthenticationComponent),
      },
      {
        path: '',
        redirectTo: '/dashboard',
        pathMatch: 'full',
      },
      {
        path: 'dashboard',
        loadComponent: () => import('./features/dashboard/dashboard.component')
          .then(m => m.DashboardComponent),
        canActivate: [AuthGuard],
      },
      {
        path: 'accounts/:accountId',
        loadComponent: () => import('./features/account-details/account-details.component')
          .then(m => m.AccountDetailsComponent),
        canActivate: [AuthGuard]
      },
      {
        path: 'policies/:policyId',
        loadComponent: () => import('./features/policy-details/policy-details.component')
          .then(m => m.PolicyDetailsComponent),
        canActivate: [AuthGuard]
      }
    ]
  }
];
