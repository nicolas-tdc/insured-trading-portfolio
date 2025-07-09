import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AccountService, UserAccountsService } from '../../core/account/service';
import { AccountDetailsComponent } from '../../core/account/component/account-details/account-details.component';
import { MatButtonModule } from '@angular/material/button';
import { AccountTransfersService } from '../../core/transfer/service/account-transfers.service';
import { AccountPoliciesComponent } from '../../core/account/component/account-policies/account-policies.component';
import { AccountTransfersComponent } from '../../core/account/component/account-transfers/account-transfers.component';

@Component({
  selector: 'app-account-page',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    MatButtonModule,
    AccountDetailsComponent,
    AccountPoliciesComponent,
    AccountTransfersComponent,
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

    this.userAccountsService.reloadUserAccounts();
  }

  ngOnDestroy(): void {
    this.accountService.selectAccount(null);
    this.accountService.clearSelectedAccount();
    this.accountTransfersService.selectAccount(null);
    this.accountTransfersService.clearAccountTransfers();
  }
}
