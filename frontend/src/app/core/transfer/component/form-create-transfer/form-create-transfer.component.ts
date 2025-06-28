import { Component, effect } from '@angular/core';
import { TransferAccountsService } from '../../service/transfer-accounts.service';
import { MatDialogRef } from '@angular/material/dialog';
import { CommonModule } from '@angular/common';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatStepperModule } from '@angular/material/stepper';
import { FormAccountExternalComponent } from '../form-account-external/form-account-external.component';
import { FormAccountInternalComponent } from '../form-account-internal/form-account-internal.component';
import { FormTransferDetailsComponent } from '../form-transfer-details/form-transfer-details.component';
import { AccountService } from '../../../account/service';
import { MatFormFieldModule } from '@angular/material/form-field';

@Component({
  selector: 'app-form-create-transfer',
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatStepperModule,
    MatFormFieldModule,
    FormAccountExternalComponent,
    FormAccountInternalComponent,
    FormTransferDetailsComponent,
  ],
  templateUrl: './form-create-transfer.component.html',
  styleUrl: './form-create-transfer.component.scss'
})
export class FormCreateTransferComponent {

  // Properties

  // Forms
  createTransferForm!: FormGroup;
  public get selectAccountForm() { return this.createTransferForm.get('selectAccount') as FormGroup; }
  public get transferDetailsForm() { return this.createTransferForm.get('transferDetails') as FormGroup; }

  // Accounts
  public get sourceAccount() { return this.accountService.userAccount(); }
  public get targetAccount() { return this.transferAccountsService.targetAccount(); }

  // Lifecycle

  constructor(
    private accountService: AccountService,
    private transferAccountsService: TransferAccountsService,
    public dialogRef: MatDialogRef<FormCreateTransferComponent>,
  ) {
    // Select source account
    effect(() => {
      const sourceAccount = this.accountService.userAccount();

      if (!sourceAccount) {
        return;
      }

      this.transferAccountsService.selectSourceAccount(sourceAccount.id);

      const amountControl = this.transferDetailsForm.get('amount');
      if (amountControl) {
        amountControl.setValidators([
          Validators.required,
          Validators.max(sourceAccount.balance),
          Validators.pattern('^[0-9]*$'),
        ]);
        amountControl.updateValueAndValidity();
      }
    });

    // Create reactive form
    this.createTransferForm = new FormGroup({
      selectAccount: new FormGroup({
        internal: new FormGroup({
          targetAccountId: new FormControl('', [
            Validators.required,
            Validators.minLength(36),
            Validators.maxLength(36),
          ]),
        }),
        external: new FormGroup({
          targetAccountNumber: new FormControl('', [
            Validators.required,
            Validators.maxLength(12),
            Validators.minLength(12),
          ]),
        }),
      }),

      transferDetails: new FormGroup({
        amount: new FormControl('', [
          Validators.required,
          Validators.max(0),
          Validators.pattern('^[0-9]*$'),
        ]),
        description: new FormControl('', [
          Validators.maxLength(100),
        ]),
      })
    });

  }
}
