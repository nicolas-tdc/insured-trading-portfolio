import { CommonModule } from '@angular/common';
import { Component, input } from '@angular/core';
import { CardItemAccountComponent } from '../card-item-account/card-item-account.component';
import { Account } from '../../model';
import { UserAccountsService } from '../../service';
import { MatButtonModule } from '@angular/material/button';
import { SortIconPipe } from '../../../shared/pipe/sort-icon.pipe';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';

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

  // Properties

  accounts = input<Account[] | []>();

  // Lifecycle

  constructor(
    private userAccountsService: UserAccountsService,
  ) { }

  // Sorting

  get sortField() { return this.userAccountsService.sortFieldValue; }
  get sortDirection() { return this.userAccountsService.sortDirectionValue; }

  toggleSortByBalance(): void { this.userAccountsService.sortByField('balance'); }

  toggleSortByAccountType(): void { this.userAccountsService.sortByField('accountType'); }

  toggleSortByAccountNumber(): void { this.userAccountsService.sortByField('accountNumber'); }
}
