import { Component } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatChipsModule } from '@angular/material/chips';
import { CommonModule } from '@angular/common';
import { CopyToClipboardComponent } from '../../../utils/component/copy-to-clipboard/copy-to-clipboard.component';
import { TransferService } from '../../../transfer/service';
import { MatButton } from '@angular/material/button';
import { FormatAmountSignedPipe } from '../../../currency/pipe/format-amount-signed';
import { AccountService } from '../../service';

@Component({
  selector: 'app-account-details',
  imports: [
    CommonModule,
    MatCardModule,
    MatChipsModule,
    MatButton,
    CopyToClipboardComponent,
    FormatAmountSignedPipe,
  ],
  templateUrl: './account-details.component.html',
  styleUrl: './account-details.component.scss'
})
export class AccountDetailsComponent {

  // Properties

  public get account() { return this.accountService.userAccount(); }
  // Lifecycle

  constructor(
    private accountService: AccountService,
    private transferService: TransferService,
  ) { }

  // Dialog

  openTransferDialog(): void {
    this.transferService.openCreateTransferFormDialog(this.account?.id);
  }
}