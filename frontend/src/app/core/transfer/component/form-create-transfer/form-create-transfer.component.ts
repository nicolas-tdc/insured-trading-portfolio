import { Component, effect } from '@angular/core';
import { TransferAccountsService } from '../../service/transfer-accounts.service';
import { MatDialogRef } from '@angular/material/dialog';
import { CommonModule } from '@angular/common';
import { AbstractControl, FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatStepperModule } from '@angular/material/stepper';
import { FormAccountExternalComponent } from '../form-account-external/form-account-external.component';
import { FormAccountInternalComponent } from '../form-account-internal/form-account-internal.component';
import { FormTransferDetailsComponent } from '../form-transfer-details/form-transfer-details.component';
import { AccountService } from '../../../account/service';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatDividerModule } from '@angular/material/divider';
import { Account } from '../../../account/model';
import { AccountSecure } from '../../../account/model/account-secure.model';

/**
 * FormCreateTransferComponent
 * 
 * Form for creating a transfer
 * 
 * @export
 */
@Component({
  selector: 'app-form-create-transfer',
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatStepperModule,
    MatFormFieldModule,
    MatDividerModule,
    FormAccountExternalComponent,
    FormAccountInternalComponent,
    FormTransferDetailsComponent,
  ],
  templateUrl: './form-create-transfer.component.html',
  styleUrl: './form-create-transfer.component.scss'
})
export class FormCreateTransferComponent {

  /**
   * Reactive transfer form
   */
  createTransferForm!: FormGroup;

  /**
   * Get select account form
   */
  public get selectAccountForm(): FormGroup { return this.createTransferForm.get('selectAccount') as FormGroup; }

  /**
   * Get transfer details form
   */
  public get transferDetailsForm(): FormGroup { return this.createTransferForm.get('transferDetails') as FormGroup; }

  /**
   * Get source account
   * 
   * @readonly
   * @type {Account | null | undefined}
   */
  public get sourceAccount(): Account | null | undefined { return this.accountService.userAccount(); }

  /**
   * Get target account
   * 
   * @readonly
   * @type {AccountSecure | null | undefined}
   */
  public get targetAccount(): AccountSecure | null | undefined { return this.transferAccountsService.targetAccount(); }

  /**
   * Initializes the component
   * Inject services for account, transfer accounts and dialog
   * 
   * @param accountService Service for account
   * @param transferAccountsService Service for transfer accounts
   * @param dialogRef Service for dialog
   */
  constructor(
    private readonly accountService: AccountService,
    private readonly transferAccountsService: TransferAccountsService,
    public readonly dialogRef: MatDialogRef<FormCreateTransferComponent>,
  ) {
    effect(() => {
      // Get source account
      const sourceAccount: Account | null | undefined = this.accountService.userAccount();
      if (!sourceAccount) {
        return;
      }

      // Select source account
      this.transferAccountsService.selectSourceAccount(sourceAccount.id);

      // Set amount validators
      const amountControl: AbstractControl<any, any> | null = this.transferDetailsForm.get('amount');
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
