import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { CardItemAccountComponent } from '../card-item-account/card-item-account.component';
import { Account } from '../../model';

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

  // Properties

  private _accounts: Account[] = [];

  // Accessors

  @Input()
  set accounts(value: Account[]) { this._accounts = value; }
  get accounts() { return this._accounts; }
}
