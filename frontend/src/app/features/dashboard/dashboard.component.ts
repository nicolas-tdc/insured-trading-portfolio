import { Component, signal } from '@angular/core';
import { ListCardPoliciesComponent } from '../../core/policy/component/card-list-policies/card-list-policies.component';
import { ListCardAccountsComponent } from '../../core/account/component/card-list-accounts/card-list-accounts.component';
import { Account } from '../../core/account/model';
import { Policy } from '../../core/policy/model';
import { AccountService } from '../../core/account/account.service';
import { PolicyService } from '../../core/policy/policy.service';
import { FormCreateAccountComponent } from '../../core/account/component/form-create-account/form-create-account.component';
import { FormCreatePolicyComponent } from '../../core/policy/component/form-create-policy/form-create-policy.component';

@Component({
  selector: 'app-dashboard',
  imports: [
    ListCardAccountsComponent,
    ListCardPoliciesComponent,
    FormCreateAccountComponent,
    FormCreatePolicyComponent,
  ],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent {

  // Properties, Accessors

  accounts = signal<Account[]>([]);

  policies = signal<Policy[]>([]);

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
        this.accounts.set(data);
      });
    }

  loadPolicies(): void {
    this.policyService.getList().subscribe(data => {
      this.policies.set(data);
    });
  }

}
