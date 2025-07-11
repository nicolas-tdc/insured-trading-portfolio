import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AccountService } from '../../core/account/service';
import { AccountDetailsComponent } from '../../core/account/component/account-details/account-details.component';
import { MatButtonModule } from '@angular/material/button';
import { AccountTransfersService } from '../../core/transfer/service/account-transfers.service';
import { AccountPoliciesComponent } from '../../core/account/component/account-policies/account-policies.component';
import { AccountTransfersComponent } from '../../core/account/component/account-transfers/account-transfers.component';
import { AuthService } from '../../core/auth/service';

/**
 * AccountPageComponent
 *
 * Displays account details, related policies, and recent transfers.
 * Handles account selection logic based on route parameters.
 */
@Component({
  selector: 'app-account-page',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    MatButtonModule,
    AccountDetailsComponent,
    AccountPoliciesComponent,
    AccountTransfersComponent,
  ],
  templateUrl: './account-page.component.html',
  styleUrl: './account-page.component.scss'
})
export class AccountPageComponent implements OnInit, OnDestroy {

  /**
   * Initializes the component.
   * Injects required services for account data and routing.
   * 
   * @param accountService - AccountService for account data.
   * @param accountTransfersService - AccountTransfersService for account transfers.
   * @param route - ActivatedRoute for route parameters.
   * @param userAccountsService - UserAccountsService for user accounts.
   */
  constructor(
    private readonly accountService: AccountService,
    private readonly accountTransfersService: AccountTransfersService,
    private readonly authService: AuthService,
    private readonly route: ActivatedRoute,
    private readonly router: Router,
  ) { }

  /**
   * Lifecycle hook called on component initialization.
   * Selects the account based on the route parameter and reloads user accounts.
   */
  ngOnInit(): void {
    // Redirect to authentication page if not logged in
    if (!this.authService.isLoggedIn()) {
      this.router.navigate(['/authentication']);
    }

    // Get the account ID from the route
    const accountId: string | null = this.route.snapshot.paramMap.get('accountId');
    if (!accountId) return;

    // Select the current account in account services
    this.accountService.selectAccount(accountId);
    this.accountTransfersService.selectAccount(accountId);
  }

  /**
   * Lifecycle hook called on component destruction.
   * Clears selected account and related transfer data from services.
   */
  ngOnDestroy(): void {
    // Clear the selected account
    this.accountService.selectAccount(null);
    this.accountService.clearSelectedAccount();

    // Clear account transfers
    this.accountTransfersService.selectAccount(null);
    this.accountTransfersService.clearAccountTransfers();
  }
}
