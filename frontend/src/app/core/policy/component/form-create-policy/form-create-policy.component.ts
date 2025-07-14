import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { PolicyService } from '../../service';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatDialogRef } from '@angular/material/dialog';
import { FormCreateAccountComponent } from '../../../account/component/form-create-account/form-create-account.component';
import { UserPoliciesService } from '../../service';
import { AccountService, UserAccountsService } from '../../../account/service';
import { PolicyType } from '../../model/policy-type.model';
import { Account } from '../../../account/model';
import { MatCardModule } from '@angular/material/card';

/**
 * FormCreatePolicyComponent
 * 
 * Form to create a new policy
 * 
 * @export
 */
@Component({
  selector: 'app-form-create-policy',
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatSelectModule,
    MatButtonModule,
    MatInputModule,
    MatCardModule,
  ],
  templateUrl: './form-create-policy.component.html',
  styleUrl: './form-create-policy.component.scss'
})
export class FormCreatePolicyComponent implements OnInit {

  /**
   * Policy form group
   */
  public policyForm!: FormGroup;

  /**
   * Policy types
   */
  public policyTypes: PolicyType[] = [];

  /**
   * Current user's account
   * Provided by the account service
   * 
   * @returns Account
   */
  public get account(): Account | undefined | null { return this.accountService.userAccount(); }

  /**
   * User accounts
   * 
   * @returns Account[]
   */
  public get userAccounts(): Account[] | undefined { return this.userAccountsService.userAccounts(); }

  /**
   * Initializes the component
   * Injects required services for policy, user policies and user accounts
   * 
   * @param policyService Service for policy
   * @param userPoliciesService Service for user policies
   * @param userAccountsService Service for user accounts
   * @param dialogRef Service for dialog
   */
  constructor(
    private readonly policyService: PolicyService,
    private readonly userPoliciesService: UserPoliciesService,
    private readonly accountService: AccountService,
    private readonly userAccountsService: UserAccountsService,
    public readonly dialogRef: MatDialogRef<FormCreateAccountComponent>,
  ) { }

  /**
   * Lifecycle hook called on component initialization
   * 
   * @returns void
   */
  ngOnInit(): void {
    // Load policy types
    this.loadPolicyTypes();

    // Create form
    this.policyForm = new FormGroup({
      typeCode: new FormControl('', [
        Validators.required,
      ]),
      coverageAmount: new FormControl('', [
        Validators.required,
        Validators.pattern('^[0-9]*$'),
      ]),
      accountId: new FormControl(this.account?.id, [
        Validators.required,
      ])
    });
  }

  /**
   * Creates a new policy
   * 
   * @returns void
   */
  createPolicy(): void {
    // Check if form is valid
    if (this.policyForm.invalid) return;

    // Create policy, reload user policies and accounts and close dialog
    this.policyService.create(this.policyForm.value).subscribe(() => {
      this.userPoliciesService.reloadUserPolicies();
      this.userAccountsService.reloadUserAccounts();
      this.dialogRef.close('completed');
    });
  }

  /**
   * Loads policy types
   * 
   * @returns void
   */
  loadPolicyTypes(): void {
    this.policyService.getTypes().subscribe(data => {
      this.policyTypes = data;
    })
  }
}
