import { Component } from '@angular/core';
import { Account, AccountsService, PoliciesService, PolicyRequest, PolicyType } from '../../../core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-form-apply-policy',
  imports: [
    CommonModule,
    FormsModule,
  ],
  templateUrl: './form-apply-policy.component.html',
  styleUrl: './form-apply-policy.component.scss'
})
export class FormApplyPolicyComponent {

  // Properties

  private _policy: PolicyRequest = {
    accountId: '',
    type: 'life',
    coverageAmount: 0
  }
  private _accounts: Account[] = [];
  private _policyTypes: PolicyType[] = [];

  // Accessors
  // Policy
  set policyAccountId(value: string) { this._policy.accountId = value; }
  set policyType(value: string) { this._policy.type = value; }
  set policyCoverageAmount(value: number) { this._policy.coverageAmount = value; }
  get policyAccountId() { return this._policy.accountId; }
  get policyType() { return this._policy.type; }
  get policyCoverageAmount() { return this._policy.coverageAmount; }
  // Account
  set accounts(value: Account[]) { this._accounts = value; }
  get accounts() { return this._accounts; }
  // Policy Types
  set policyTypes(value: PolicyType[]) { this._policyTypes = value; }
  get policyTypes() { return this._policyTypes; }

  // Lifecycle

  constructor(
    private router: Router,
    private policiesService: PoliciesService,
    private accountsService: AccountsService,
  ) {}

  ngOnInit(): void {
    this.loadAccounts();
    this.loadPolicyTypes();
  }

  // API

  createPolicy(): void {
    this.policiesService.create(this._policy).subscribe(() => {
      this.router.navigate(['/dashboard']);
    });
  }

  loadPolicyTypes(): void {
    this.policiesService.getTypes().subscribe(data => {
      this.policyTypes = data;
    })
  }

  loadAccounts(): void {
    this.accountsService.getList().subscribe(data => {
      this.accounts = data;
    });
  }
}
