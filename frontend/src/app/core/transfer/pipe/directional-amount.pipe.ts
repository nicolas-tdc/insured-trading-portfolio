import { Pipe, PipeTransform } from '@angular/core';
import { Transfer } from '../model';

@Pipe({
  name: 'directionalAmount',
  standalone: true
})
export class DirectionalAmountPipe implements PipeTransform {
  transform(transfer: Transfer, currentAccountNumber: string | undefined): number {
    if (!currentAccountNumber || !transfer) return transfer.amount;

    if (transfer.sourceAccountNumber === currentAccountNumber) {
      return -transfer.amount;
    } else if (transfer.targetAccountNumber === currentAccountNumber) {
      return transfer.amount;
    } else {
      return transfer.amount;
    }
  }
}
