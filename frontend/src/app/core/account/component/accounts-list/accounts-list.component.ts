import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { AccountsListItemComponent } from '../accounts-list-item/accounts-list-item.component';
import { Account } from '../../model';
import { UserAccountsService } from '../../service';
import { AccountsListSortComponent } from '../accounts-list-sort/accounts-list-sort.component';
import { AccountsListHeaderComponent } from '../accounts-list-header/accounts-list-header.component';
import { AccountsListEmptyComponent } from '../accounts-list-empty/accounts-list-empty.component';

/**
 * AccountsListComponent
 * 
 * Displays a list of user accounts
 * 
 * @export
 */
@Component({
  selector: 'app-accounts-list',
  imports: [
    CommonModule,
    AccountsListHeaderComponent,
    AccountsListSortComponent,
    AccountsListItemComponent,
    AccountsListEmptyComponent,
  ],
  templateUrl: './accounts-list.component.html',
  styleUrl: './accounts-list.component.scss'
})
export class AccountsListComponent {

  /**
   * List of user accounts
   */
  get accounts(): Account[] | undefined { return this.userAccountsService.userAccounts(); }


  /**
   * Initializes the component
   * Injects required services for user accounts data
   * 
   * @param userAccountsService Service for user accounts
   */
  constructor(
    private readonly userAccountsService: UserAccountsService,
  ) { }
}
