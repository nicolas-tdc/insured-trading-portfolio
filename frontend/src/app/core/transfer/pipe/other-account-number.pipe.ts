import { Pipe, PipeTransform } from '@angular/core';
import { Transfer } from '../model';

@Pipe({
  name: 'otherAccountNumber'
})
export class OtherAccountNumberPipe implements PipeTransform {

  transform(transfer: Transfer, currentAccountId: string | undefined): string {
    if (!currentAccountId) return '';

    return transfer.sourceAccountNumber === currentAccountId ? transfer.targetAccountNumber : transfer.sourceAccountNumber;
  }

}
