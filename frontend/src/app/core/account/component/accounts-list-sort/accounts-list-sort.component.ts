import { Component, Signal } from '@angular/core';
import { Account } from '../../model';
import { UserAccountsService } from '../../service';
import { MatListModule } from '@angular/material/list';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { SortIconPipe } from '../../../shared/pipe/sort-icon.pipe';

/**
 * AccountsListSortComponent
 * 
 * Component for sorting user accounts
 * 
 * @export
 */
@Component({
  selector: 'app-accounts-list-sort',
  imports: [
    MatListModule,
    MatButtonModule,
    MatIconModule,
    SortIconPipe,
  ],
  templateUrl: './accounts-list-sort.component.html',
  styleUrl: './accounts-list-sort.component.scss'
})
export class AccountsListSortComponent {

  /**
   * Initializes the component
   * Injects required services for user accounts data
   * 
   * @param userAccountsService Service for user accounts
   */
  constructor(
    private readonly userAccountsService: UserAccountsService,
  ) { }

  /**
   * Sort field for user accounts
   * Provided by the user accounts service
   * 
   * @returns Signal<keyof Account>
   */
  get sortField(): Signal<keyof Account> { return this.userAccountsService.sortFieldValue; }

  /**
   * Sort direction for user accounts
   * Provided by the user accounts service
   *
   * @returns Signal<'asc' | 'desc'>
   */
  get sortDirection(): Signal<'asc' | 'desc'> { return this.userAccountsService.sortDirectionValue; }

  /**
   * Sorts user accounts by balance
   * Executed by the user accounts service
   * 
   * @returns void
   */
  toggleSortByBalance(): void { this.userAccountsService.sortByField('balance'); }

  /**
   * Sorts user accounts by account type
   * Executed by the user accounts service
   * 
   * @returns void
   */
  toggleSortByAccountType(): void { this.userAccountsService.sortByField('typeCode'); }

  /**
   * Sorts user accounts by account number
   * Executed by the user accounts service
   * 
   * @returns void
   */
  toggleSortByAccountNumber(): void { this.userAccountsService.sortByField('accountNumber'); }
}
