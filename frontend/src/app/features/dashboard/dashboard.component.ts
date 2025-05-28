import { Component } from '@angular/core';
import { ListCardAccountsComponent } from '../../components/accounts/card-list-accounts/card-list-accounts.component';
import { Account, AccountsService, PoliciesService, Policy } from '../../core';
import { ListCardPoliciesComponent } from '../../components/policies/card-list-policies/card-list-policies.component';

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
    private accountsService: AccountsService,
    private policiesService: PoliciesService
  ) { }

  ngOnInit(): void {
    this.loadAccounts();
    this.loadPolicies();
  }

  // API

  loadAccounts(): void {
      this.accountsService.getList().subscribe(data => {
        this.accounts = data;
      });
    }

  loadPolicies(): void {
    this.policiesService.getList().subscribe(data => {
      this.policies = data;
    });
  }
}
