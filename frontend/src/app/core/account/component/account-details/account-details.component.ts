import { Component, input } from '@angular/core';
import { Account } from '../../model';
import { MatCardModule } from '@angular/material/card';
import { MatChip, MatChipsModule } from '@angular/material/chips';

@Component({
  selector: 'app-account-details',
  imports: [
    MatCardModule,
    MatChipsModule,
  ],
  templateUrl: './account-details.component.html',
  styleUrl: './account-details.component.scss'
})
export class AccountDetailsComponent {

  // Properties

  account = input<Account | null>();
}