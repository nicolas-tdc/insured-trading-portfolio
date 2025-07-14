import { Pipe, PipeTransform } from '@angular/core';
import { Transfer } from '../model';

/**
 * TransferTypePipe
 * 
 * Pipe for displaying the transfer type
 * 
 * @export
 */
@Pipe({
  name: 'transferType'
})
export class TransferTypePipe implements PipeTransform {

  /**
   * Transform transfer into the transfer type
   * 
   * @param transfer Transfer
   * @param currentAccountNumber Current account number 
   * @returns string
   */
  transform(transfer: Transfer, currentAccountNumber: string | undefined): string {
    // Check if current account number is defined
    if (!currentAccountNumber) return 'UNKNOWN';

    // Return the transfer type
    if (transfer.sourceAccountNumber === currentAccountNumber) {
      return 'OUTGOING';
    } else if (transfer.targetAccountNumber === currentAccountNumber) {
      return 'INCOMING';
    } else {
      return 'UNKNOWN';
    }
  }
}
