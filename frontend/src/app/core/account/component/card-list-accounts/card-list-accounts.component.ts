import { CommonModule } from '@angular/common';
import { Component, input } from '@angular/core';
import { CardItemAccountComponent } from '../card-item-account/card-item-account.component';
import { Account } from '../../model';
import { MatCardModule } from '@angular/material/card';
import { MatGridListModule } from '@angular/material/grid-list';

@Component({
  selector: 'app-card-list-accounts',
  imports: [
    CommonModule,
    MatCardModule,
    MatGridListModule,
    CardItemAccountComponent,
  ],
  templateUrl: './card-list-accounts.component.html',
  styleUrl: './card-list-accounts.component.scss'
})
export class CardListAccountsComponent {

  // Properties

  accounts = input<Account[] | null>()
}
