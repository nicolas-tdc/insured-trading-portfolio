import { Pipe, PipeTransform } from '@angular/core';
import { Transfer } from '../model';

/**
 * OtherAccountNumberPipe
 *
 * Pipe for displaying the other account number depending on transfer direction
 * 
 * @export
 */
@Pipe({
  name: 'otherAccountNumber'
})
export class OtherAccountNumberPipe implements PipeTransform {

  /**
   * Transform transfer into the other account number depending on transfer direction
   * 
   * @param transfer Transfer
   * @param currentAccountId Current account id 
   * @returns string
   */
  transform(transfer: Transfer, currentAccountId: string | undefined): string {
    // Check if current account id is defined
    if (!currentAccountId) return '';

    // Return the other account number depending on transfer direction
    return transfer.sourceAccountNumber === currentAccountId ? transfer.targetAccountNumber : transfer.sourceAccountNumber;
  }

}
