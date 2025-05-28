import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { Transfer } from '../../../core';
import { ItemTableTransferComponent } from '../item-table-transfer/item-table-transfer.component';

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

  // Properties

  private _transfers: Transfer[] = [];

  // Accessors

  @Input({ required: true })
  set transfers(value: Transfer[]) { this._transfers = value; }

  get transfers() { return this._transfers; }
}
