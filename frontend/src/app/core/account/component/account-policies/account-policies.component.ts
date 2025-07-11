import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { CopyToClipboardComponent } from '../../../shared/component/copy-to-clipboard/copy-to-clipboard.component';
import { AccountService } from '../../service';
import { Account } from '../../model';

/**
 * AccountPoliciesComponent
 * 
 * Displays account policies
 * 
 * @export
 */
@Component({
  selector: 'app-account-policies',
  imports: [
    CommonModule,
    MatCardModule,
    CopyToClipboardComponent,
  ],
  templateUrl: './account-policies.component.html',
  styleUrl: './account-policies.component.scss'
})
export class AccountPoliciesComponent {

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
