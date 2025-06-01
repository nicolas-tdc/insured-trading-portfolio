import { Component, Input } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Account } from '../../model';
import { AccountDetailsComponent } from '../account-details/account-details.component';

@Component({
  selector: 'app-card-item-account',
  imports: [
    RouterLink,
    AccountDetailsComponent,
  ],
  templateUrl: './card-item-account.component.html',
  styleUrl: './card-item-account.component.scss'
})
export class CardItemAccountComponent {

  // Properties, Accessors

  // Account
  private _account: Account = {
    id: '',
    accountType: '',
    currency: '',
    accountNumber: '',
    balance: 0,
    status: '',
  };
  get account() { return this._account; }
  @Input()
  set account(value: Account) { this._account = value; }
}
