import { CommonModule } from '@angular/common';
import { Component, Inject } from '@angular/core';
import { ReactiveFormsModule, FormGroup, FormControl, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { TransferService } from '../../service';
import { Account } from '../../../account/model';
import { MatError, MatFormField, MatHint, MatLabel } from '@angular/material/form-field';
import { MatInput } from '@angular/material/input';
import { MatButton } from '@angular/material/button';
import { AccountService } from '../../../account/service';
import { UserAccountsService } from '../../../account/service';

@Component({
  selector: 'app-form-request-transfer',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatInput,
    MatFormField,
    MatLabel,
    MatHint,
    MatButton,
    MatError,
  ],
  templateUrl: './form-request-transfer.component.html',
  styleUrl: './form-request-transfer.component.scss'
})
export class FormRequestTransferComponent {

  // Properties

  transferForm: FormGroup;

  transferError: string = '';

  // Lifecycle

  constructor(
    private transferService: TransferService,
    private accountService: AccountService,
    private userAccountsService: UserAccountsService,
    public dialogRef: MatDialogRef<FormRequestTransferComponent>,
    @Inject(MAT_DIALOG_DATA) public account: Account
  ) {

    this.transferForm = new FormGroup({
      targetAccountNumber: new FormControl('', [
        Validators.required,
        Validators.maxLength(12),
        Validators.minLength(12),
      ]),
      amount: new FormControl('', [
        Validators.required,
        Validators.max(this.account.balance),
        Validators.pattern('^[0-9]*$'),
      ]),
      description: new FormControl(''),
    });
  }

  // Transfer

  createTransfer(): void {
    if (this.transferForm.invalid) return;
    if (!this.account || !this.account.id) return;

    const transferRequest = {
      sourceAccountId: this.account.id,
      ...this.transferForm.value
    };

    this.transferService.create(transferRequest).subscribe({
      next: () => {
        this.transferService.reloadAccountTransfers();
        this.accountService.reloadUserAccount();
        this.userAccountsService.reloadUserAccounts();
        this.dialogRef.close('completed');
      },
      error: err => {
        this.transferError = err.error.message;
      }
    });
  }
}
