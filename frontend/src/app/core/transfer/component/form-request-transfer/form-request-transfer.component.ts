import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { TransferRequest } from '../../model';
import { Account } from '../../../account/model';
import { TransferService } from '../../transfer.service';

@Component({
  selector: 'app-form-request-transfer',
  imports: [
    CommonModule,
    FormsModule,
  ],
  templateUrl: './form-request-transfer.component.html',
  styleUrl: './form-request-transfer.component.scss'
})
export class FormRequestTransferComponent {

  // Properties, Accessors

  // Transfer
  private _transfer: TransferRequest = {
    sourceAccountId: '',
    targetAccountNumber: '',
    amount: 0,
    description: '',
  }

  get targetAccountNumber() { return this._transfer.targetAccountNumber; }
  set targetAccountNumber(value: string) { this._transfer.targetAccountNumber = value; }

  get amount() { return this._transfer.amount; }
  set amount(value: number) { this._transfer.amount = value; }

  get description() { return this._transfer.description; }
  set description(value: string) { this._transfer.description = value; }

  // Account
  @Input() account: Account | null = null;

  // Lifecycle

  constructor(
    private router: Router,
    private transferService: TransferService,
  ) { }

  // API

  createTransfer(): void {
    this._transfer.sourceAccountId = this.account?.id || '';

    this.transferService.create(this._transfer).subscribe(() => {
      this.router.navigate(['/accounts/' + this.account?.id]);
    });
  }
}
