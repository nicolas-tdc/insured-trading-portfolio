import { Component, Input } from '@angular/core';
import { Transfer } from '../../model';

@Component({
  selector: 'app-item-table-transfer',
  imports: [],
  templateUrl: './item-table-transfer.component.html',
  styleUrl: './item-table-transfer.component.scss'
})
export class ItemTableTransferComponent {

  // Properties, Accessors

  // Transfer
  private _transfer: Transfer | null = null;
  get amount() { return this._transfer?.amount.toFixed(2); }
  @Input({ required: true })
  set transfer(value: Transfer) { this._transfer = value; }
}
