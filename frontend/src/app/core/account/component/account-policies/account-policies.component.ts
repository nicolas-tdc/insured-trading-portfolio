import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { CopyToClipboardComponent } from '../../../shared/component/copy-to-clipboard/copy-to-clipboard.component';
import { AccountService } from '../../service';

@Component({
  selector: 'app-account-policies',
  imports: [
    CommonModule,
    MatCardModule,
    CopyToClipboardComponent,
  ],
  templateUrl: './account-policies.component.html',
  styleUrl: './account-policies.component.scss'
})
export class AccountPoliciesComponent {

  // Account

  public get account() { return this.accountService.userAccount(); }

  // Lifecycle

  constructor(
    private accountService: AccountService,
  ) { }
}
