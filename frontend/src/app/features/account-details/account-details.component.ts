import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Account, AccountsService } from '../../core';
import { Observable } from 'rxjs';
import { CommonModule } from '@angular/common';
import { CardItemAccountComponent } from '../../components/accounts/card-item-account/card-item-account.component';

@Component({
  selector: 'app-account-details',
  imports: [
    CommonModule,
    CardItemAccountComponent,
  ],
  templateUrl: './account-details.component.html',
  styleUrl: './account-details.component.scss'
})
export class AccountDetailsComponent {

  // Properties

  private _account: Account | null = null;

  // Accessors

  get account() { return this._account; }

  // Lifecycle

  constructor(
    private route: ActivatedRoute,
    private accountsService: AccountsService,
  ) {}

  ngOnInit(): void {
    const paramId = this.route.snapshot.paramMap.get('accountId');
    if (!paramId) {
      return;
    }

    this.accountsService.getItem(paramId).subscribe(data => {
      this._account = data;
    });
  }
}
