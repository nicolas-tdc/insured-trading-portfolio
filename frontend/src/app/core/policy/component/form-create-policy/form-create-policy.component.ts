import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { Policy, PolicyRequest } from '../../model';
import { Account } from '../../../account/model';
import { PolicyService } from '../../policy.service';

@Component({
  selector: 'app-form-create-policy',
  imports: [
    CommonModule,
    FormsModule,
  ],
  templateUrl: './form-create-policy.component.html',
  styleUrl: './form-create-policy.component.scss'
})
export class FormCreatePolicyComponent {

  // Properties, Accessors

  @Input() policies: Policy[] = [];

  @Input() accounts: Account[] = [];

  // Policy
  private _policy: PolicyRequest = {
    accountId: '',
    policyType: '',
    coverageAmount: 0
  }

  get policyAccountId() { return this._policy.accountId; }
  set policyAccountId(value: string) { this._policy.accountId = value; }

  get policyType() { return this._policy.policyType; }
  set policyType(value: string) { this._policy.policyType = value; }

  get policyCoverageAmount() { return this._policy.coverageAmount; }
  set policyCoverageAmount(value: number) { this._policy.coverageAmount = value; }

  // Policy Types
  private _policyTypes: string[] = [];
  get policyTypes() { return this._policyTypes; }
  set policyTypes(value: string[]) { this._policyTypes = value; }

  // Lifecycle

  constructor(
    private router: Router,
    private policyService: PolicyService,
  ) {}

  ngOnInit(): void {
    this.loadPolicyTypes();
  }

  // API

  createPolicy(): void {
    this.policyService.create(this._policy).subscribe((data) => {
      this.policies.push(data);
      this.router.navigate(['/dashboard']);
    });
  }

  loadPolicyTypes(): void {
    this.policyService.getTypes().subscribe(data => {
      this.policyTypes = data;
    })
  }
}
