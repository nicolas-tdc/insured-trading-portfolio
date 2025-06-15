import { Component, Inject, input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { PolicyService } from '../../service';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatButton } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { Account } from '../../../account/model';
import { MatGridListModule } from '@angular/material/grid-list';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { FormCreateAccountComponent } from '../../../account/component/form-create-account/form-create-account.component';
import { UserPoliciesService } from '../../service';

@Component({
  selector: 'app-form-create-policy',
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatSelectModule,
    MatButton,
    MatInputModule,
    MatGridListModule,
  ],
  templateUrl: './form-create-policy.component.html',
  styleUrl: './form-create-policy.component.scss'
})
export class FormCreatePolicyComponent {

  // Properties

  public policyTypes: string[] = [];

  public policyForm!: FormGroup;


  // Lifecycle

  constructor(
    private policyService: PolicyService,
    private userPoliciesService: UserPoliciesService,
    public dialogRef: MatDialogRef<FormCreateAccountComponent>,
    @Inject(MAT_DIALOG_DATA) public accounts: Account[]
  ) { }

  ngOnInit(): void {
    this.loadPolicyTypes();

    this.policyForm = new FormGroup({
      policyType: new FormControl('', [
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
      this.dialogRef.close('completed');
    });
  }

  loadPolicyTypes(): void {
    this.policyService.getTypes().subscribe(data => {
      this.policyTypes = data;
    })
  }
}
