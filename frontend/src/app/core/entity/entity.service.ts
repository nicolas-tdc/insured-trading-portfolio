import { Injectable } from '@angular/core';
import { AuthService } from '../auth/auth.service';
import { PolicyService } from '../policy/policy.service';
import { TransferService } from '../transfer/transfer.service';
import { CurrencyService } from '../currency/currency.service';
import { AccountService } from '../account/account.service';

@Injectable({
  providedIn: 'root'
})
export class EntityService {

  constructor(
    private accountService: AccountService,
    private transferService: TransferService,
    private policyService: PolicyService,
    private authService: AuthService,
  ) { }

  public clearEntities() {
    // Auth
    this.authService.clearAuthUser();

    // Accounts
    this.accountService.clearUserAccounts();
    this.accountService.clearSelectedAccount();

    // Policies
    this.policyService.clearUserPolicies();
    this.policyService.clearSelectedPolicy();

    // Transfers
    this.transferService.clearAccountTransfers();
  }
}
