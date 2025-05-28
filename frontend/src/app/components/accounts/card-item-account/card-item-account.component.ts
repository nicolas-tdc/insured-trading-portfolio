import { Component, Input } from '@angular/core';
import { Account } from '../../../core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-card-item-account',
  imports: [
    RouterLink,
  ],
  templateUrl: './card-item-account.component.html',
  styleUrl: './card-item-account.component.scss'
})
export class CardItemAccountComponent {

  // Properties

  private _account: Account | null = null;

  // Accessors

  @Input()
  set account(value: Account) { this._account = value; }

  get accountId() { return this._account?.id; }
  get accountNumber() { return this._account?.accountNumber; }
  get type() { return this._account?.accountType; }
  get balance() { return this._account?.balance.toFixed(2); }

}
