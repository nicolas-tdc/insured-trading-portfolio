import { CommonModule } from '@angular/common';
import { Component, input } from '@angular/core';
import { CardItemAccountComponent } from '../card-item-account/card-item-account.component';
import { Account } from '../../model';
import { UserAccountsService } from '../../service';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-card-list-accounts',
  imports: [
    CommonModule,
    MatButtonModule,
    CardItemAccountComponent,
  ],
  templateUrl: './card-list-accounts.component.html',
  styleUrl: './card-list-accounts.component.scss'
})
export class CardListAccountsComponent {

  // Properties

  accounts = input<Account[] | []>();

  sorted: string = 'date_asc';

  // Lifecycle

  constructor(
    private userAccountsService: UserAccountsService,
  ) { }

  // Sorting

  toggleSortByDate(): void {
    if (this.sorted === 'date_asc') {
      this.userAccountsService.reverseUserAccounts();
      this.sorted = 'date_desc';

      return;
    }
    
    if (this.sorted === 'date_desc') {
      this.userAccountsService.reverseUserAccounts();
      this.sorted = 'date_asc';

      return;
    }

    this.userAccountsService.reloadUserAccounts();
    this.sorted = 'date_asc';
  }

  toggleSortByBalance(): void {
    if (this.sorted === 'balance_asc') {
      this.userAccountsService.reverseUserAccounts();
      this.sorted = 'balance_desc';

      return;
    }

    if (this.sorted === 'balance_desc') {
      this.userAccountsService.reverseUserAccounts();
      this.sorted = 'balance_asc';

      return;
    }

    this.userAccountsService.sortByBalance('desc');
    this.sorted = 'balance_desc';
  }

  toggleSortByAccountType(): void {
    if (this.sorted === 'accountType_asc') {
      this.userAccountsService.reverseUserAccounts();
      this.sorted = 'accountType_desc';

      return;
    }

    if (this.sorted === 'accountType_desc') {
      this.userAccountsService.reverseUserAccounts();
      this.sorted = 'accountType_asc';

      return;
    }

    this.userAccountsService.sortByAccountType('desc');
    this.sorted = 'accountType_desc';
  }

  toggleSortByAccountNumber(): void {
    if (this.sorted === 'accountNumber_asc') {
      this.userAccountsService.reverseUserAccounts();
      this.sorted = 'accountNumber_desc';

      return;
    }

    if (this.sorted === 'accountNumber_desc') {
      this.userAccountsService.reverseUserAccounts();
      this.sorted = 'accountNumber_asc';

      return;
    }

    this.userAccountsService.sortByAccountNumber('desc');
    this.sorted = 'accountNumber_desc';
  }
}
