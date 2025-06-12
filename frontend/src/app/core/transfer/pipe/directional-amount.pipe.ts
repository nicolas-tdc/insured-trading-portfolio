import { Pipe, PipeTransform } from '@angular/core';
import { Transfer } from '../model';

@Pipe({
  name: 'directionalAmount'
})
export class DirectionalAmountPipe implements PipeTransform {

  transform(transfer: Transfer, currentAccountNumber: string | undefined): string {
    if (!currentAccountNumber) return transfer.amount.toString();

    if (transfer.sourceAccountNumber === currentAccountNumber) {
      return '-' + transfer.amount + ' ' + transfer.currencySymbol;
    } else if (transfer.targetAccountNumber === currentAccountNumber) {
      return '+' + transfer.amount + ' ' + transfer.currencySymbol;
    } else {
      return transfer.amount + ' ' + transfer.currencySymbol;
    }
  }

}
