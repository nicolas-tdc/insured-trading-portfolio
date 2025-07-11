import { Component } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatChipsModule } from '@angular/material/chips';
import { CommonModule } from '@angular/common';
import { CopyToClipboardComponent } from '../../../shared/component/copy-to-clipboard/copy-to-clipboard.component';
import { MatButtonModule } from '@angular/material/button';
import { FormatAmountSignedPipe } from '../../../currency/pipe/format-amount-signed';
import { AccountService } from '../../service';
import { MatListModule } from '@angular/material/list';
import { Account } from '../../model';

/**
 * Account details component
 * 
 * Displays account details such as account number, balance, status, and type.
 * 
 * @export
 */
@Component({
  selector: 'app-account-details',
  imports: [
    CommonModule,
    MatCardModule,
    MatChipsModule,
    MatButtonModule,
    MatListModule,
    CopyToClipboardComponent,
    FormatAmountSignedPipe,
  ],
  templateUrl: './account-details.component.html',
  styleUrl: './account-details.component.scss'
})
export class AccountDetailsComponent {

  /**
   * Current user's account
   * Provided by the account service
   */
  public get account(): Account | undefined | null { return this.accountService.userAccount(); }

  /**
   * Initializes the component.
   * Injects required services for account data.
   * 
   * @param accountService Service for account data
   */
  constructor(
    private readonly accountService: AccountService,
  ) { }
}