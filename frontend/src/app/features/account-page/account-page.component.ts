import { Component, signal } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Account } from '../../core/account/model';
import { AccountService } from '../../core/account/account.service';
import { AccountDetailsComponent } from '../../core/account/component/account-details/account-details.component';
import { FormRequestTransferComponent } from '../../core/transfer/component/form-request-transfer/form-request-transfer.component';
@Component({
  selector: 'app-account-page',
  imports: [
    CommonModule,
    RouterLink,
    AccountDetailsComponent,
    FormRequestTransferComponent,
  ],
  templateUrl: './account-page.component.html',
  styleUrl: './account-page.component.scss'
})
export class AccountPageComponent {

  // Properties

  account = signal<Account | null>(null);

  // Lifecycle

  constructor(
    private route: ActivatedRoute,
    private accountService: AccountService,
  ) {}

  ngOnInit(): void {
    const paramId = this.route.snapshot.paramMap.get('accountId');
    if (!paramId) return;

    this.accountService.getItem(paramId).subscribe(data => {
      this.account.set(data);
    });
  }
}
