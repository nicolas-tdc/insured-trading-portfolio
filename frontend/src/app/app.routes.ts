import { Routes } from '@angular/router';

import { AuthGuard } from './core/auth/auth.guard';

import { ShellComponent } from './features/layout/shell/shell.component';

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
        loadComponent: () => import('./features/account-page/account-page.component')
          .then(m => m.AccountPageComponent),
        canActivate: [AuthGuard]
      },
      {
        path: 'policies/:policyId',
        loadComponent: () => import('./features/policy-page/policy-page.component')
          .then(m => m.PolicyPageComponent),
        canActivate: [AuthGuard]
      }
    ]
  }
];
