import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { TransferService } from '../../service';
import { MatTableModule } from '@angular/material/table';
import { OtherAccountNumberPipe } from '../../pipe/other-account-number.pipe';
import { DirectionalAmountPipe } from '../../pipe/directional-amount.pipe';
import { AccountService } from '../../../account/service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-list-table-transfers',
  imports: [
    CommonModule,
    MatTableModule,
    OtherAccountNumberPipe,
    DirectionalAmountPipe,
  ],
  templateUrl: './list-table-transfers.component.html',
  styleUrl: './list-table-transfers.component.scss'
})
export class ListTableTransfersComponent {

  // Properties

  public get account() {
    return this.accountService.userAccount();
  }

  public get transfers() {
    return this.transferService.accountTransfers();
  }

  public displayedColumns: string[] = [
    'transferNumber',
    'date',
    'amount',
    'otherAccountNumber',
    'description',
  ];

  // Lifecycle

  constructor(
    private transferService: TransferService,
    private accountService: AccountService,
    private route: ActivatedRoute,
  ) { }


  ngOnInit(): void {
    this.accountService.selectAccount(
      this.route.snapshot.paramMap.get('accountId')!
    );
  }
}
