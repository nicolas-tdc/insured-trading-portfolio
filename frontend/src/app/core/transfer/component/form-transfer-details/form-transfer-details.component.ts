import { Component, input, InputSignal } from '@angular/core';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';
import { AccountTransfersService, TransferService } from '../../service';
import { TransferAccountsService } from '../../service/transfer-accounts.service';
import { TransferRequest } from '../../model';
import { AccountService, UserAccountsService } from '../../../account/service';
import { CommonModule } from '@angular/common';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { FormCreateTransferComponent } from '../form-create-transfer/form-create-transfer.component';
import { MatDialogRef } from '@angular/material/dialog';
import { MatStepper } from '@angular/material/stepper';
import { Account } from '../../../account/model';
import { AccountSecure } from '../../../account/model/account-secure.model';

/**
 * FormTransferDetailsComponent
 * 
 * Form for creating a transfer
 * 
 * @export
 */
@Component({
  selector: 'app-form-transfer-details',
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule
  ],
  templateUrl: './form-transfer-details.component.html',
  styleUrl: './form-transfer-details.component.scss'
})
export class FormTransferDetailsComponent {

  /**
   * Transfer details form
   * Provided as input
   */
  transferDetailsForm: InputSignal<FormGroup | undefined> = input<FormGroup>();

  /**
   * Stepper
   * Provided as input
   */
  stepper: InputSignal<MatStepper | null | undefined> = input<MatStepper | null>();

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
   * Get target account error
   * 
   * @readonly
   * @type {Error | undefined}
   */
  public get targetAccountError(): Error | undefined { return this.transferAccountsService.targetAccountError(); }

  /**
   * API error
   * 
   * @type {string}
   */
  apiError: string = '';

  /**
   * Initializes the component
   * Injects required services for creating a transfer
   * 
   * @param dialogRef Service for dialog
   * @param accountService Service for account
   * @param userAccountsService Service for user accounts
   * @param transferService Service for transfer
   * @param transferAccountsService Service for transfer accounts
   * @param accountTransfersService Service for account transfers
   */
  constructor(
    public readonly dialogRef: MatDialogRef<FormCreateTransferComponent>,
    private readonly accountService: AccountService,
    private readonly userAccountsService: UserAccountsService,
    private readonly transferService: TransferService,
    private readonly transferAccountsService: TransferAccountsService,
    private readonly accountTransfersService: AccountTransfersService,
  ) { }

  /**
   * Step back
   * 
   * @returns void
   */
  stepBack(): void {
    this.stepper()?.previous();
  }

  /**
   * Close dialog
   * 
   * @returns void
   */
  closeDialog(): void {
    this.dialogRef.close();
  }

  /**
   * Create transfer
   * 
   * @returns void
   */
  createTransfer(): void {
    // Check form validity
    if (this.transferDetailsForm()?.invalid) return;
  
    // Check source account and target account
    if (!this.sourceAccount?.id || !this.targetAccount?.accountNumber) return;

    // Create request
    const request: TransferRequest = {
      sourceAccountId: this.sourceAccount.id,
      targetAccountNumber: this.targetAccount.accountNumber,
      amount: this.transferDetailsForm()?.value.amount,
      description: this.transferDetailsForm()?.value.description,
    };

    // Create transfer
    this.transferService.create(request).subscribe({
      next: () => {
        // Reload selected account, accounts and account transfers
        this.accountService.reloadUserAccount();
        this.userAccountsService.reloadUserAccounts();
        this.accountTransfersService.reloadAccountTransfers();

        // Clear transfer accounts and close dialog
        this.transferAccountsService.clearTransferAccounts();
        this.dialogRef.close('completed');
      },
      error: err => {
        this.apiError = err.error.message;
      }
    });
  }
}