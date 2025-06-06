import { Component, input } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatChipsModule } from '@angular/material/chips';
import { CommonModule } from '@angular/common';
import { Account } from '../../model';

@Component({
  selector: 'app-account-details',
  imports: [
    CommonModule,
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