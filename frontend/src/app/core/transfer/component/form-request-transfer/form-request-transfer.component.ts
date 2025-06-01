import { CommonModule } from '@angular/common';
import { Component, Inject, OnInit } from '@angular/core';
import { ReactiveFormsModule, FormGroup, FormControl, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { TransferService } from '../../transfer.service';
import { Account } from '../../../account/model';
import { MatError, MatFormField, MatHint, MatLabel } from '@angular/material/form-field';
import { MatInput } from '@angular/material/input';
import { MatButton } from '@angular/material/button';

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
  ],
  templateUrl: './form-request-transfer.component.html',
  styleUrl: './form-request-transfer.component.scss'
})
export class FormRequestTransferComponent implements OnInit {

  transferForm!: FormGroup;

  constructor(
    private transferService: TransferService,
    public dialogRef: MatDialogRef<FormRequestTransferComponent>,
    @Inject(MAT_DIALOG_DATA) public account: Account
  ) {}


  ngOnInit(): void {
    this.transferForm = new FormGroup({
      targetAccountNumber: new FormControl('', [
        Validators.required,
        Validators.maxLength(12),
        Validators.minLength(12),
      ]),
      amount: new FormControl('', [
        Validators.required,
        Validators.min(0.01),
        Validators.max(this.account.balance),
        Validators.pattern(/^\d+(\.\d{2})$/),
      ]),
      description: new FormControl(''),
    });
  }

  createTransfer(): void {
    if (this.transferForm.invalid) return;

    const transferRequest = {
      sourceAccountId: this.account.id,
      ...this.transferForm.value
    };

    this.transferService.create(transferRequest).subscribe(() => {
      this.dialogRef.close('completed');
    });
  }
}
