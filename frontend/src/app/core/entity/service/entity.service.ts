import { Injectable } from '@angular/core';
import { AuthService } from '../../auth/service';
import { PolicyService } from '../../policy/service';
import { AccountService } from '../../account/service';
import { UserAccountsService } from '../../account/service';
import { UserPoliciesService } from '../../policy/service';
import { AccountTransfersService } from '../../transfer/service/account-transfers.service';
import { TransferAccountsService } from '../../transfer/service';

/**
 * Entity service
 * 
 * @export
 */
@Injectable({
  providedIn: 'root'
})
export class EntityService {

  /**
   * Initializes the service
   * Injects required services for authentication, accounts, policies and transfers
   * 
   * @param authService Service for authentication
   * @param userAccountsService Service for user accounts
   * @param accountService Service for accounts
   * @param userPoliciesService Service for user policies
   * @param policyService Service for policies
   * @param accountTransfersService Service for account transfers
   * @param transferAccountsService Service for transfer accounts
   */
  constructor(
    private readonly authService: AuthService,
    private readonly userAccountsService: UserAccountsService,
    private readonly accountService: AccountService,
    private readonly userPoliciesService: UserPoliciesService,
    private readonly policyService: PolicyService,
    private readonly accountTransfersService: AccountTransfersService,
    private readonly transferAccountsService: TransferAccountsService,
  ) { }

  /**
   * Clears all entities
   * 
   * @returns void
   */
  public clearEntities(): void {
    // Clear Auth User
    this.authService.clearAuthUser();

    // Clear Accounts
    this.userAccountsService.clearUserAccounts();
    this.accountService.clearSelectedAccount();

    // Clear Policies
    this.userPoliciesService.clearUserPolicies();
    this.policyService.clearSelectedPolicy();

    // Clear Transfers
    this.transferAccountsService.clearTransferAccounts();
    this.accountTransfersService.clearAccountTransfers();
  }

  /**
   * Gets the status class
   * 
   * @param status Status
   * @returns string
   */
  getStatusClass(status: string | undefined): string {
    switch (status) {
      case 'PENDING':
        return 'status-pending';
      case 'ACTIVE':
      case 'COMPLETED':
        return 'status-active';
      case 'INACTIVE':
      case 'REJECTED':
        return 'status-inactive';
      default:
        return '';
    }
  }
}
