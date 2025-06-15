import { Injectable } from '@angular/core';
import { AuthService } from '../../auth/service';
import { PolicyService } from '../../policy/service';
import { TransferService } from '../../transfer/service';
import { AccountService } from '../../account/service';
import { UserAccountsService } from '../../account/service';
import { UserPoliciesService } from '../../policy/service';

@Injectable({
  providedIn: 'root'
})
export class EntityService {

  constructor(
    private accountService: AccountService,
    private userAccountsService: UserAccountsService,
    private transferService: TransferService,
    private policyService: PolicyService,
    private userPoliciesService: UserPoliciesService,
    private authService: AuthService,
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
    this.transferService.clearAccountTransfers();
  }
}
