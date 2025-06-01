import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { ItemTableTransferComponent } from '../item-table-transfer/item-table-transfer.component';
import { Transfer } from '../../model';

@Component({
  selector: 'app-list-table-transfers',
  imports: [
    CommonModule,
    ItemTableTransferComponent
  ],
  templateUrl: './list-table-transfers.component.html',
  styleUrl: './list-table-transfers.component.scss'
})
export class ListTableTransfersComponent {

  // Properties, Accessors

  // Account Id
  @Input() accountId: string = '';

  // Transfers
  private _transfers: Transfer[] = [];
  get transfers() { return this._transfers; }
}
