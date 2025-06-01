import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { CardItemAccountComponent } from '../card-item-account/card-item-account.component';
import { Account } from '../../model';
import { AccountService } from '../../account.service';

@Component({
  selector: 'app-card-list-accounts',
  imports: [
    CommonModule,
    CardItemAccountComponent,
  ],
  templateUrl: './card-list-accounts.component.html',
  styleUrl: './card-list-accounts.component.scss'
})
export class ListCardAccountsComponent {

  // Properties, Accessors

  // Accounts
  @Input() accounts: Account[] = [];
}
