import { Component, Input } from '@angular/core';
import { Transfer } from '../../../core';

@Component({
  selector: 'app-item-table-transfer',
  imports: [],
  templateUrl: './item-table-transfer.component.html',
  styleUrl: './item-table-transfer.component.scss'
})
export class ItemTableTransferComponent {

  // Properties

  private _transfer: Transfer | null = null;

  // Accessors

  @Input({ required: true })
  set transfer(value: Transfer) { this._transfer = value; }

  get type() { return this._transfer?.type; }
  get amount() { return this._transfer?.amount.toFixed(2); }
}
