import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AccountService, UserAccountsService } from '../../core/account/service';
import { AccountDetailsComponent } from '../../core/account/component/account-details/account-details.component';
import { MatButtonModule } from '@angular/material/button';
import { ListTableTransfersComponent } from '../../core/transfer/component/list-table-transfers/list-table-transfers.component';
import { AccountTransfersService } from '../../core/transfer/service/account-transfers.service';

@Component({
  selector: 'app-account-page',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    MatButtonModule,
    AccountDetailsComponent,
    ListTableTransfersComponent,
  ],
  templateUrl: './account-page.component.html',
  styleUrl: './account-page.component.scss'
})
export class AccountPageComponent implements OnInit {

  // Properties

  public get account() { return this.accountService.userAccount(); }
  public get accounts() { return this.userAccountsService.userAccounts(); }

  // Lifecycle

  constructor(
    private accountService: AccountService,
    private accountTransfersService: AccountTransfersService,
    private route: ActivatedRoute,
    private userAccountsService: UserAccountsService
  ) { }

  ngOnInit(): void {
    const accountId = this.route.snapshot.paramMap.get('accountId');
    if (!accountId) return;

    this.accountService.selectAccount(accountId);
    this.accountTransfersService.selectAccount(accountId);
  }

  ngOnDestroy(): void {
    this.accountService.selectAccount(null);
    this.accountTransfersService.selectAccount(null);
  }
}
