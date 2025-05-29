import { Component } from '@angular/core';
import { ListCardPoliciesComponent } from '../../policy/component/card-list-policies/card-list-policies.component';
import { ListCardAccountsComponent } from '../../account/component/card-list-accounts/card-list-accounts.component';
import { Account } from '../../account/model';
import { Policy } from '../../policy/model';
import { AccountService } from '../../account/account.service';
import { PolicyService } from '../../policy/policy.service';

@Component({
  selector: 'app-dashboard',
  imports: [
    ListCardAccountsComponent,
    ListCardPoliciesComponent,
  ],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent {

  // Properties

  private _accounts: Account[] = [];
  private _policies: Policy[] = [];

  // Accessors

  get accounts() { return this._accounts; }
  set accounts(value: Account[]) { this._accounts = value; }

  get policies() { return this._policies; }
  set policies(value: Policy[]) { this._policies = value; }

  // Lifecycle

  constructor(
    private accountService: AccountService,
    private policyService: PolicyService
  ) { }

  ngOnInit(): void {
    this.loadAccounts();
    this.loadPolicies();
  }

  // API

  loadAccounts(): void {
      this.accountService.getList().subscribe(data => {
        this.accounts = data;
      });
    }

  loadPolicies(): void {
    this.policyService.getList().subscribe(data => {
      this.policies = data;
    });
  }
}
