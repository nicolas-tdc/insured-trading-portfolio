import { Component } from '@angular/core';
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
import { UserAccountsService } from '../../../account/service';
import { PolicyType } from '../../model/policy-type.model';

@Component({
  selector: 'app-form-create-policy',
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatSelectModule,
    MatButtonModule,
    MatInputModule,
  ],
  templateUrl: './form-create-policy.component.html',
  styleUrl: './form-create-policy.component.scss'
})
export class FormCreatePolicyComponent {

  // Properties

  public policyForm!: FormGroup;

  public policyTypes: PolicyType[] = [];

  public get userAccounts() { return this.userAccountsService.userAccounts(); }

  // Lifecycle

  constructor(
    private policyService: PolicyService,
    private userPoliciesService: UserPoliciesService,
    private userAccountsService: UserAccountsService,
    public dialogRef: MatDialogRef<FormCreateAccountComponent>,
  ) { }

  ngOnInit(): void {
    this.loadPolicyTypes();

    this.policyForm = new FormGroup({
      typeCode: new FormControl('', [
        Validators.required,
      ]),
      coverageAmount: new FormControl('', [
        Validators.required,
        Validators.pattern('^[0-9]*$'),
      ]),
      accountId: new FormControl('', [
        Validators.required,
      ])
    });
  }

  // API

  createPolicy(): void {
    if (this.policyForm.invalid) return;

    this.policyService.create(this.policyForm.value).subscribe(() => {
      this.userPoliciesService.reloadUserPolicies();
      this.userAccountsService.reloadUserAccounts();
      this.dialogRef.close('completed');
    });
  }

  loadPolicyTypes(): void {
    this.policyService.getTypes().subscribe(data => {
      this.policyTypes = data;
    })
  }
}
