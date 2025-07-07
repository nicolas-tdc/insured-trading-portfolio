import { Component } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatChipsModule } from '@angular/material/chips';
import { CommonModule } from '@angular/common';
import { CopyToClipboardComponent } from '../../../utils/component/copy-to-clipboard/copy-to-clipboard.component';
import { TransferService } from '../../../transfer/service';
import { MatButtonModule } from '@angular/material/button';
import { FormatAmountSignedPipe } from '../../../currency/pipe/format-amount-signed';
import { AccountService } from '../../service';
import { EntityService } from '../../../entity/service';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-account-details',
  imports: [
    CommonModule,
    MatCardModule,
    MatChipsModule,
    MatButtonModule,
    MatIconModule,
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
    private entityService: EntityService,
  ) { }

  // Dialog

  openTransferDialog(): void {
    this.transferService.openCreateTransferFormDialog(this.account?.id);
  }

  getAccountStatusClass(): string {
    return this.entityService.getStatusClass(this.account?.accountStatus);
  }
}