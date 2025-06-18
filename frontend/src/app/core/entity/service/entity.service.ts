import { Injectable } from '@angular/core';
import { AuthService } from '../../auth/service';
import { PolicyService } from '../../policy/service';
import { AccountService } from '../../account/service';
import { UserAccountsService } from '../../account/service';
import { UserPoliciesService } from '../../policy/service';
import { AccountTransfersService } from '../../transfer/service/account-transfers.service';
import { TransferAccountsService } from '../../transfer/service';

@Injectable({
  providedIn: 'root'
})
export class EntityService {

  constructor(
    private authService: AuthService,
    private userAccountsService: UserAccountsService,
    private accountService: AccountService,
    private userPoliciesService: UserPoliciesService,
    private policyService: PolicyService,
    private accountTransfersService: AccountTransfersService,
    private transferAccountsService: TransferAccountsService,
  ) { }

  public clearEntities() {
    // Auth
    this.authService.clearAuthUser();

    // Accounts
    this.userAccountsService.clearUserAccounts();
    this.accountService.clearSelectedAccount();

    // Policies
    this.userPoliciesService.clearUserPolicies();
    this.policyService.clearSelectedPolicy();

    // Transfers
    this.transferAccountsService.clearTransferAccounts();
    this.accountTransfersService.clearAccountTransfers();
  }
}
