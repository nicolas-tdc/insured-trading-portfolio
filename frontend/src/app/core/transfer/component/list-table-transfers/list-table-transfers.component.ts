import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { MatTableModule } from '@angular/material/table';
import { OtherAccountNumberPipe } from '../../pipe/other-account-number.pipe';
import { DirectionalAmountPipe } from '../../pipe/directional-amount.pipe';
import { AccountService } from '../../../account/service';
import { AccountTransfersService } from '../../service/account-transfers.service';
import { FormatAmountSignedPipe } from '../../../currency/pipe/format-amount-signed';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-list-table-transfers',
  imports: [
    CommonModule,
    MatTableModule,
    MatCardModule,
    MatButtonModule,
    OtherAccountNumberPipe,
    DirectionalAmountPipe,
    FormatAmountSignedPipe,
  ],
  templateUrl: './list-table-transfers.component.html',
  styleUrl: './list-table-transfers.component.scss'
})
export class ListTableTransfersComponent {

  // Account

  public get account() { return this.accountService.userAccount(); }

  // Transfers

  public get transfers() { return this.accountTransfersService.accountTransfers(); }

  // Table columns

  public displayedColumns: string[] = [
    'transferNumber',
    'transferStatus',
    'date',
    'amount',
    'otherAccountNumber',
    'description',
  ];

  // Lifecycle

  constructor(
    private accountTransfersService: AccountTransfersService,
    private accountService: AccountService,
  ) { }
}
