import { Component, input } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatChipsModule } from '@angular/material/chips';
import { CommonModule } from '@angular/common';
import { Account } from '../../model';
import { CopyToClipboardComponent } from '../../../utils/component/copy-to-clipboard/copy-to-clipboard.component';
import { TransferService } from '../../../transfer/service';
import { MatButton } from '@angular/material/button';

@Component({
  selector: 'app-account-details',
  imports: [
    CommonModule,
    MatCardModule,
    MatChipsModule,
    MatButton,
    CopyToClipboardComponent,
  ],
  templateUrl: './account-details.component.html',
  styleUrl: './account-details.component.scss'
})
export class AccountDetailsComponent {

  // Properties

  account = input<Account | null>();

  // Lifecycle

  constructor(
    private transferService: TransferService,
  ) { }

  // Dialog

  openTransferDialog(): void {
    this.transferService.openCreateTransferFormDialog(this.account());
  }
}