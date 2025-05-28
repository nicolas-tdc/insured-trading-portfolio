import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Account, AccountsService, Transfer, TransferRequest, TransfersService } from '../../../core';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';

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

  // Properties

  private _transfer: TransferRequest = {
    sourceAccountId: '',
    targetAccountNumber: '',
    amount: 0,
    description: '',
  }
  private _account: Account | null = null;

  // Accessors
  // Transfer
  set targetAccountNumber(value: string) { this._transfer.targetAccountNumber = value; }
  set amount(value: number) { this._transfer.amount = value; }
  set description(value: string) { this._transfer.description = value; }
  get targetAccountNumber() { return this._transfer.targetAccountNumber; }
  get amount() { return this._transfer.amount; }
  get description() { return this._transfer.description; }
  // Account
  @Input({ required: true })
  set account(value: Account) { this._account = value; }
  get accountId() { return this._account?.id; }

  // Lifecycle

  constructor(
    private router: Router,
    private transfersService: TransfersService,
  ) { }

  // API

  createTransfer(): void {
    this._transfer.sourceAccountId = this._account?.id || '';

    this.transfersService.create(this._transfer).subscribe(() => {
      this.router.navigate(['/accounts/' + this.account.id]);
    });
  }
}
