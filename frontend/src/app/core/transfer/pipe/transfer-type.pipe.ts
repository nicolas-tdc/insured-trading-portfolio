import { Pipe, PipeTransform } from '@angular/core';
import { Transfer } from '../model';

@Pipe({
  name: 'transferType'
})
export class TransferTypePipe implements PipeTransform {

  transform(transfer: Transfer, currentAccountNumber: string | undefined): string {
    if (!currentAccountNumber) return 'UNKNOWN';

    if (transfer.sourceAccountNumber === currentAccountNumber) {
      return 'OUTGOING';
    } else if (transfer.targetAccountNumber === currentAccountNumber) {
      return 'INCOMING';
    } else {
      return 'UNKNOWN';
    }
  }
}
