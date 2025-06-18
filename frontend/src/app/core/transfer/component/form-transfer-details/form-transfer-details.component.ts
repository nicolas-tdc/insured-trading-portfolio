import { Component, input } from '@angular/core';
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

  // Properties

  transferDetailsForm = input<FormGroup>();

  stepper = input<MatStepper | null>();

  public get sourceAccount() { return this.accountService.userAccount(); }

  public get targetAccount() { return this.transferAccountsService.targetAccount(); }
  public get targetAccountError() { return this.transferAccountsService.targetAccountError(); }

  apiError = '';

  // Lifecycle

  constructor(
    public dialogRef: MatDialogRef<FormCreateTransferComponent>,
    private accountService: AccountService,
    private userAccountsService: UserAccountsService,
    private transferService: TransferService,
    private transferAccountsService: TransferAccountsService,
    private accountTransfersService: AccountTransfersService,
  ) {
  }

  stepBack(): void {
    this.stepper()?.previous();
  }

  createTransfer(): void {
    if (this.transferDetailsForm()?.invalid) return;
    if (!this.sourceAccount?.id || !this.targetAccount?.accountNumber) return;

    const request: TransferRequest = {
      sourceAccountId: this.sourceAccount.id,
      targetAccountNumber: this.targetAccount.accountNumber,
      amount: this.transferDetailsForm()?.value.amount,
      description: this.transferDetailsForm()?.value.description,
    };

    this.transferService.create(request).subscribe({
      next: () => {
        this.accountService.reloadUserAccount();
        this.userAccountsService.reloadUserAccounts();
        this.accountTransfersService.reloadAccountTransfers();

        this.transferAccountsService.clearTransferAccounts();
        this.dialogRef.close('completed');
      },
      error: err => {
        this.apiError = err.error.message;
      }
    });
  }
}