import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { AccountService } from '../../service';
import { TransferService } from '../../../transfer/service';
import { ListTableTransfersComponent } from '../../../transfer/component/list-table-transfers/list-table-transfers.component';

@Component({
  selector: 'app-account-transfers',
  imports: [
    CommonModule,
    MatCardModule,
    MatButtonModule,
    ListTableTransfersComponent,
  ],
  templateUrl: './account-transfers.component.html',
  styleUrl: './account-transfers.component.scss'
})
export class AccountTransfersComponent {

  // Account

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
