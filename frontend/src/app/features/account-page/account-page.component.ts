import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MatDialog } from '@angular/material/dialog';
import { AccountService } from '../../core/account/service/account.service';
import { AccountDetailsComponent } from '../../core/account/component/account-details/account-details.component';
import { MatButton } from '@angular/material/button';
import { ListTableTransfersComponent } from '../../core/transfer/component/list-table-transfers/list-table-transfers.component';
import { TransferService } from '../../core/transfer/transfer.service';

@Component({
  selector: 'app-account-page',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    MatButton,
    AccountDetailsComponent,
    ListTableTransfersComponent,
  ],
  templateUrl: './account-page.component.html',
  styleUrl: './account-page.component.scss'
})
export class AccountPageComponent implements OnInit {

  // Properties

  public get account() { return this.accountService.userAccount(); }

  // Lifecycle

  constructor(
    private accountService: AccountService,
    private transferService: TransferService,
    private route: ActivatedRoute,
    private dialog: MatDialog,
  ) { }

  ngOnInit(): void {
    const accountId = this.route.snapshot.paramMap.get('accountId');
    if (!accountId) return;

    this.accountService.selectAccount(accountId);
    this.transferService.selectAccount(accountId);
  }

  ngOnDestroy(): void {
    this.accountService.selectAccount(null);
    this.transferService.selectAccount(null);
  }

  // Dialog

  openTransferDialog(): void {
    this.transferService.openCreateTransferFormDialog(this.account);
  }
}
