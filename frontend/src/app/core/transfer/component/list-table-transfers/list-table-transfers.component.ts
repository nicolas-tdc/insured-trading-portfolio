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
import { Account } from '../../../account/model';
import { Transfer } from '../../model';

/**
 * ListTableTransfersComponent
 * 
 * Component for displaying a table of transfers
 * 
 * @export
 */
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

  /**
   * Current user's account
   * Provided by the account service
   * 
   * @readonly
   * @type {Account | null | undefined}
   */
  public get account(): Account | null | undefined { return this.accountService.userAccount(); }

  /**
   * Account transfers
   * Provided by the account transfers service
   * 
   * @readonly
   * @type {Transfer[] | undefined}
   */
  public get transfers(): Transfer[] | undefined { return this.accountTransfersService.accountTransfers(); }

  /**
   * Table columns
   * 
   * @readonly
   * @type {string[]}
   */
  public displayedColumns: string[] = [
    'transferNumber',
    'transferStatus',
    'date',
    'amount',
    'otherAccountNumber',
    'description',
  ];

  /**
   * Initializes the component
   * Injects required services for displaying a table of transfers
   * 
   * @param accountTransfersService Service for account transfers
   * @param accountService Service for account
   */
  constructor(
    private readonly accountTransfersService: AccountTransfersService,
    private readonly accountService: AccountService,
  ) { }
}
