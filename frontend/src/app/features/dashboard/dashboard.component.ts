import { Component, OnDestroy, OnInit } from '@angular/core';
import { PoliciesListComponent } from '../../core/policy/component/policies-list/policies-list.component';
import { AccountsListComponent } from '../../core/account/component/accounts-list/accounts-list.component';
import { MatButtonModule } from '@angular/material/button';
import { UserAccountsService } from '../../core/account/service';
import { UserPoliciesService } from '../../core/policy/service';
import { MatCardModule } from '@angular/material/card';
import { MatDividerModule } from '@angular/material/divider';
import { AuthService } from '../../core/auth/service';
import { Router } from '@angular/router';
import { AccountsListHeaderComponent } from '../../core/account/component/accounts-list-header/accounts-list-header.component';
import { PoliciesListHeaderComponent } from '../../core/policy/component/policies-list-header/policies-list-header.component';

/**
 * DashboardComponent
 *
 * Displays a list of user accounts and policies.
 * Provides buttons to create new accounts and policies.
 * 
 * @export
 */
@Component({
  selector: 'app-dashboard',
  imports: [
    AccountsListComponent,
    AccountsListHeaderComponent,
    PoliciesListComponent,
    PoliciesListHeaderComponent,
    MatCardModule,
    MatButtonModule,
    MatDividerModule
  ],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent implements OnInit, OnDestroy {

  /**
   * Initializes the component.
   * Injects required services for accounts and policies data.
   * 
   * @param userAccountsService Service for user accounts
   * @param userPoliciesService Service for user policies
   * @param authService Service for authentication
   * @param router Service for routing
   * @param dialog Service for dialog
   */
  constructor(
    private readonly userAccountsService: UserAccountsService,
    private readonly userPoliciesService: UserPoliciesService,
    private readonly authService: AuthService,
    private readonly router: Router,
  ) { }

  /**
   * Lifecycle hook called on component initialization.
   * Reloads user accounts and policies data.
   * 
   * @return void
   */
  ngOnInit(): void {
    // Redirect to authentication page if not logged in
    if (!this.authService.isLoggedIn()) {
      this.router.navigate(['/authentication']);
    }

    // Reload user accounts
    this.userAccountsService.reloadUserAccounts();

    // Reload user policies
    this.userPoliciesService.reloadUserPolicies();
  }

  /**
   * Lifecycle hook called on component destruction.
   * Clears user accounts and policies data.
   * 
   * @return void
   */
  ngOnDestroy(): void {
    // Clear user accounts
    this.userAccountsService.clearUserAccounts();

    // Clear user policies
    this.userPoliciesService.clearUserPolicies();
  }
}