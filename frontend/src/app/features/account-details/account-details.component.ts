import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { CardItemAccountComponent } from '../../account/component/card-item-account/card-item-account.component';
import { Account } from '../../account/model';
import { AccountService } from '../../account/account.service';
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
    private accountService: AccountService,
  ) {}

  ngOnInit(): void {
    const paramId = this.route.snapshot.paramMap.get('accountId');
    if (!paramId) {
      return;
    }

    this.accountService.getItem(paramId).subscribe(data => {
      this._account = data;
    });
  }
}
