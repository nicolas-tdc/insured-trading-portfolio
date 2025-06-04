import { Routes } from '@angular/router';
import { AuthGuard } from './core/auth/guard/auth.guard';
import { NoAuthGuard } from './core/auth/guard/no-auth.guard';
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
        canActivate: [NoAuthGuard],
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
        path: 'account/:accountId',
        loadComponent: () => import('./features/account-page/account-page.component')
          .then(m => m.AccountPageComponent),
        canActivate: [AuthGuard],
      },
      {
        path: 'policy/:policyId',
        loadComponent: () => import('./features/policy-page/policy-page.component')
          .then(m => m.PolicyPageComponent),
        canActivate: [AuthGuard],
      }
    ]
  }
];
