import { CommonModule } from '@angular/common';
import { Component, Signal } from '@angular/core';
import { CardItemAccountComponent } from '../card-item-account/card-item-account.component';
import { Account } from '../../model';
import { UserAccountsService } from '../../service';
import { MatButtonModule } from '@angular/material/button';
import { SortIconPipe } from '../../../shared/pipe/sort-icon.pipe';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';

/**
 * CardListAccountsComponent
 * 
 * Displays a list of user accounts
 * 
 * @export
 */
@Component({
  selector: 'app-card-list-accounts',
  imports: [
    CommonModule,
    MatButtonModule,
    MatIconModule,
    MatListModule,
    CardItemAccountComponent,
    SortIconPipe,
  ],
  templateUrl: './card-list-accounts.component.html',
  styleUrl: './card-list-accounts.component.scss'
})
export class CardListAccountsComponent {

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
