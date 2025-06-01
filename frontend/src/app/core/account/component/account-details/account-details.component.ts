import { Component, Input } from '@angular/core';
import { Account } from '../../model';

@Component({
  selector: 'app-account-details',
  imports: [],
  templateUrl: './account-details.component.html',
  styleUrl: './account-details.component.scss'
})
export class AccountDetailsComponent {

  // Properties, Accessors

  // Account
  @Input() account: Account | null = null;
}