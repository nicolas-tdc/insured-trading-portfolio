import { Component } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatChipsModule } from '@angular/material/chips';
import { CommonModule } from '@angular/common';
import { CopyToClipboardComponent } from '../../../shared/component/copy-to-clipboard/copy-to-clipboard.component';
import { MatButtonModule } from '@angular/material/button';
import { FormatAmountSignedPipe } from '../../../currency/pipe/format-amount-signed';
import { AccountService } from '../../service';
import { EntityService } from '../../../entity/service';
import { MatListModule } from '@angular/material/list';

@Component({
  selector: 'app-account-details',
  imports: [
    CommonModule,
    MatCardModule,
    MatChipsModule,
    MatButtonModule,
    MatListModule,
    CopyToClipboardComponent,
    FormatAmountSignedPipe,
  ],
  templateUrl: './account-details.component.html',
  styleUrl: './account-details.component.scss'
})
export class AccountDetailsComponent {

  // Account

  public get account() { return this.accountService.userAccount(); }

  // Lifecycle

  constructor(
    private accountService: AccountService,
  ) { }
}