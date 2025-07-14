import { Component, OnDestroy, OnInit } from '@angular/core';
import { CardListPoliciesComponent } from '../../core/policy/component/card-list-policies/card-list-policies.component';
import { AccountsListComponent } from '../../core/account/component/accounts-list/accounts-list.component';
import { FormCreateAccountComponent } from '../../core/account/component/form-create-account/form-create-account.component';
import { FormCreatePolicyComponent } from '../../core/policy/component/form-create-policy/form-create-policy.component';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { UserAccountsService } from '../../core/account/service';
import { UserPoliciesService } from '../../core/policy/service';
import { MatCardModule } from '@angular/material/card';
import { MatDividerModule } from '@angular/material/divider';
import { AuthService } from '../../core/auth/service';
import { Router } from '@angular/router';

/**
 * DashboardComponent
 *
 * Displays a list of user accounts and policies.
 * Provides buttons to create new accounts and policies.
 */
@Component({
  selector: 'app-dashboard',
  imports: [
    AccountsListComponent,
    CardListPoliciesComponent,
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
    private readonly dialog: MatDialog,
  ) { }

  /**
   * Lifecycle hook called on component initialization.
   * Reloads user accounts and policies data.
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
   */
  ngOnDestroy(): void {
    // Clear user accounts
    this.userAccountsService.clearUserAccounts();

    // Clear user policies
    this.userPoliciesService.clearUserPolicies();
  }

  /**
   * Opens a dialog form to create a new account.
   * 
   * @returns void
   */
  openCreateAccountFormDialog(): void {
    // Open the policy creation dialog form
    const dialogRef: MatDialogRef<FormCreateAccountComponent> = this.dialog.open(
      FormCreateAccountComponent,
      {
        width: '600px',
        height: '500px',
      }
    );

    // Reload user accounts on form completion
    dialogRef.afterClosed().subscribe(result => {
      if (result === 'completed') {
        this.userAccountsService.reloadUserAccounts();
      }
    });
  }

  /**
   * Opens a dialog form to create a new policy.
   * 
   * @returns void
   */
  openCreatePolicyFormDialog(): void {
    // Open the policy creation dialog form
    const dialogRef: MatDialogRef<FormCreatePolicyComponent> = this.dialog.open(
      FormCreatePolicyComponent,
      {
        width: '600px',
        height: '500px',
      }
    );

    // Reload user policies on form completion
    dialogRef.afterClosed().subscribe(result => {
      if (result === 'completed') {
        this.userPoliciesService.reloadUserPolicies();
      }
    });
  }
}