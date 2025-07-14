import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { AccountService } from '../../service';
import { TransferService } from '../../../transfer/service';
import { ListTableTransfersComponent } from '../../../transfer/component/list-table-transfers/list-table-transfers.component';
import { Account } from '../../model';

/**
 * AccountTransfersComponent
 * 
 * Displays account transfers
 * 
 * @export
 */
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

  /**
   * Current user's account
   * Provided by the account service
   * 
   * @returns Account
   */
  public get account(): Account | undefined | null { return this.accountService.userAccount(); }

  /**
   * Initializes the component
   * Injects required services for account and transfer data
   * 
   * @param accountService Service for account data
   * @param transferService Service for transfer data
   */
  constructor(
    private readonly accountService: AccountService,
    private readonly transferService: TransferService,
  ) { }

  /**
   * Opens the transfer form dialog
   * 
   * @returns void
   */
  openTransferDialog(): void {
    this.transferService.openCreateTransferFormDialog(this.account?.id);
  }
}
