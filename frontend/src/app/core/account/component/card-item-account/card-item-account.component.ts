import { Component, input } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Clipboard } from '@angular/cdk/clipboard';
import { Account } from '../../model';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatChipsModule } from '@angular/material/chips';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatTooltipModule } from '@angular/material/tooltip';
import { TransferService } from '../../../transfer/transfer.service';

@Component({
  selector: 'app-card-item-account',
  imports: [
    CommonModule,
    RouterLink,
    MatCardModule,
    MatButtonModule,
    MatChipsModule,
    MatIconModule,
    MatTooltipModule,
  ],
  templateUrl: './card-item-account.component.html',
  styleUrl: './card-item-account.component.scss'
})
export class CardItemAccountComponent {

  // Properties

  account = input<Account | null>();

  tooltipText = 'Copy to clipboard';

  // Lifecycle

  constructor(
    private clipboard: Clipboard,
    private transferService: TransferService,
  ) { }

  // Tooltip

  copyAccountNumber() {
    const accountNumber = this.account()?.accountNumber;
    if (!accountNumber) return;

    this.clipboard.copy(accountNumber);
    this.tooltipText = 'Copied!';
  }

  resetTooltip() {
    this.tooltipText = 'Copy to clipboard';
  }

  openTransferDialog() {
    this.transferService.openCreateTransferFormDialog(this.account());
  }
}
